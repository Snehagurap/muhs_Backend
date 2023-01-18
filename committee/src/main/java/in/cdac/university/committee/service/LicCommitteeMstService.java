package in.cdac.university.committee.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.cdac.university.committee.bean.LicCommitteeBean;
import in.cdac.university.committee.bean.LicCommitteeDtlBean;
import in.cdac.university.committee.bean.LicCommitteeRuleSetBeanMst;
import in.cdac.university.committee.bean.LicCommitteeRuleSetDtlBean;
import in.cdac.university.committee.entity.GbltLicCommitteeMemberDtl;
import in.cdac.university.committee.entity.GbltLicCommitteeMst;
import in.cdac.university.committee.entity.GbltLicCommitteeRuleSetDtl;
import in.cdac.university.committee.entity.GbltLicCommitteeRuleSetMst;
import in.cdac.university.committee.entity.GbltScrutinycommitteeMemberDtl;
import in.cdac.university.committee.repository.LicCommitteetDtlRespository;
import in.cdac.university.committee.repository.LicCommitteetMstRespository;
import in.cdac.university.committee.util.BeanUtils;
import in.cdac.university.committee.util.Language;
import in.cdac.university.committee.util.RequestUtility;
import in.cdac.university.committee.util.ServiceResponse;

@Service
public class LicCommitteeMstService {

	@Autowired
    private Language language;
    
    @Autowired
    private LicCommitteetMstRespository licCommitteetMstRespository;
    
    @Autowired
    private LicCommitteetDtlRespository licCommitteetDtlRespository;
    
    @Transactional
	public ServiceResponse saveLicCommittee(LicCommitteeBean licCommitteeBean) throws Exception{
		
    	List<GbltLicCommitteeMst> licCommitteeMst = licCommitteetMstRespository.findByUnumIsValidInAndUstrLicNameIgnoreCase(
                List.of(1), licCommitteeBean.getUstrLicName());
        if (!licCommitteeMst.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("Lic Committee", licCommitteeBean.getUstrLicName()));
        }
        GbltLicCommitteeMst gbltLicCommitteeMst = new GbltLicCommitteeMst();
        BeanUtils.copyProperties(licCommitteeBean, gbltLicCommitteeMst);
        gbltLicCommitteeMst.setUnumLicId(licCommitteetMstRespository.getNextId());
        licCommitteetMstRespository.save(gbltLicCommitteeMst);
        List<LicCommitteeDtlBean> licCommitteeDtlBean = licCommitteeBean.getLicCommitteeDtlBean();
        if(!licCommitteeDtlBean.isEmpty()) {
        	for(LicCommitteeDtlBean licCommitteeDtl : licCommitteeDtlBean) {
        		licCommitteeDtl.setUnumLicMemberId(licCommitteetDtlRespository.getNextId());
        		licCommitteeDtl.setUnumLicRsId(licCommitteeBean.getUnumComRsId());
        		licCommitteeDtl.setUnumIsValid(1);
        		licCommitteeDtl.setUnumEntryUid(RequestUtility.getUserId());
        		licCommitteeDtl.setUnumUnivId(RequestUtility.getUniversityId());
        		licCommitteeDtl.setUdtEntryDate(new Date());
        	}
            List<GbltLicCommitteeMemberDtl> gbltLicCommitteeDtl = BeanUtils.copyListProperties(licCommitteeDtlBean, GbltLicCommitteeMemberDtl.class);
            licCommitteetDtlRespository.saveAll(gbltLicCommitteeDtl);
        }
        return ServiceResponse.builder().status(1).message(language.saveSuccess("Lic Committee Save")).build();
    }
}
