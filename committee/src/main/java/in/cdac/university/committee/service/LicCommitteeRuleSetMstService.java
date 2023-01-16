package in.cdac.university.committee.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.cdac.university.committee.bean.CommitteeBean;
import in.cdac.university.committee.bean.CommitteeRulesetBean;
import in.cdac.university.committee.bean.LicCommitteeRuleSetBeanMst;
import in.cdac.university.committee.bean.LicCommitteeRuleSetDtlBean;
import in.cdac.university.committee.entity.GbltLicCommitteeRuleSetDtl;
import in.cdac.university.committee.entity.GbltLicCommitteeRuleSetMst;
import in.cdac.university.committee.repository.LicCommitteeRuleSetDtlRespository;
import in.cdac.university.committee.repository.LicCommitteeRuleSetMstRespository;
import in.cdac.university.committee.util.BeanUtils;
import in.cdac.university.committee.util.Language;
import in.cdac.university.committee.util.RequestUtility;
import in.cdac.university.committee.util.ServiceResponse;
import in.cdac.university.committee.util.StringUtils;



@Service
public class LicCommitteeRuleSetMstService {
	
	@Autowired
    private LicCommitteeRuleSetMstRespository licCommitteeRuleSetMstRespository;

	@Autowired
    private Language language;
	
	@Autowired
	LicCommitteeRuleSetDtlRespository licCommitteeRuleSetDtlRespository;

	@Transactional
	public ServiceResponse saveLicCommitteeRule(LicCommitteeRuleSetBeanMst licCommitteeRuleSetBeanMst) throws Exception{
	    
		List<GbltLicCommitteeRuleSetMst> licCommitteeRuleSetMst = licCommitteeRuleSetMstRespository.findByUnumIsValidInAndUstrComRsNameIgnoreCase(
                List.of(1), licCommitteeRuleSetBeanMst.getUstrComRsName());

        if (!licCommitteeRuleSetMst.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("Lic Committee Rule Set", licCommitteeRuleSetBeanMst.getUstrComRsName()));
        }
        GbltLicCommitteeRuleSetMst gbltLicCommitteeRuleSetMst = new GbltLicCommitteeRuleSetMst();
        BeanUtils.copyProperties(licCommitteeRuleSetBeanMst, gbltLicCommitteeRuleSetMst);
        Long data = licCommitteeRuleSetMstRespository.getNextId();
        
        gbltLicCommitteeRuleSetMst.setUnumComRsId(data);
        
        licCommitteeRuleSetMstRespository.save(gbltLicCommitteeRuleSetMst);
        
        
        List<LicCommitteeRuleSetDtlBean> committeeRuleList = licCommitteeRuleSetBeanMst.getCommitteeRuleList() ;
        if (!committeeRuleList.isEmpty())
        {
        	int count = 1;
        	for(LicCommitteeRuleSetDtlBean licCommitteeRuleSetDtl : committeeRuleList){
        		licCommitteeRuleSetDtl.setUnumComRsId(gbltLicCommitteeRuleSetMst.getUnumComRsId());
        		licCommitteeRuleSetDtl.setUnumComRsDtlId(Long.parseLong(
        					licCommitteeRuleSetDtl.getUnumComRsId() + StringUtils.padLeftZeros(count++ + "", 5)));
        		licCommitteeRuleSetDtl.setUnumIsValid(1);
        		licCommitteeRuleSetDtl.setUnumEntryUid(RequestUtility.getUserId());
        		licCommitteeRuleSetDtl.setUnumUnivId(RequestUtility.getUniversityId());
        		licCommitteeRuleSetDtl.setUdtEntryDate(new Date());
        	}
            List<GbltLicCommitteeRuleSetDtl> gbltLicCommitteeRuleSetDtl = BeanUtils.copyListProperties(committeeRuleList, GbltLicCommitteeRuleSetDtl.class);
        	licCommitteeRuleSetDtlRespository.saveAll(gbltLicCommitteeRuleSetDtl);
        }

        return ServiceResponse.builder().status(1).message(language.saveSuccess("Lic Committee Rule Set Save")).build();

	}

    public List<LicCommitteeRuleSetBeanMst> getCommitteeCombo() {

        return BeanUtils.copyListProperties(
        		licCommitteeRuleSetMstRespository.findByUnumUnivIdAndUnumIsValid(RequestUtility.getUniversityId(), 1), LicCommitteeRuleSetBeanMst.class);
       
    }

	public List<LicCommitteeRuleSetBeanMst> getListPageData() {
		return BeanUtils.copyListProperties(
				licCommitteeRuleSetMstRespository.findByUnumUnivIdAndUnumIsValid(RequestUtility.getUniversityId(), 1), LicCommitteeRuleSetBeanMst.class);
		
	}
	

	
}
