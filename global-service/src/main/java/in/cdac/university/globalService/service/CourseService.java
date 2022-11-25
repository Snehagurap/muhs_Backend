package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.CourseBean;
import in.cdac.university.globalService.entity.GmstCourseMst;
import in.cdac.university.globalService.exception.ApplicationException;
import in.cdac.university.globalService.repository.CourseRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.Language;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private Language language;

    public List<CourseBean> listPageData(int status) throws Exception {
        return BeanUtils.copyListProperties(
                courseRepository.listPageData(status, RequestUtility.getUniversityId()),
                CourseBean.class
        );
    }

    @Transactional
    public ServiceResponse save(CourseBean courseBean) {
        // Duplicate check
        List<GmstCourseMst> courseList = courseRepository.findByUnumIsvalidInAndUnumUnivIdAndUstrCourseFnameIgnoreCase(
                List.of(1, 2), courseBean.getUnumUnivId(), courseBean.getUstrCourseFname()
        );

        if (!courseList.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("Course", courseBean.getUstrCourseFname()));
        }

        GmstCourseMst courseMst = BeanUtils.copyProperties(courseBean, GmstCourseMst.class);
        Long courseId = courseRepository.getNextId();
        courseMst.setUnumCourseId(courseId);
        courseRepository.save(courseMst);

        return ServiceResponse.builder()
                .status(1)
                .message(language.saveSuccess("Course"))
                .build();
    }

    public ServiceResponse getCourse(Long courseId) throws Exception {
        Optional<GmstCourseMst> courseMstOptional = courseRepository.findByUnumIsvalidInAndUnumUnivIdAndUnumCourseId(
                List.of(1, 2), RequestUtility.getUniversityId(), courseId
        );

        if (courseMstOptional.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Course", courseId));
        }

        return ServiceResponse.builder()
                .status(1)
                .responseObject(BeanUtils.copyProperties(courseMstOptional.get(), CourseBean.class))
                .build();
    }

    @Transactional
    public ServiceResponse update(CourseBean courseBean) {
        if (courseBean.getUnumCourseId() == null) {
            return ServiceResponse.errorResponse((language.mandatory("Course Id")));
        }

        // Duplicate Check
        List<GmstCourseMst> courseMsts = courseRepository.findByUnumIsvalidInAndUnumUnivIdAndUstrCourseFnameIgnoreCaseAndUnumCourseIdNot(
                List.of(1, 2), courseBean.getUnumUnivId(), courseBean.getUstrCourseFname(), courseBean.getUnumCourseId()
        );

        if (!courseMsts.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("College", courseBean.getUstrCourseFname()));
        }

        // Create Log
        int noOfRecordsAffected = courseRepository.createLog(List.of(courseBean.getUnumCourseId()));
        if (noOfRecordsAffected == 0) {
            throw new ApplicationException(language.notFoundForId("Course", courseBean.getUnumCourseId()));
        }

        // Save new Data
        GmstCourseMst courseMst = BeanUtils.copyProperties(courseBean, GmstCourseMst.class);
        courseRepository.save(courseMst);

        return ServiceResponse.builder()
                .status(1)
                .message(language.updateSuccess("Course"))
                .build();
    }

    @Transactional
    public ServiceResponse delete(CourseBean courseBean, Long[] idsToDelete) {
        if (idsToDelete == null || idsToDelete.length == 0) {
            return ServiceResponse.errorResponse(language.mandatory("Course Id"));
        }

        List<GmstCourseMst> courseMsts = courseRepository.findByUnumIsvalidInAndUnumUnivIdAndUnumCourseIdIn(
                List.of(1, 2), courseBean.getUnumUnivId(), List.of(idsToDelete)
        );

        if (courseMsts.size() != idsToDelete.length) {
            return ServiceResponse.errorResponse(language.notFoundForId("Course", Arrays.toString(idsToDelete)));
        }

        // Create Log
        int noOfRowsAffected = courseRepository.createLog(List.of(idsToDelete));
        if (noOfRowsAffected != idsToDelete.length) {
            throw new ApplicationException(language.deleteError("Course"));
        }

        courseMsts.forEach(course -> {
            course.setUnumIsvalid(0);
            course.setUdtEntryDate(courseBean.getUdtEntryDate());
            course.setUnumEntryUid(course.getUnumEntryUid());
        });

        courseRepository.saveAll(courseMsts);
        return ServiceResponse.builder()
                .status(1)
                .message(language.deleteSuccess("Course"))
                .build();
    }

    public List<CourseBean> getCousreByFacultyId(Integer facultyId) throws Exception {
        return BeanUtils.copyListProperties(
                courseRepository.findByUnumCfacultyIdAndUnumIsvalidAndUnumUnivId(
                        facultyId, 1,RequestUtility.getUniversityId()), CourseBean.class
        );
    }
}
