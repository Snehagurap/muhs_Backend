package in.cdac.university.committee.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.cdac.university.committee.bean.LicCommitteeBean;
import in.cdac.university.committee.bean.LicCommitteeRuleSetBeanMst;
import in.cdac.university.committee.service.LicCommitteeMstService;
import in.cdac.university.committee.service.LicCommitteeRuleSetMstService;
import in.cdac.university.committee.util.ListPageUtility;
import in.cdac.university.committee.util.RequestUtility;
import in.cdac.university.committee.util.ResponseHandler;

@RestController
@RequestMapping("/committee/lic")
public class LicCommitteeController {
	
    @Autowired
	private LicCommitteeMstService licCommitteeMstService;
    
    
    @PostMapping("saveLicCommittee")
    public ResponseEntity<?> saveLicCommittee(@Valid @RequestBody LicCommitteeBean licCommitteeBean) throws Exception {
    	
    	licCommitteeBean.setUnumIsValid(1);
    	licCommitteeBean.setUnumUnivId(RequestUtility.getUniversityId());
    	licCommitteeBean.setUnumEntryUid(RequestUtility.getUserId());
    	licCommitteeBean.setUdtEntryDate(new Date());
        return ResponseHandler.generateResponse(
        		licCommitteeMstService.saveLicCommittee(licCommitteeBean)
        );
    }
    
    @GetMapping("/licCommitteeRuleset/data/{licCommitteeRsId}/{facultyId}")
    public ResponseEntity<?> getComRsDataForScrutinyComCreation(@PathVariable("licCommitteeRsId") Long licCommitteeRsId,
                                                                @PathVariable("facultyId") Integer facultyId) {
        return ResponseHandler.generateResponse(
        		licCommitteeMstService.getComRsDataForScrutinyComCreation(licCommitteeRsId, facultyId)
        );
    }

    @GetMapping("allLicCommittee")
    public ResponseEntity<?> getAllLicCommittee() throws IllegalAccessException {
    	return ResponseHandler.generateOkResponse(
                ListPageUtility.generateListPageData (licCommitteeMstService.getAllLicCommitee()) );
    }
}
