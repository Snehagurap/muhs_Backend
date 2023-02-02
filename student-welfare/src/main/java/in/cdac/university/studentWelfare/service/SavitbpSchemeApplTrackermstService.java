package in.cdac.university.studentWelfare.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.cdac.university.studentWelfare.util.BeanUtils;
import in.cdac.university.studentWelfare.bean.SavitbpSchemeApplTrackermst;
import in.cdac.university.studentWelfare.entity.GmstSwSavitbpSchemeApplTrackermst;
import in.cdac.university.studentWelfare.repository.GmstSwSavitbpSchemeApplTrackermstRepository;
import in.cdac.university.studentWelfare.util.Language;
import in.cdac.university.studentWelfare.util.RequestUtility;
import in.cdac.university.studentWelfare.util.ServiceResponse;

@Service
public class SavitbpSchemeApplTrackermstService {

	@Autowired
	private Language language;
	
	@Autowired
	private GmstSwSavitbpSchemeApplTrackermstRepository gmstSwSavitbpSchemeApplTrackermstRepository;
	
	@Transactional
	public ServiceResponse saveSavitbpSchemeApplTrackermst(SavitbpSchemeApplTrackermst savitbpSchemeApplTrackermst) throws Exception {
        savitbpSchemeApplTrackermst.setUnumIsvalid(1);
        savitbpSchemeApplTrackermst.setUnumSno(1);
        savitbpSchemeApplTrackermst.setUnumApplDecisionStatusid(1L);
        savitbpSchemeApplTrackermst.setUnumApplLevelid(1L);
        savitbpSchemeApplTrackermst.setUnumApplStatusid(1L);
		GmstSwSavitbpSchemeApplTrackermst gmstSwSavitbpSchemeApplTrackermst = BeanUtils.copyProperties(savitbpSchemeApplTrackermst, GmstSwSavitbpSchemeApplTrackermst.class);
		gmstSwSavitbpSchemeApplTrackermstRepository.save(gmstSwSavitbpSchemeApplTrackermst);
		return ServiceResponse.successObject(savitbpSchemeApplTrackermst);
	}
	
	@Transactional
	public ServiceResponse updateSavitbpSchemeApplTrackermst(SavitbpSchemeApplTrackermst savitbpSchemeApplTrackermst) throws Exception {
        savitbpSchemeApplTrackermst.setUnumIsvalid(1);
        savitbpSchemeApplTrackermst.setUnumSno(1);
        savitbpSchemeApplTrackermst.setUnumApplDecisionStatusid(1L);
        savitbpSchemeApplTrackermst.setUnumApplLevelid(1L);
        savitbpSchemeApplTrackermst.setUnumApplStatusid(1L);
		GmstSwSavitbpSchemeApplTrackermst gmstSwSavitbpSchemeApplTrackermst = BeanUtils.copyProperties(savitbpSchemeApplTrackermst, GmstSwSavitbpSchemeApplTrackermst.class);
		gmstSwSavitbpSchemeApplTrackermstRepository.save(gmstSwSavitbpSchemeApplTrackermst);
		return ServiceResponse.successObject(savitbpSchemeApplTrackermst);
	}
	
}
