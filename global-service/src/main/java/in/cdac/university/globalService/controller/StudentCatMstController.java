package in.cdac.university.globalService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import in.cdac.university.globalService.service.StudentCatMstService;
import in.cdac.university.globalService.util.ComboUtility;
import in.cdac.university.globalService.util.ResponseHandler;

@RestController
@RequestMapping("/global/StudentCat")
public class StudentCatMstController {

	@Autowired
	private StudentCatMstService stuCatMstService;
	
	@GetMapping("combo")
    public ResponseEntity<?> getAllCategoryCombo() throws Exception {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(stuCatMstService.getAllCategoryCombo())
        );
    }
	@GetMapping("comboSubCat/{id}")
    public ResponseEntity<?> getAllCategoryCombo(@PathVariable (value = "id") Long subCatId) throws Exception {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(stuCatMstService.getSubCategoryCombo(subCatId))
        );
    }
	
}
