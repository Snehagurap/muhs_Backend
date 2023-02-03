package in.cdac.university.studentWelfare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.cdac.university.studentWelfare.util.BeanUtils;
import in.cdac.university.studentWelfare.util.ServiceResponse;
import in.cdac.university.studentWelfare.bean.SavitbpSchemeApplTrackerdtlBean;
import in.cdac.university.studentWelfare.entity.GmstSwSavitbpSchemeApplTrackerdtl;
import in.cdac.university.studentWelfare.repository.GmstSwSavitbpSchemeApplTrackerdtlRepository;

@Service
public class SavitbpSchemeApplTrackerdtlService {

	@Autowired
	private GmstSwSavitbpSchemeApplTrackerdtlRepository gmstSwSavitbpSchemeApplTrackerdtlRepository ;
	
	
	@Transactional
	public ServiceResponse saveSavitbpSchemeApplTrackerDtl(SavitbpSchemeApplTrackerdtlBean savitbpSchemeApplTrackerdtlBean) throws Exception {
		savitbpSchemeApplTrackerdtlBean.setUnumIsvalid(1);
		savitbpSchemeApplTrackerdtlBean.setUnumSno(1);
		savitbpSchemeApplTrackerdtlBean.setUnumApplDecisionStatusid(1L);
		savitbpSchemeApplTrackerdtlBean.setUnumApplLevelid(1L);
		savitbpSchemeApplTrackerdtlBean.setUnumApplStatusid(1L);
        GmstSwSavitbpSchemeApplTrackerdtl gmstSwSavitbpSchemeApplTrackerdtl = BeanUtils.copyProperties(savitbpSchemeApplTrackerdtlBean, GmstSwSavitbpSchemeApplTrackerdtl.class);
		gmstSwSavitbpSchemeApplTrackerdtlRepository.save(gmstSwSavitbpSchemeApplTrackerdtl);
		return ServiceResponse.successObject(savitbpSchemeApplTrackerdtlBean);
	}
}
