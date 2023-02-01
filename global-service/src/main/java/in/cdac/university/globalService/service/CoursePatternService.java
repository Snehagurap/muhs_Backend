package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.CoursePatternBean;
import in.cdac.university.globalService.controller.GmstCourseTypeMst;
import in.cdac.university.globalService.entity.*;
import in.cdac.university.globalService.exception.ApplicationException;
import in.cdac.university.globalService.repository.*;
import in.cdac.university.globalService.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CoursePatternService {

    @Autowired
    private CoursePatternRepository coursePatternRepository;

    @Autowired
    private Language language;

    @Autowired
    private FtpUtility ftpUtility;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseTypeRepository courseTypeRepository;

    @Autowired
    private StreamRepository streamRepository;

    public ServiceResponse getCoursePattern(Long coursePatId) {
        Optional<GmstCoursePatternDtl> coursePatOptional = coursePatternRepository.findByUnumIsvalidAndUnumCoursePatId(1, coursePatId);

        if (coursePatOptional.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Course Pattern Id ", coursePatId));
        }

        return ServiceResponse.builder()
                .status(1)
                .responseObject(BeanUtils.copyProperties(coursePatOptional.get(), CoursePatternBean.class))
                .build();
    }

    @Transactional
    public ServiceResponse save(CoursePatternBean coursePatternBean) {
        //for duplicate
        List<GmstCoursePatternDtl> coursePatternDtlList = coursePatternRepository.findByUnumCourseIdAndUstrCoursePatFnameIgnoreCaseAndUnumIsvalid(
                coursePatternBean.getUnumCourseId(), coursePatternBean.getUstrCoursePatFname(), 1
        );
        if (!coursePatternDtlList.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("Course Pattern", coursePatternBean.getUstrCoursePatFname()));
        }

        if (coursePatternBean.getUstrCoursePatDocPath() != null && !coursePatternBean.getUstrCoursePatDocPath().isBlank()) {
            boolean isFileMoved = ftpUtility.moveFileFromTempToFinalDirectory(coursePatternBean.getUstrCoursePatDocPath());
            if (!isFileMoved) {
                throw new ApplicationException("Unable to upload file");
            }
        }
        GmstCoursePatternDtl coursePatternDtl = BeanUtils.copyProperties(coursePatternBean, GmstCoursePatternDtl.class);
        Long coursePatId = coursePatternRepository.getNextId();
        coursePatternDtl.setUnumCoursePatId(coursePatId);
        coursePatternRepository.save(coursePatternDtl);

        return ServiceResponse.builder()
                .status(1)
                .message(language.saveSuccess("Course Pattern"))
                .build();
    }


    @Transactional
    public ServiceResponse update(CoursePatternBean coursePatternBean) {
        if (coursePatternBean.getUnumCoursePatId() == null) {
            return ServiceResponse.errorResponse((language.mandatory("Course Pattern Id")));
        }

        List<GmstCoursePatternDtl> coursePatternDtlList = coursePatternRepository.findByUnumCourseIdAndUstrCoursePatFnameIgnoreCaseAndUnumIsvalid(
                coursePatternBean.getUnumCourseId(), coursePatternBean.getUstrCoursePatFname(), 1
        );

        if (!coursePatternDtlList.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("Course Pattern", coursePatternBean.getUstrCoursePatFname()));
        }

        if (coursePatternBean.getUstrCoursePatDocPath() != null && !coursePatternBean.getUstrCoursePatDocPath().isBlank()) {

            boolean isFileExist = ftpUtility.isFileExists(coursePatternBean.getUstrCoursePatDocPath());
            if (!isFileExist) {
                boolean isFileMoved = ftpUtility.moveFileFromTempToFinalDirectory(coursePatternBean.getUstrCoursePatDocPath());
                if (!isFileMoved) {
                    throw new ApplicationException("Unable to upload file");
                }
            }
        }
        // Create Log
        int noOfRecordsAffected = coursePatternRepository.createLog(List.of((coursePatternBean.getUnumCoursePatId())));
        if (noOfRecordsAffected == 0) {
            throw new ApplicationException(language.notFoundForId("Course Pattern Id", coursePatternBean.getUnumCoursePatId()));
        }

        //save new record
        coursePatternRepository.save(BeanUtils.copyProperties(coursePatternBean, GmstCoursePatternDtl.class));

        return ServiceResponse.builder()
                .status(1)
                .message(language.updateSuccess("Course Pattern"))
                .build();
    }


    public List<CoursePatternBean> listPage() throws Exception {

        List<GmstCoursePatternDtl> allCoursePatternDtl = coursePatternRepository.getAllCoursePatterns();

        Map<Integer, String> facultyNameMap = facultyRepository.getAllFaculty(RequestUtility.getUniversityId())
                .stream()
                .collect(Collectors.toMap(GmstCoursefacultyMst::getUnumCfacultyId, GmstCoursefacultyMst::getUstrCfacultyFname));

        Map<Long, String> courseNameMap = courseRepository.getAllCourses(RequestUtility.getUniversityId())
                .stream()
                .collect(Collectors.toMap(GmstCourseMst::getUnumCourseId, GmstCourseMst::getUstrCourseFname));

        Map<Long, String> courseTypeNameMap = courseTypeRepository.getAllCourseTypes(RequestUtility.getUniversityId())
                .stream()
                .collect(Collectors.toMap(GmstCourseTypeMst::getUnumCtypeId, GmstCourseTypeMst::getUstrCtypeFname));

        Map<Long, String> streamNameMap = streamRepository.findByUnumIsvalidAndUnumUnivId(1, RequestUtility.getUniversityId())
                .stream()
                .collect(Collectors.toMap(GmstStreamMst::getUnumStreamId, GmstStreamMst::getUstrStreamFname));

        return allCoursePatternDtl.stream()
                .map(coursePattern -> {
                    CoursePatternBean coursePatternBean = BeanUtils.copyProperties(coursePattern, CoursePatternBean.class);
                    coursePatternBean.setCourseTypeName(courseTypeNameMap.get(coursePattern.getUnumCtypeId()));
                    coursePatternBean.setCourseName(courseNameMap.get(coursePattern.getUnumCourseId()));
                    coursePatternBean.setFacultyName(facultyNameMap.get(coursePattern.getUnumCfacultyId()));
                    coursePatternBean.setStreamName(streamNameMap.get(coursePattern.getUnumStreamId()));
                    return coursePatternBean;
                }).toList();
    }


    @Transactional
    public ServiceResponse delete(CoursePatternBean coursePatternBean, Long[] idsToDelete) {
        if (idsToDelete == null || idsToDelete.length == 0) {
            return ServiceResponse.errorResponse(language.mandatory("Course Pattern Id"));
        }

        List<GmstCoursePatternDtl> coursePatternDtls = coursePatternRepository.findByUnumIsvalidAndUnumCoursePatIdIn(
                1, List.of(idsToDelete)
        );

        if (coursePatternDtls.size() != idsToDelete.length) {
            return ServiceResponse.errorResponse(language.notFoundForId("Course Pattern ", Arrays.toString(idsToDelete)));
        }

        // Create Log
        int noOfRowsAffected = coursePatternRepository.createLog(List.of(idsToDelete));
        if (noOfRowsAffected != idsToDelete.length) {
            throw new ApplicationException(language.deleteError("Course Pattern"));
        }

        coursePatternDtls.forEach(coursePattern -> {
            coursePattern.setUnumIsvalid(0);
            coursePattern.setUdtEntryDate(coursePatternBean.getUdtEntryDate());
        });

        coursePatternRepository.saveAll(coursePatternDtls);

        return ServiceResponse.builder()
                .status(1)
                .message(language.deleteSuccess("Course Pattern"))
                .build();
    }

    public List<CoursePatternBean> getAllCoursePatterns() {
        return BeanUtils.copyListProperties(
                coursePatternRepository.getAllCoursePatterns(),
                CoursePatternBean.class
        );
    }
}


