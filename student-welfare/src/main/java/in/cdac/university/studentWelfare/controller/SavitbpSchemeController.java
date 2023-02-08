package in.cdac.university.studentWelfare.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.cdac.university.studentWelfare.util.*;
import in.cdac.university.studentWelfare.util.ListPageUtility;
import in.cdac.university.studentWelfare.util.ResponseHandler;
import in.cdac.university.studentWelfare.bean.SavitbpSchemeApplMstBean;
import in.cdac.university.studentWelfare.service.SavitbpSchemeService;


@RestController
@RequestMapping("/sw/SavitbpScheme")
public class SavitbpSchemeController {
	
	
	@Autowired
	private SavitbpSchemeService savitbpSchemeService;
	
	@PostMapping("save")
    public ResponseEntity<?> saveSavitbpScheme(@Valid @RequestBody SavitbpSchemeApplMstBean savitbpSchemeApplMstBean) throws Exception {
        return ResponseHandler.generateResponse(savitbpSchemeService.saveSavitbpScheme(savitbpSchemeApplMstBean));
	}
	
	@PostMapping("draftSave")
    public ResponseEntity<?> saveChecklist(@Valid @RequestBody SavitbpSchemeApplMstBean savitbpSchemeApplMstBean) throws Exception {
        return ResponseHandler.generateResponse(savitbpSchemeService.saveDraftSavitbpScheme(savitbpSchemeApplMstBean));
	}

	@GetMapping("draft/{studentEnrollmentNo}")
    public ResponseEntity<?> getSchemeDraft(@PathVariable (value = "studentEnrollmentNo") String studentEnrollmentNo) throws Exception {
		return ResponseHandler.generateResponse(savitbpSchemeService.getDraftSavitbpScheme(studentEnrollmentNo));
	}
	
	@GetMapping("retrive/{studentEnrollmentNo}")
    public ResponseEntity<?> getSchemeRetrive(@PathVariable (value = "studentEnrollmentNo") String studentEnrollmentNo) throws Exception {
		return ResponseHandler.generateResponse(savitbpSchemeService.getSavitbpScheme(studentEnrollmentNo));
	}
	
	
	@GetMapping("getAllStudentForScheme")
    public ResponseEntity<?> getAllStudentForScheme() throws Exception {
		return ResponseHandler.generateOkResponse(ListPageUtility.generateListPageData(savitbpSchemeService.getAllStudentForScheme()));
	}
}
		   
	
	


