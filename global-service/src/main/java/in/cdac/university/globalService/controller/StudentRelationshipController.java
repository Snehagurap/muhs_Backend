package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.service.StudentRelationshipService;
import in.cdac.university.globalService.util.ComboUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/global/studentRelationship")
public class StudentRelationshipController {

    @Autowired
    private StudentRelationshipService studentRelationshipService;

    @GetMapping("combo")
    public @ResponseBody ResponseEntity<?> getStuRelationCombo() throws Exception {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(studentRelationshipService.getAllStuRelations())
        );
    }
}
