package in.cdac.university.committee.controller;

import in.cdac.university.committee.bean.CommitteeRulesetBean;
import in.cdac.university.committee.service.CommitteeRulesetService;
import in.cdac.university.committee.util.ComboUtility;
import in.cdac.university.committee.util.ListPageUtility;
import in.cdac.university.committee.util.RequestUtility;
import in.cdac.university.committee.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/committee/ruleset")
public class CommitteeRulesetController {

    @Autowired
    CommitteeRulesetService committeeRulesetService;

    @GetMapping("listPage")
    public ResponseEntity<?> getListPage() throws IllegalAccessException {
        return ResponseHandler.generateOkResponse(
                ListPageUtility.generateListPageData(committeeRulesetService.getListPageData())
        );
    }

    @GetMapping("combo")
    public ResponseEntity<?> getCommitteeRulesetCombo() throws Exception {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(committeeRulesetService.getCommitteeRulesetCombo())
        );
    }

    @PostMapping("save")
    public ResponseEntity<?> saveCommitteeRuleset(@Valid @RequestBody CommitteeRulesetBean committeeRulesetBean) {
        committeeRulesetBean.setUnumUnivId(RequestUtility.getUniversityId());
        committeeRulesetBean.setUnumIsvalid(1);
        committeeRulesetBean.setUnumEntryUid(RequestUtility.getUserId());
        committeeRulesetBean.setUdtEntryDate(new Date());
        committeeRulesetBean.setUdtEffFrom(new Date());
        return ResponseHandler.generateResponse(
                committeeRulesetService.saveCommitteeRuleset(committeeRulesetBean)
        );
    }

    @GetMapping("{committeeRulesetId}")
    public ResponseEntity<?> getCommitteeRulesetById(@PathVariable("committeeRulesetId") Long committeeRulesetId) {
        return ResponseHandler.generateResponse(
                committeeRulesetService.getCommitteeRulesetById(committeeRulesetId)
        );
    }

    @PostMapping("update")
    public ResponseEntity<?> updateCommitteeRuleset(@Valid @RequestBody CommitteeRulesetBean committeeRulesetBean) {
        committeeRulesetBean.setUnumUnivId(RequestUtility.getUniversityId());
        committeeRulesetBean.setUnumIsvalid(1);
        committeeRulesetBean.setUnumEntryUid(RequestUtility.getUserId());
        committeeRulesetBean.setUdtEntryDate(new Date());
        committeeRulesetBean.setUdtEffFrom(new Date());
        return ResponseHandler.generateResponse(
                committeeRulesetService.updateCommitteeRuleset(committeeRulesetBean)
        );
    }

}
