package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.service.CourseTypeService;
import in.cdac.university.globalService.util.ComboUtility;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/global/courseType")
public class CourseTypeController {

    @Autowired
    private CourseTypeService courseTypeService;

    @GetMapping("combo")
    public ResponseEntity<?> getCourseTypeCombo() throws Exception {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(courseTypeService.courseTypes())
        );
    }

    @GetMapping("all")
    public ResponseEntity<?> getAllCourseTypes() throws Exception {
        return ResponseHandler.generateResponse(
                courseTypeService.getAllCourseTypes(RequestUtility.getUniversityId())
        );
    }

}
