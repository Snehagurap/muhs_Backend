package in.cdac.university.committee.controller;

import in.cdac.university.committee.service.CommitteeTypeService;
import in.cdac.university.committee.util.ComboUtility;
import in.cdac.university.committee.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/committee/type")
public class CommitteeTypeController {

    @Autowired
    private CommitteeTypeService committeeTypeService;

    @GetMapping("/combo")
    public ResponseEntity<?> getCommitteeTypeCombo() throws IllegalAccessException {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(
                        committeeTypeService.getAllCommitteeType(1)
                )
        );
    }
}
