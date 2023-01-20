package in.cdac.university.committee.service;


import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

import in.cdac.university.globalService.exception.ApplicationException;



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
	    
		List<GbltLicCommitteeRuleSetMst> licCommitteeRuleSetMst = licCommitteeRuleSetMstRespository.findByUnumIsValidInAndUstrComRsNameIgnoreCaseAndUnumUnivId(
                List.of(1), licCommitteeRuleSetBeanMst.getUstrComRsName(),RequestUtility.getUniversityId());

        if (!licCommitteeRuleSetMst.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("Lic Committee Rule Set", licCommitteeRuleSetBeanMst.getUstrComRsName()));
        }
        GbltLicCommitteeRuleSetMst gbltLicCommitteeRuleSetMst = new GbltLicCommitteeRuleSetMst();
        BeanUtils.copyProperties(licCommitteeRuleSetBeanMst, gbltLicCommitteeRuleSetMst);

        gbltLicCommitteeRuleSetMst.setUnumComRsId(licCommitteeRuleSetMstRespository.getNextId());
        
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

	@Transactional
	public ServiceResponse updateLicCommitteeRule(LicCommitteeRuleSetBeanMst licCommitteeRuleSetBeanMst) {

        List<GbltLicCommitteeRuleSetMst> licRuleSet = licCommitteeRuleSetMstRespository.findByUnumIsValidInAndUstrComRsNameIgnoreCaseAndUnumComRsIdNotAndUnumUnivId(
                List.of(1, 2), licCommitteeRuleSetBeanMst.getUstrComRsName(), licCommitteeRuleSetBeanMst.getUnumComRsId(),RequestUtility.getUniversityId());

        if (!licRuleSet.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("Lic Rule Set", licCommitteeRuleSetBeanMst.getUstrComRsName()));
        }
		
        // Create Log
        Integer noOfRecordsAffected = licCommitteeRuleSetMstRespository.createLog(licCommitteeRuleSetBeanMst.getUnumComRsId());
       // licCommitteeRuleSetBeanMst.setUnumIsValid(1);
        if (noOfRecordsAffected == 0) {
            throw new ApplicationException(language.notFoundForId("Lic Rule Set", licCommitteeRuleSetBeanMst.getUnumComRsId()));
        }
        GbltLicCommitteeRuleSetMst gbltLicCommitteeRuleSetMst =  BeanUtils.copyProperties(licCommitteeRuleSetBeanMst, GbltLicCommitteeRuleSetMst.class);
       // gbltLicCommitteeRuleSetMst.setUnumIsValid(1);
        licCommitteeRuleSetMstRespository.saveAndFlush(gbltLicCommitteeRuleSetMst);

        List<LicCommitteeRuleSetDtlBean> committeeRuleList = licCommitteeRuleSetBeanMst.getCommitteeRuleList() ;
        if (!committeeRuleList.isEmpty())
        {
        	committeeRuleList.stream().forEach( DtlBean ->{
            	DtlBean.setUnumIsValid(1);
            });
       		licCommitteeRuleSetDtlRespository.createLog(licCommitteeRuleSetBeanMst.getUnumComRsId());
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
	

	public ServiceResponse getCommitteeRuleSetByUnumComRsId(int i, Integer universityId,
			Long unumComRsId) throws Exception {
		
		LicCommitteeRuleSetBeanMst committeeMasterBean=new LicCommitteeRuleSetBeanMst();
		//unum_com_rs_id
		List<GbltLicCommitteeRuleSetMst> ruleSetMst = licCommitteeRuleSetMstRespository.getByUnumIsValidAndUnumUnivIdAndUnumComRsId(1,RequestUtility.getUniversityId(), unumComRsId);
        if (ruleSetMst.isEmpty())
            return ServiceResponse.errorResponse(language.notFoundForId("Committee", unumComRsId));
        BeanUtils.copyProperties(ruleSetMst.get(0), committeeMasterBean);
        
        List<GbltLicCommitteeRuleSetDtl> ruleSetDtl = licCommitteeRuleSetDtlRespository.findByUnumIsValidAndUnumUnivIdAndUnumComRsId(1,RequestUtility.getUniversityId(),unumComRsId); // and rsid=i
                
        if(!ruleSetDtl.isEmpty()) {
            List<LicCommitteeRuleSetDtlBean> committeeRulesetDtlBeanList = BeanUtils.copyListProperties(ruleSetDtl, LicCommitteeRuleSetDtlBean.class);
            committeeMasterBean.setCommitteeRuleList(committeeRulesetDtlBeanList);
        }
        
        return ServiceResponse.successObject(
        		committeeMasterBean
        );
		// TODO Auto-generated method stub
	}
	
}