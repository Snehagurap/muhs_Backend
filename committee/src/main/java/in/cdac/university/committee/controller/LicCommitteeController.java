package in.cdac.university.committee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.cdac.university.committee.service.LicCommitteeRuleSetMstService;

@RestController
@RequestMapping("/committee/lic")
public class LicCommitteeController {
	
    @Autowired
	private LicCommitteeMstService licCommitteeMstService;


}
