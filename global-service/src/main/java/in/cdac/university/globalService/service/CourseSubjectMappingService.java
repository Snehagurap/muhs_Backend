package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.ComboBean;
import in.cdac.university.globalService.bean.CourseSubjectMappingBean;
import in.cdac.university.globalService.bean.MappedComboBean;
import in.cdac.university.globalService.entity.GmstCourseSubjectDtl;
import in.cdac.university.globalService.entity.GmstSubjectMst;
import in.cdac.university.globalService.repository.CourseSubjectMappingRepository;
import in.cdac.university.globalService.repository.SubjectRepository;
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
public class CourseSubjectMappingService {

    @Autowired
    CourseSubjectMappingRepository courseSubjectMappingRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    private Language language;

    public ServiceResponse getMappingDetails(Long courseId, Integer universityId) {
        List<GmstSubjectMst> allSubjects = subjectRepository.getAllSubjects(universityId);

        Set<Long> mappedSubjectIds = courseSubjectMappingRepository.findByUnumCourseIdAndUnumIsvalidAndUnumUnivId(
                courseId, 1, universityId
        ).stream().map(GmstCourseSubjectDtl::getUnumSubId).collect(Collectors.toSet());

        List<ComboBean> mappedSubjects = new ArrayList<>();
        List<ComboBean> notMappedSubjects = new ArrayList<>();

        for(GmstSubjectMst subjectMst: allSubjects) {
            ComboBean comboBean = new ComboBean(subjectMst.getUnumSubId().toString(), subjectMst.getUstrSubFname());
            if(mappedSubjectIds.contains(subjectMst.getUnumSubId())) {
                mappedSubjects.add(comboBean);
            }
            else{
                notMappedSubjects.add(comboBean);
            }
        }

        return ServiceResponse.successObject(new MappedComboBean(mappedSubjects, notMappedSubjects));
    }


    @Transactional
    public ServiceResponse saveMappingDetails(CourseSubjectMappingBean courseSubjectMappingBean) {

        // Get existing Mapping Details
        List<GmstCourseSubjectDtl> alreadyMappedSubjects = courseSubjectMappingRepository.findByUnumCourseIdAndUnumIsvalidAndUnumUnivId(
                courseSubjectMappingBean.getUnumCourseId(), 1, courseSubjectMappingBean.getUnumUnivId()
        );

        Set<Long> mappedSubjectsSet = new HashSet<>(courseSubjectMappingBean.getMappedSubjects());

        // Subjects to delete
        List<GmstCourseSubjectDtl> subjectsToDelete = new ArrayList<>();
        for(GmstCourseSubjectDtl subjectDtl : alreadyMappedSubjects) {
            if (mappedSubjectsSet.contains(subjectDtl.getUnumSubId())) {
                mappedSubjectsSet.remove(subjectDtl.getUnumSubId());
            } else
                subjectsToDelete.add(subjectDtl);
        }

        if(!subjectsToDelete.isEmpty()) {
            List<Long> subjectIdsToDelete = subjectsToDelete.stream().map(GmstCourseSubjectDtl::getUnumSubId).toList();
            courseSubjectMappingRepository.delete(courseSubjectMappingBean.getUnumCourseId(), subjectIdsToDelete);
        }

        // Subjects to add
        List<GmstCourseSubjectDtl> subjectsToAdd = new ArrayList<>();
        for(Long subjectId : mappedSubjectsSet) {
            GmstCourseSubjectDtl gmstCollegeSubjectDtl = BeanUtils.copyProperties(courseSubjectMappingBean, GmstCourseSubjectDtl.class);
            gmstCollegeSubjectDtl.setUnumCourseSubId(courseSubjectMappingRepository.getNextId());
            gmstCollegeSubjectDtl.setUnumSubId(subjectId);
            subjectsToAdd.add(gmstCollegeSubjectDtl);
        }

        if(!subjectsToAdd.isEmpty()) {
            courseSubjectMappingRepository.saveAll(subjectsToAdd);
        }

        return ServiceResponse.successMessage(language.saveSuccess("Course Subject Mapping"));
    }
}


