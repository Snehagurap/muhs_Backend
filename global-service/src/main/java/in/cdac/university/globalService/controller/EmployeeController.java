package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.bean.CollegeBean;
import in.cdac.university.globalService.bean.EmployeeBean;
import in.cdac.university.globalService.service.EmployeeCurrentDetailService;
import in.cdac.university.globalService.service.EmployeeProfileService;
import in.cdac.university.globalService.service.EmployeeService;
import in.cdac.university.globalService.util.ComboUtility;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
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

    @PostMapping("save")
    public ResponseEntity<?> save(@Valid @RequestBody EmployeeBean employeeBean) throws Exception {
        employeeBean.setUnumIsvalid(1);
        employeeBean.setUdtEntryDate(new Date());
        employeeBean.setUnumUnivId(RequestUtility.getUniversityId());
        employeeBean.setUnumEntryUid(RequestUtility.getUserId());
        return ResponseHandler.generateResponse(
                employeeService.save(employeeBean)
        );
    }

    @GetMapping("{teacherId}")
    public ResponseEntity<?> getTeacherById(@PathVariable("teacherId") Long teacherId) throws Exception {
        return ResponseHandler.generateResponse(
                employeeService.getTeacherById(teacherId)
        );
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@Valid @RequestBody EmployeeBean employeeBean) throws Exception {
        employeeBean.setUdtEntryDate(new Date());
        employeeBean.setUnumUnivId(RequestUtility.getUniversityId());
        employeeBean.setUnumEntryUid(RequestUtility.getUserId());
        return ResponseHandler.generateResponse(
                employeeService.update(employeeBean)
        );
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@RequestBody Long[] idsToDelete) throws Exception {
        EmployeeBean employeeBean = new EmployeeBean();
        employeeBean.setUdtEntryDate(new Date());
        employeeBean.setUnumUnivId(RequestUtility.getUniversityId());
        employeeBean.setUnumEntryUid(RequestUtility.getUserId());
        return ResponseHandler.generateResponse(
                employeeService.delete(employeeBean, idsToDelete)
        );
    }
}
