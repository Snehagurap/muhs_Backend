package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.bean.CollegeCourseMappingBean;
import in.cdac.university.globalService.service.CollegeCourseMappingService;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/global/collegeCourse")
public class CollegeCourseMappingController {

    @Autowired
    private CollegeCourseMappingService collegeCourseMappingService;

    @GetMapping("mapping/{collegeId}/{facultyId}")
    public ResponseEntity<?> getMappingDetails(@PathVariable("collegeId") Long collegeId, @PathVariable("facultyId") Integer facultyId) throws Exception {
        return ResponseHandler.generateResponse(
                collegeCourseMappingService.getMappingDetails(collegeId, facultyId, RequestUtility.getUniversityId())
        );
    }

    @PostMapping("mapping/save")
    public ResponseEntity<?> saveMappingDetails(@Valid @RequestBody CollegeCourseMappingBean collegeCourseMappingBean) throws Exception {
        collegeCourseMappingBean.setUnumIsvalid(1);
        collegeCourseMappingBean.setUnumEntryUid(RequestUtility.getUserId());
        collegeCourseMappingBean.setUnumUnivId(RequestUtility.getUniversityId());
        collegeCourseMappingBean.setUdtEntryDate(new Date());
        collegeCourseMappingBean.setUdtEffFrom(new Date());
        return ResponseHandler.generateResponse(
                collegeCourseMappingService.saveMappingDetails(collegeCourseMappingBean)
        );
    }
}
