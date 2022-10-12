package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.CollegeCourseMappingBean;
import in.cdac.university.globalService.bean.ComboBean;
import in.cdac.university.globalService.bean.MappedComboBean;
import in.cdac.university.globalService.entity.CmstCollegeCourseMst;
import in.cdac.university.globalService.entity.CmstCollegeCourseMstPK;
import in.cdac.university.globalService.entity.GmstCourseMst;
import in.cdac.university.globalService.repository.CollegeCourseMappingRepository;
import in.cdac.university.globalService.repository.CourseRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.Language;
import in.cdac.university.globalService.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CollegeCourseMappingService {

    @Autowired
    private CollegeCourseMappingRepository collegeCourseMappingRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private Language language;

    public ServiceResponse getMappingDetails(Long collegeId, Integer facultyId, Integer universityId) {
        List<GmstCourseMst> allCourses = courseRepository.getAllCourses(universityId);

        Set<Long> mappedCourseIds = collegeCourseMappingRepository.findByUnumIsvalidAndUnumUnivIdAndUnumCfacultyIdAndUnumCollegeId(
                1, universityId, facultyId, collegeId
            ).stream()
            .map(CmstCollegeCourseMst::getUnumCourseId)
            .collect(Collectors.toSet());

        List<ComboBean> mappedCourses = new ArrayList<>();
        List<ComboBean> notMappedCourses = new ArrayList<>();

        for (GmstCourseMst courseMst: allCourses) {
            ComboBean comboBean = new ComboBean(courseMst.getUnumCourseId().toString(), courseMst.getUstrCourseFname());
            if (mappedCourseIds.contains(courseMst.getUnumCourseId())) {
                mappedCourses.add(comboBean);
            } else {
                notMappedCourses.add(comboBean);
            }
        }

        return ServiceResponse.builder()
                .status(1)
                .responseObject(new MappedComboBean(mappedCourses, notMappedCourses))
                .build();
    }

    @Transactional
    public ServiceResponse saveMappingDetails(CollegeCourseMappingBean collegeCourseMappingBean) {
        // Get existing Mapping Details
        List<CmstCollegeCourseMst> alreadyMappedCourses = collegeCourseMappingRepository.findByUnumIsvalidAndUnumUnivIdAndUnumCfacultyIdAndUnumCollegeId(
                1, collegeCourseMappingBean.getUnumUnivId(), collegeCourseMappingBean.getUnumCfacultyId(), collegeCourseMappingBean.getUnumCollegeId()
        );

        Set<Long> mappedCoursesSet = new HashSet<>(collegeCourseMappingBean.getMappedCourses());

        // Courses to delete
        List<CmstCollegeCourseMst> coursesToDelete = new ArrayList<>();
        for (CmstCollegeCourseMst courseMst: alreadyMappedCourses) {
            if (mappedCoursesSet.contains(courseMst.getUnumCourseId()))
                mappedCoursesSet.remove(courseMst.getUnumCourseId());
            else
                coursesToDelete.add(courseMst);
        }
        if (!coursesToDelete.isEmpty()) {
            List<Long> courseIdsToDelete = coursesToDelete.stream().map(CmstCollegeCourseMst::getUnumCourseId).toList();
            collegeCourseMappingRepository.delete(collegeCourseMappingBean.getUnumCollegeId(), collegeCourseMappingBean.getUnumCfacultyId(), courseIdsToDelete);
        }

        // Courses to add
        List<CmstCollegeCourseMst> coursesToAdd = new ArrayList<>();
        for (Long courseId: mappedCoursesSet) {
            CmstCollegeCourseMst collegeCourseMst = BeanUtils.copyProperties(collegeCourseMappingBean, CmstCollegeCourseMst.class);
            collegeCourseMst.setUnumColCourseId(collegeCourseMappingRepository.getNextId());
            collegeCourseMst.setUnumCourseId(courseId);
            coursesToAdd.add(collegeCourseMst);
        }

        if (!coursesToAdd.isEmpty())
            collegeCourseMappingRepository.saveAll(coursesToAdd);

        return ServiceResponse.builder()
                .status(1)
                .message(language.saveSuccess("College Course Mapping"))
                .build();
    }
}
