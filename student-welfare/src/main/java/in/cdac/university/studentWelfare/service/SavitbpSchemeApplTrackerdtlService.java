package in.cdac.university.studentWelfare.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = new Date();
		savitbpSchemeApplTrackerdtlBean.setUnumIsvalid(1);
		savitbpSchemeApplTrackerdtlBean.setUnumSno(1);
		savitbpSchemeApplTrackerdtlBean.setUnumApplDecisionStatusid(1L);
		savitbpSchemeApplTrackerdtlBean.setUnumApplLevelid(1L);
		savitbpSchemeApplTrackerdtlBean.setUnumApplStatusid(1L);
        GmstSwSavitbpSchemeApplTrackerdtl gmstSwSavitbpSchemeApplTrackerdtl = BeanUtils.copyProperties(savitbpSchemeApplTrackerdtlBean, GmstSwSavitbpSchemeApplTrackerdtl.class);
		if(savitbpSchemeApplTrackerdtlBean.getUdtEffFrom() != null && (!savitbpSchemeApplTrackerdtlBean.getUdtEffFrom().isEmpty()) )
			gmstSwSavitbpSchemeApplTrackerdtl.setUdtEffFrom(df.parse(savitbpSchemeApplTrackerdtlBean.getUdtEffFrom()));
		else {
			gmstSwSavitbpSchemeApplTrackerdtl.setUdtEffFrom(d1);
		}
		if(savitbpSchemeApplTrackerdtlBean.getUdtEntryDate() != null && (!savitbpSchemeApplTrackerdtlBean.getUdtEntryDate().isEmpty()))
			gmstSwSavitbpSchemeApplTrackerdtl.setUdtEntryDate(df.parse(savitbpSchemeApplTrackerdtlBean.getUdtEntryDate()));
		else {
			gmstSwSavitbpSchemeApplTrackerdtl.setUdtEntryDate(d1);
		}
        gmstSwSavitbpSchemeApplTrackerdtlRepository.save(gmstSwSavitbpSchemeApplTrackerdtl);
		return ServiceResponse.successObject(savitbpSchemeApplTrackerdtlBean);
	}
}
