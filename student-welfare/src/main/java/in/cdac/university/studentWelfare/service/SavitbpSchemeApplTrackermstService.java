package in.cdac.university.studentWelfare.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
		Date d1 = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        savitbpSchemeApplTrackermst.setUnumIsvalid(1);
        savitbpSchemeApplTrackermst.setUnumSno(1);
        savitbpSchemeApplTrackermst.setUnumApplDecisionStatusid(1L);
        savitbpSchemeApplTrackermst.setUnumApplLevelid(1L);
        savitbpSchemeApplTrackermst.setUnumApplStatusid(1L);
        savitbpSchemeApplTrackermst.setUdtLstModDate(d1);
		GmstSwSavitbpSchemeApplTrackermst gmstSwSavitbpSchemeApplTrackermst = BeanUtils.copyProperties(savitbpSchemeApplTrackermst, GmstSwSavitbpSchemeApplTrackermst.class);
		if(savitbpSchemeApplTrackermst.getUdtEffFrom() != null && (!savitbpSchemeApplTrackermst.getUdtEffFrom().isEmpty()) )
			gmstSwSavitbpSchemeApplTrackermst.setUdtEffFrom(df.parse(savitbpSchemeApplTrackermst.getUdtEffFrom()));
		else {
			gmstSwSavitbpSchemeApplTrackermst.setUdtEffFrom(d1);
		}
		if(savitbpSchemeApplTrackermst.getUdtEntryDate() != null && (!savitbpSchemeApplTrackermst.getUdtEntryDate().isEmpty()))
			gmstSwSavitbpSchemeApplTrackermst.setUdtEntryDate(df.parse(savitbpSchemeApplTrackermst.getUdtEntryDate()));
		else {
			gmstSwSavitbpSchemeApplTrackermst.setUdtEntryDate(d1);
		}
		gmstSwSavitbpSchemeApplTrackermstRepository.save(gmstSwSavitbpSchemeApplTrackermst);
		return ServiceResponse.successObject(savitbpSchemeApplTrackermst);
	}
	
	@Transactional
	public ServiceResponse updateSavitbpSchemeApplTrackermst(SavitbpSchemeApplTrackermst savitbpSchemeApplTrackermst) throws Exception {
		Date d1 = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		savitbpSchemeApplTrackermst.setUnumIsvalid(1);
        savitbpSchemeApplTrackermst.setUnumSno(1);
        savitbpSchemeApplTrackermst.setUnumApplDecisionStatusid(1L);
        savitbpSchemeApplTrackermst.setUnumApplLevelid(1L);
        savitbpSchemeApplTrackermst.setUnumApplStatusid(1L);
        savitbpSchemeApplTrackermst.setUdtLstModDate(d1);
		GmstSwSavitbpSchemeApplTrackermst gmstSwSavitbpSchemeApplTrackermst = BeanUtils.copyProperties(savitbpSchemeApplTrackermst, GmstSwSavitbpSchemeApplTrackermst.class);
		if(savitbpSchemeApplTrackermst.getUdtEffFrom() != null && (!savitbpSchemeApplTrackermst.getUdtEffFrom().isEmpty()) )
			gmstSwSavitbpSchemeApplTrackermst.setUdtEffFrom(df.parse(savitbpSchemeApplTrackermst.getUdtEffFrom()));
		else {
			gmstSwSavitbpSchemeApplTrackermst.setUdtEffFrom(d1);
		}
		if(savitbpSchemeApplTrackermst.getUdtEntryDate() != null && (!savitbpSchemeApplTrackermst.getUdtEntryDate().isEmpty()))
			gmstSwSavitbpSchemeApplTrackermst.setUdtEntryDate(df.parse(savitbpSchemeApplTrackermst.getUdtEntryDate()));
		else {
			gmstSwSavitbpSchemeApplTrackermst.setUdtEntryDate(d1);
		}
		gmstSwSavitbpSchemeApplTrackermstRepository.save(gmstSwSavitbpSchemeApplTrackermst);
		return ServiceResponse.successObject(savitbpSchemeApplTrackermst);
	}
	
}
