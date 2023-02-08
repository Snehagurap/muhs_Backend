package in.cdac.university.studentWelfare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.cdac.university.studentWelfare.service.SchemeMstService;

@RestController
@RequestMapping("/sw/Scheme/")
public class SchemeMstController {

	
	@Autowired
	private SchemeMstService schemeMstService;
	
	
	@GetMapping("schemeDetail/{schemeNo}")
    public ResponseEntity<?> getSchemeDraft(@PathVariable (value = "schemeNo") int schemeNo) throws Exception {
		return null;
		//getStudentDetailsByScheme use this serive for end point 
	
	}
	
}
