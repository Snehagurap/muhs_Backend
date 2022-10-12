package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.service.EmployeeCurrentDetailService;
import in.cdac.university.globalService.service.EmployeeProfileService;
import in.cdac.university.globalService.service.EmployeeService;
import in.cdac.university.globalService.util.ComboUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/global/teacher")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeProfileService employeeProfileService;

    @Autowired
    private EmployeeCurrentDetailService employeeCurrentDetailService;

    @GetMapping("listPage/{status}")
    public ResponseEntity<?> listPageData(@PathVariable("status") int status) throws Exception {
        return ResponseHandler.generateOkResponse(
                employeeService.listPageData(status)
        );
    }

    @GetMapping("all")
    public ResponseEntity<?> getAllTeachersCombo() throws Exception {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(employeeService.getAllTeachers())
        );
    }

    @PostMapping("profile")
    public ResponseEntity<?> getTeachersProfileByFaculty(@RequestBody List<Integer> facultyIds) throws Exception {
        return ResponseHandler.generateOkResponse(
                employeeProfileService.getTeachersProfilesByFaculty(facultyIds)
        );
    }

    @PostMapping("currentDetail")
    public ResponseEntity<?> getTeachersCurrentDetailsByDesignation(@RequestBody List<Integer> designationIds) throws Exception {
        return ResponseHandler.generateOkResponse(
                employeeCurrentDetailService.getEmpCurrentDetailsByDesignation(designationIds)
        );
    }

    @GetMapping("committeeMember/combo/{eventId}")
    public ResponseEntity<?> combo(@PathVariable("eventId") Long eventId) throws Exception {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(employeeService.getCommitteeMembers(eventId))
        );
    }
}
