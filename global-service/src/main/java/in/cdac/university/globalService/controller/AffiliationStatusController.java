package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.service.AffiliationStatusService;
import in.cdac.university.globalService.util.ComboUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/global/affiliationStatus")
public class AffiliationStatusController {

    @Autowired
    private AffiliationStatusService affiliationStatusService;

    @GetMapping("combo")
    public ResponseEntity<?> affiliationStatusCombo() throws IllegalAccessException {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(
                        affiliationStatusService.getAffiliationStatus()
                )
        );
    }
}
