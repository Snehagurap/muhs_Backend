package in.cdac.university.globalService.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import in.cdac.university.globalService.bean.StudentMasterBean;
import in.cdac.university.globalService.service.StudentService;
import in.cdac.university.globalService.util.ResponseHandler;

@RestController
@RequestMapping("/global/Student")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@PostMapping("updateStudent")
    public ResponseEntity<?> saveProcessDtl(@Valid @RequestBody StudentMasterBean studentMasterBean) throws Exception {

        return ResponseHandler.generateResponse(studentService.saveUpdate(studentMasterBean));
    }
	
	@GetMapping("getStudentDetails/{studentEnrollmentNo}")
    public ResponseEntity<?> getStudentDetails(@PathVariable (value = "studentEnrollmentNo") String studentEnrollmentNo) throws Exception {
          return ResponseHandler.generateResponse(
        			studentService.getStudentDetails(studentEnrollmentNo));
    }

}
