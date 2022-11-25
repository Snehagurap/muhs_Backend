package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.bean.CourseSubjectMappingBean;
import in.cdac.university.globalService.service.CourseSubjectMappingService;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/global/courseSubject")
public class CourseSubjectMappingController {

    @Autowired
    CourseSubjectMappingService courseSubjectMappingService;

    @GetMapping("mapping/{courseId}")
    public ResponseEntity<?> getMappingDetails(@PathVariable("courseId") Long courseId) throws Exception {
        return ResponseHandler.generateResponse(
                courseSubjectMappingService.getMappingDetails(courseId, RequestUtility.getUniversityId())
        );
    }

    @PostMapping("mapping/save")
    public ResponseEntity<?> saveMappingDetails(@Valid @RequestBody CourseSubjectMappingBean courseSubjectMappingBean) throws Exception {
        courseSubjectMappingBean.setUnumIsvalid(1);
        courseSubjectMappingBean.setUnumEntryUid(RequestUtility.getUserId());
        courseSubjectMappingBean.setUnumUnivId(RequestUtility.getUniversityId());
        courseSubjectMappingBean.setUdtEntryDate(new Date());
        courseSubjectMappingBean.setUdtEffFrom(new Date());
        return ResponseHandler.generateResponse(
                courseSubjectMappingService.saveMappingDetails(courseSubjectMappingBean)
        );
    }
}
