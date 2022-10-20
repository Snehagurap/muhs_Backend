package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.service.ApplicantTypeService;
import in.cdac.university.globalService.util.ComboUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/global/applicant/type")
public class ApplicantTypeController {

    @Autowired
    private ApplicantTypeService applicantTypeService;

    @GetMapping("combo")
    public ResponseEntity<?> getApplicantTypeCombo() throws IllegalAccessException {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(applicantTypeService.getApplicantTypes())
        );
    }
}
