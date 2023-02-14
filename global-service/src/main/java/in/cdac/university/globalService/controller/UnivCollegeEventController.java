package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.service.UnivCollegeEventService;
import in.cdac.university.globalService.util.ComboUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/global/univCollegeEvent")
public class UnivCollegeEventController {

    @Autowired
    private UnivCollegeEventService univCollegeEventService;
    @GetMapping("collegeEventCombo")
    public ResponseEntity<?> getCollegeEventcombo() throws Exception {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(univCollegeEventService.getCollegeEventCombo())
        );
    }

}
