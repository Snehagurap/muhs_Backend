package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.service.FacultyService;
import in.cdac.university.globalService.util.ComboUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/global/faculty")
public class FacultyController {

    @Autowired
    private FacultyService facultyService;

    @GetMapping("combo")
    public ResponseEntity<?> getFacultyCombo() throws Exception {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(
                        facultyService.getAllFaculty()
                )
        );
    }

    @GetMapping("all")
    public ResponseEntity<?> allFaculties() throws Exception {
        return ResponseHandler.generateResponse(
                facultyService.allFaculties()
        );
    }
}
