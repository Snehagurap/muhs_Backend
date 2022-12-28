package in.cdac.university.globalService.controller;


import in.cdac.university.globalService.service.FacultyStreamDtlService;
import in.cdac.university.globalService.util.ComboUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/global/facultyStreamDtl")
public class FacultyStreamDtlController {

    @Autowired
    FacultyStreamDtlService facultyStreamDtlService;

    @GetMapping("combo/{facultyId}")
    public ResponseEntity<?> getStreamComboByFaculty(@PathVariable("facultyId") Integer facultyId) throws Exception {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(facultyStreamDtlService.getStreamComboByFaculty(facultyId))
        );
    }
}
