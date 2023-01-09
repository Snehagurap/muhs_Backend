package in.cdac.university.committee.controller;

import in.cdac.university.committee.bean.ScrutinycommitteeBean;
import in.cdac.university.committee.service.ScrutinycommitteeService;
import in.cdac.university.committee.util.RequestUtility;
import in.cdac.university.committee.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

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

    @PostMapping("save")
    public ResponseEntity<?> saveScrutinyCommittee(@Valid @RequestBody ScrutinycommitteeBean scrutinycommitteeBean) {
        scrutinycommitteeBean.setUnumEntryUid(RequestUtility.getUserId());
        scrutinycommitteeBean.setUdtEntryDate(new Date());
        scrutinycommitteeBean.setUnumUnivId(RequestUtility.getUniversityId());
        scrutinycommitteeBean.setUnumIsvalid(1);
        return ResponseHandler.generateResponse(
                scrutinycommitteeService.saveScrutinyCommittee(scrutinycommitteeBean)
        );
    }
}
