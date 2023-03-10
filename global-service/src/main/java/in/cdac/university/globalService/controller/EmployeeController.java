package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.bean.EmployeeBean;
import in.cdac.university.globalService.service.EmployeeCurrentDetailService;
import in.cdac.university.globalService.service.EmployeeProfileService;
import in.cdac.university.globalService.service.EmployeeService;
import in.cdac.university.globalService.util.ComboUtility;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import in.cdac.university.globalService.util.ServiceResponse;
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

    @GetMapping("combo")
    public ResponseEntity<?> getAllTeachersCombo() throws Exception {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(employeeService.getAllTeachers())
        );
    }

    @GetMapping("all")
    public ResponseEntity<?> getAllTeachers() throws Exception {
        return ResponseHandler.generateResponse(
            ServiceResponse.successObject(employeeService.getAllTeachers())
        );
    }

    @PostMapping("profile")
    public ResponseEntity<?> getTeachersProfileByFaculty(@RequestBody List<Integer> facultyIds) throws Exception {
        return ResponseHandler.generateOkResponse(
                employeeProfileService.getTeachersProfilesByFaculty(facultyIds)
        );
    }

    @GetMapping("allProfiles")
    public ResponseEntity<?> getAllTeachersProfile() throws Exception {
        return ResponseHandler.generateResponse(
                employeeProfileService.getAllTeachersProfile()
        );
    }


    @PostMapping("currentDetail")
    public ResponseEntity<?> getTeachersCurrentDetailsByDesignation(@RequestBody List<Integer> designationIds) throws Exception {
        return ResponseHandler.generateOkResponse(
                employeeCurrentDetailService.getEmpCurrentDetailsByDesignation(designationIds)
        );
    }

    @GetMapping("allCurrentDetails")
    public ResponseEntity<?> getAllTeachersCurrentDetails() throws Exception {
        return ResponseHandler.generateResponse(
                employeeCurrentDetailService.getAllTeachersCurrentDetails()
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

    // Put mapping replaced by POST
    @PostMapping("update")
    public ResponseEntity<?> update(@Valid @RequestBody EmployeeBean employeeBean) throws Exception {
        employeeBean.setUdtEntryDate(new Date());
        employeeBean.setUnumUnivId(RequestUtility.getUniversityId());
        employeeBean.setUnumEntryUid(RequestUtility.getUserId());
        return ResponseHandler.generateResponse(
                employeeService.update(employeeBean)
        );
    }

    // Delete mapping replaced by POST
    @PostMapping("delete")
    public ResponseEntity<?> delete(@RequestBody Long[] idsToDelete) throws Exception {
        EmployeeBean employeeBean = new EmployeeBean();
        employeeBean.setUdtEntryDate(new Date());
        employeeBean.setUnumUnivId(RequestUtility.getUniversityId());
        employeeBean.setUnumEntryUid(RequestUtility.getUserId());
        return ResponseHandler.generateResponse(
                employeeService.delete(employeeBean, idsToDelete)
        );
    }

    @PostMapping("updateChairmanFlag")
    public ResponseEntity<?> updateChairmanFlag(@RequestBody EmployeeBean employeeBean) {
        return ResponseHandler.generateResponse(
                employeeService.updateChairmanFlag(employeeBean.getEmployeesToFlag())
        );
    }

    @PostMapping("updateMember1Flag")
    public ResponseEntity<?> updateMember1Flag(@RequestBody EmployeeBean employeeBean) {
        return ResponseHandler.generateResponse(
                employeeService.updateMember1Flag(employeeBean.getEmployeesToFlag())
        );
    }

    @PostMapping("updateMember2Flag")
    public ResponseEntity<?> updateMember2Flag(@RequestBody EmployeeBean employeeBean) {
        return ResponseHandler.generateResponse(
                employeeService.updateMember2Flag(employeeBean.getEmployeesToFlag())
        );
    }

    @PostMapping("updateCommitteeSelectionFlag")
    public ResponseEntity<?> updateCommitteeSelectionFlag(@RequestBody EmployeeBean employeeBean) {
        return ResponseHandler.generateResponse(
                employeeService.updateCommitteeSelectionFlag(employeeBean.getEmployeesToFlag())
        );
    }

    @PostMapping("saveAllTeachersDtl")
    public ResponseEntity<?> saveAll(@Valid @RequestBody List<EmployeeBean> employeeBeanList) throws Exception {
        return ResponseHandler.generateResponse(
                employeeService.saveCollegeTeachersDtls(employeeBeanList)
        );
    }

    @GetMapping("/getTeachersByCollegeId/{collegeId}")
    public ResponseEntity<?> getTeachersDetailsByCollegeId(@PathVariable("collegeId") Long collegeId) throws Exception {

            return ResponseHandler.generateOkResponse(
                    employeeService.getTeacherDetailsByCollegeId(collegeId)
            );

    }
    @PostMapping("updateTeacher")
    public ResponseEntity<?> updateTeacherByCollegeId(@Valid @RequestBody EmployeeBean employeeBean) throws Exception {
        employeeBean.setUnumEntryUid(RequestUtility.getUserId());
        employeeBean.setUnumUnivId(RequestUtility.getUniversityId());
        employeeBean.setUdtEntryDate(new Date());
        employeeBean.setUnumIsvalid(1);
        return ResponseHandler.generateResponse(
                employeeService.updateTeacherByCollegeId(employeeBean)
        );
    }
}
