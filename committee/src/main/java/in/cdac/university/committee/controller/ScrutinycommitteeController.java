package in.cdac.university.committee.controller;

import in.cdac.university.committee.service.ScrutinycommitteeService;
import in.cdac.university.committee.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/committee/scrutiny")
public class ScrutinycommitteeController {

    @Autowired
    ScrutinycommitteeService scrutinycommitteeService;

    @GetMapping("/committeeRuleset/data/{committeeRsId}/{facultyId}")
    public ResponseEntity<?> getComRsDataForScrutinyComCreation(@PathVariable("committeeRsId") Long committeeRsId,
                                                                @PathVariable("facultyId") Integer facultyId) {
        return ResponseHandler.generateResponse(
            scrutinycommitteeService.getComRsDataForScrutinyComCreation(committeeRsId, facultyId)
        );
    }
}
