package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.CollegeFacultyMappingBean;
import in.cdac.university.globalService.bean.ComboBean;
import in.cdac.university.globalService.bean.MappedComboBean;
import in.cdac.university.globalService.entity.CmstCollegeFacultyMst;
import in.cdac.university.globalService.entity.GmstCoursefacultyMst;
import in.cdac.university.globalService.repository.CollegeFacultyMappingRepository;
import in.cdac.university.globalService.repository.FacultyRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.Language;
import in.cdac.university.globalService.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CollegeFacultyMappingService {

    @Autowired
    private CollegeFacultyMappingRepository collegeFacultyMappingRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private Language language;

    public ServiceResponse getMappingDetails(Long collegeId, Integer universityId) {
        List<GmstCoursefacultyMst> allFaculty = facultyRepository.getAllFaculty(universityId);

        Set<Integer> mappedFacultyIds = collegeFacultyMappingRepository.findByUnumIsvalidAndUnumUnivIdAndUnumCollegeId(1, universityId, collegeId)
                .stream()
                .map(CmstCollegeFacultyMst::getUnumFacultyId)
                .collect(Collectors.toSet());

        List<ComboBean> mappedFaculties = new ArrayList<>();
        List<ComboBean> notMappedFaculties = new ArrayList<>();

        for (GmstCoursefacultyMst coursefacultyMst: allFaculty) {
            if (coursefacultyMst.getUnumCfacultyId() == 0)
                continue;
            ComboBean comboBean = new ComboBean(coursefacultyMst.getUnumCfacultyId().toString(), coursefacultyMst.getUstrCfacultyFname());
            if (mappedFacultyIds.contains(coursefacultyMst.getUnumCfacultyId()))
                mappedFaculties.add(comboBean);
            else
                notMappedFaculties.add(comboBean);
        }

        MappedComboBean mappedComboBean = new MappedComboBean(mappedFaculties, notMappedFaculties);

        return ServiceResponse.builder()
                .status(1)
                .responseObject(mappedComboBean)
                .build();
    }

    public List<ComboBean> getMappedFaculties(Long collegeId, Integer universityId) {
        List<GmstCoursefacultyMst> allFaculty = facultyRepository.getAllFaculty(universityId);

        Set<Integer> mappedFacultyIds = collegeFacultyMappingRepository.findByUnumIsvalidAndUnumUnivIdAndUnumCollegeId(1, universityId, collegeId)
                .stream()
                .map(CmstCollegeFacultyMst::getUnumFacultyId)
                .collect(Collectors.toSet());

        List<ComboBean> mappedFaculties = new ArrayList<>();

        for (GmstCoursefacultyMst coursefacultyMst: allFaculty) {
            if (coursefacultyMst.getUnumCfacultyId() == 0)
                continue;
            ComboBean comboBean = new ComboBean(coursefacultyMst.getUnumCfacultyId().toString(), coursefacultyMst.getUstrCfacultyFname());
            if (mappedFacultyIds.contains(coursefacultyMst.getUnumCfacultyId()))
                mappedFaculties.add(comboBean);
        }

        return mappedFaculties;
    }

    @Transactional
    public ServiceResponse saveMappingDetails(CollegeFacultyMappingBean collegeFacultyMappingBean) {
        // Get existing Mapping Details
        List<CmstCollegeFacultyMst> alreadyMappedFaculties = collegeFacultyMappingRepository.findByUnumIsvalidAndUnumUnivIdAndUnumCollegeId(
                1, collegeFacultyMappingBean.getUnumUnivId(), collegeFacultyMappingBean.getUnumCollegeId());

        List<Integer> mappedFaculties = collegeFacultyMappingBean.getMappedFaculties();
        Set<Integer> mappedFacultiesSet = new HashSet<>(mappedFaculties);

        // Faculties to Delete
        List<CmstCollegeFacultyMst> facultiesToDelete = new ArrayList<>();
        for (CmstCollegeFacultyMst alreadyMappedFaculty : alreadyMappedFaculties) {
            if (!mappedFacultiesSet.contains(alreadyMappedFaculty.getUnumFacultyId())) {
                facultiesToDelete.add(alreadyMappedFaculty);
            } else {
                mappedFacultiesSet.remove(alreadyMappedFaculty.getUnumFacultyId());
            }
        }
        if (!facultiesToDelete.isEmpty()) {
            List<Integer> facultyIdsToDelete = facultiesToDelete.stream().map(CmstCollegeFacultyMst::getUnumFacultyId).toList();
            collegeFacultyMappingRepository.delete(collegeFacultyMappingBean.getUnumCollegeId(), facultyIdsToDelete);
        }

        // Faculties to add
        List<CmstCollegeFacultyMst> facultiesToAdd = new ArrayList<>();
        for (Integer facultyId : mappedFacultiesSet) {
            CmstCollegeFacultyMst collegeFacultyMst = BeanUtils.copyProperties(collegeFacultyMappingBean, CmstCollegeFacultyMst.class);
            collegeFacultyMst.setUnumColFacId(collegeFacultyMappingRepository.getNextId());
            collegeFacultyMst.setUnumFacultyId(facultyId);
            facultiesToAdd.add(collegeFacultyMst);
        }
        if (!facultiesToAdd.isEmpty())
            collegeFacultyMappingRepository.saveAll(facultiesToAdd);

        return ServiceResponse.builder()
                .status(1)
                .message(language.saveSuccess("College Faculty Mapping"))
                .build();
    }
}
