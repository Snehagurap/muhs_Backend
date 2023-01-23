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

import in.cdac.university.committee.bean.LicCommitteeRuleSetBeanMst;
import in.cdac.university.committee.service.LicCommitteeRuleSetMstService;
import in.cdac.university.committee.util.ComboUtility;
import in.cdac.university.committee.util.ListPageUtility;
import in.cdac.university.committee.util.RequestUtility;
import in.cdac.university.committee.util.ResponseHandler;

@RestController
@RequestMapping("/committee/licRuleset")
public class LicRuleSetController {

    @Autowired
	private LicCommitteeRuleSetMstService licCommitteeRuleSetMstService;

    	@PostMapping("saveLicCommitteeRule")
	    public ResponseEntity<?> saveLicCommitteeRule(@Valid @RequestBody LicCommitteeRuleSetBeanMst licCommitteeRuleSetBeanMst) throws Exception {
	    	
	    	licCommitteeRuleSetBeanMst.setUnumIsValid(1);
			licCommitteeRuleSetBeanMst.setUnumUnivId(RequestUtility.getUniversityId());
			licCommitteeRuleSetBeanMst.setUnumEntryUid(RequestUtility.getUserId());
			licCommitteeRuleSetBeanMst.setUdtEntryDate(new Date());
	        return ResponseHandler.generateResponse(
	        		licCommitteeRuleSetMstService.saveLicCommitteeRule(licCommitteeRuleSetBeanMst)
	        );
	    }
    	@PostMapping("updateLicCommitteeRule")
	    public ResponseEntity<?> updateLicCommitteeRule(@Valid @RequestBody LicCommitteeRuleSetBeanMst licCommitteeRuleSetBeanMst) throws Exception {
	    	
	    	licCommitteeRuleSetBeanMst.setUnumIsValid(1);
			System.out.println("isValid>>"+licCommitteeRuleSetBeanMst.getUnumIsValid());
			licCommitteeRuleSetBeanMst.setUnumUnivId(RequestUtility.getUniversityId());
			licCommitteeRuleSetBeanMst.setUnumEntryUid(RequestUtility.getUserId());
			licCommitteeRuleSetBeanMst.setUdtEntryDate(new Date());
	        return ResponseHandler.generateResponse(
	        		licCommitteeRuleSetMstService.updateLicCommitteeRule(licCommitteeRuleSetBeanMst)
	        );
	    }
    	
    	@GetMapping("combo")
    	    public ResponseEntity<?> getLicCommitteeRuleCombo() throws Exception {
    	        return ResponseHandler.generateOkResponse(
    	                ComboUtility.generateComboData(
    	                		licCommitteeRuleSetMstService.getCommitteeCombo()
    	                )
    	        );
    	    }
    	@GetMapping("comboCourseTypeByRsID/{Id}")
	    public ResponseEntity<?> getComboCourseTypeByRsID(@PathVariable("Id") Long unumComRsId) throws Exception {
	        return ResponseHandler.generateOkResponse(
	                             		licCommitteeRuleSetMstService.getComboCourseTypeByRsID(unumComRsId)
	        );
	    }
    	
    	@GetMapping("listPage")
        public ResponseEntity<?> getListPage() throws IllegalAccessException {
            return ResponseHandler.generateOkResponse(
                    ListPageUtility.generateListPageData(licCommitteeRuleSetMstService.getListPageData())
            );
        }
    	
    	@GetMapping("/id/{Id}")
    	public ResponseEntity<?> getCommitteeRuleSetByunumComRsId(@PathVariable("Id") Long unumComRsId) throws Exception {
            return ResponseHandler.generateResponse(
            		licCommitteeRuleSetMstService.getCommitteeRuleSetByUnumComRsId(1,RequestUtility.getUniversityId(),unumComRsId)
            );
        }
}
