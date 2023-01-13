package in.cdac.university.committee.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.cdac.university.committee.bean.LicCommitteeRuleSetBeanMst;
import in.cdac.university.committee.service.LicCommitteeRuleSetMstService;
import in.cdac.university.committee.util.RequestUtility;
import in.cdac.university.committee.util.ResponseHandler;



@RestController
@RequestMapping("/licCommittee/ruleset")
public class LicCommitteeRuleSetMstController {

	@Autowired
	private LicCommitteeRuleSetMstService licCommitteeRuleSetMstService;
	
	@PostMapping("saveLicCommitteeRule")
    public ResponseEntity<?> saveLicCommitteeRule(@Valid @RequestBody LicCommitteeRuleSetBeanMst licCommitteeRuleSetBeanMst) throws Exception {
    	 
		licCommitteeRuleSetBeanMst.setUnumUnivId(RequestUtility.getUniversityId());
		licCommitteeRuleSetBeanMst.setUnumEntryUid(RequestUtility.getUserId());
		licCommitteeRuleSetBeanMst.setUdtEntryDate(new Date());
        return ResponseHandler.generateResponse(
        		licCommitteeRuleSetMstService.saveLicCommitteeRule(licCommitteeRuleSetBeanMst)
        );
    }
	
}
