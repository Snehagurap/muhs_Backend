package in.cdac.university.studentWelfare.service;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.cdac.university.studentWelfare.util.*;
import in.cdac.university.studentWelfare.exception.ApplicationException;
import in.cdac.university.studentWelfare.exception.DataNotFoundForGivenEnrollmentNumber;
import in.cdac.university.studentWelfare.util.BeanUtils;
import in.cdac.university.studentWelfare.util.RequestUtility;
import in.cdac.university.studentWelfare.util.Constants;
import in.cdac.university.studentWelfare.util.RestUtility;
import in.cdac.university.studentWelfare.service.SavitbpSchemeApplTrackermstService;
import in.cdac.university.studentWelfare.bean.SavitbpSchemeApplMstBean;
import in.cdac.university.studentWelfare.bean.SavitbpSchemeApplTrackerdtlBean;
import in.cdac.university.studentWelfare.bean.SavitbpSchemeApplTrackermst;
import in.cdac.university.studentWelfare.entity.GmstSwSavitbpSchemeApplMst;
import in.cdac.university.studentWelfare.repository.GmstSwSavitbpSchemeApplMstRepository;
import in.cdac.university.studentWelfare.util.Language;
import in.cdac.university.studentWelfare.util.ServiceResponse;
import in.cdac.university.studentWelfare.bean.StudentMasterBean;


@Service
public class SavitbpSchemeService implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private Language language;
	
    @Autowired
    private FtpUtility ftpUtility;
    
	@Autowired
	private GmstSwSavitbpSchemeApplMstRepository gmstSwSavitbpSchemeApplMstRepository;

	@Autowired
	private SavitbpSchemeApplTrackermstService savitbpSchemeApplTrackermstService;
	
	@Autowired
	private SavitbpSchemeApplTrackerdtlService savitbpSchemeApplTrackerdtlService;
	
    @Autowired
    RestUtility restUtility;
    
    
	@Transactional
	public ServiceResponse saveSavitbpScheme(SavitbpSchemeApplMstBean savitbpschemeapplmstbean) throws Exception {
		
		//student duplicate Check
		StudentMasterBean  studentBean= restUtility.getOrThrow(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_STUDENT+savitbpschemeapplmstbean.getUstrEnrollmentNo(), StudentMasterBean.class);
        
		fileValidations(savitbpschemeapplmstbean);
        
        StudentMasterBean studentMasterBean = BeanUtils.copyProperties(savitbpschemeapplmstbean, StudentMasterBean.class);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = commonDataSetting(savitbpschemeapplmstbean);
        
		//save case 
		if (studentBean == null) {
			
			StudentMasterBean  studentBeanDraft= restUtility.getOrThrow(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_STUDENT_DRAFT+savitbpschemeapplmstbean.getUstrEnrollmentNo(), StudentMasterBean.class);
	        if(studentBeanDraft == null) {
	        	studentMasterBean.setUnumStudentId(1L);
	        }
	        else {
	        	studentMasterBean.setUnumStudentId(studentBeanDraft.getUnumStudentId());
	        }
	        GmstSwSavitbpSchemeApplMst gmstSwSavitbpSchemeApplMstDraft  = gmstSwSavitbpSchemeApplMstRepository.findByUnumIsvalidAndUnumStudentIdAndUnumUnivId(2,studentMasterBean.getUnumStudentId(),RequestUtility.getUniversityId());
	        if(gmstSwSavitbpSchemeApplMstDraft == null) {
	        	savitbpschemeapplmstbean.setUnumSavitbpApplicationid( gmstSwSavitbpSchemeApplMstRepository.getNextId());
	        	savitbpschemeapplmstbean.setUnumEntryUid(RequestUtility.getUserId());
	        }
	        else {
	        	savitbpschemeapplmstbean.setUnumSavitbpApplicationid(gmstSwSavitbpSchemeApplMstDraft.getUnumSavitbpApplicationid());
	        	gmstSwSavitbpSchemeApplMstRepository.delete(gmstSwSavitbpSchemeApplMstDraft);
	        }
			StudentMasterBean studentMasterBeanSaved =  restUtility.postForData(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_UPDATE_SAVE_STUDENT,studentMasterBean, StudentMasterBean.class);
			savitbpschemeapplmstbean.setUnumStudentId(studentMasterBeanSaved.getUnumStudentId());
			savitbpschemeapplmstbean.setUnumIsvalid(1);
			
		    //save
			GmstSwSavitbpSchemeApplMst gmstSwSavitbpSchemeApplMst = BeanUtils.copyProperties(savitbpschemeapplmstbean, GmstSwSavitbpSchemeApplMst.class);
			SavitbpSchemeApplTrackermst savitbpSchemeApplTrackermst = BeanUtils.copyProperties(savitbpschemeapplmstbean, SavitbpSchemeApplTrackermst.class);
			SavitbpSchemeApplTrackerdtlBean savitbpSchemeApplTrackerdtlBean = BeanUtils.copyProperties(savitbpschemeapplmstbean, SavitbpSchemeApplTrackerdtlBean.class);
			dateConverstion(savitbpschemeapplmstbean, df, gmstSwSavitbpSchemeApplMst);
			gmstSwSavitbpSchemeApplMstRepository.save(gmstSwSavitbpSchemeApplMst);
			savitbpSchemeApplTrackermstService.saveSavitbpSchemeApplTrackermst(savitbpSchemeApplTrackermst);
			savitbpSchemeApplTrackerdtlService.saveSavitbpSchemeApplTrackerDtl(savitbpSchemeApplTrackerdtlBean);
		} //update case
		else {
			studentMasterBean.setUnumStudentId(studentBean.getUnumStudentId());
			studentMasterBean.setUnumIsvalid(1);
			savitbpschemeapplmstbean.setUnumIsvalid(1);
	        GmstSwSavitbpSchemeApplMst gmstSwSavitbpSchemeApplMstDraft  =gmstSwSavitbpSchemeApplMstRepository.findByUnumIsvalidAndUnumStudentIdAndUnumUnivId(2,studentMasterBean.getUnumStudentId(),RequestUtility.getUniversityId());
	        if(gmstSwSavitbpSchemeApplMstDraft == null) {
	        	savitbpschemeapplmstbean.setUnumSavitbpApplicationid( gmstSwSavitbpSchemeApplMstRepository.getNextId());
	        	savitbpschemeapplmstbean.setUnumEntryUid(RequestUtility.getUserId());
	        }
	        else {
	        	savitbpschemeapplmstbean.setUnumSavitbpApplicationid(gmstSwSavitbpSchemeApplMstDraft.getUnumSavitbpApplicationid());
	        	gmstSwSavitbpSchemeApplMstRepository.delete(gmstSwSavitbpSchemeApplMstDraft);
	        }
	        StudentMasterBean studentMasterBeanSaved = restUtility.postForData(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_UPDATE_SAVE_STUDENT,studentMasterBean, StudentMasterBean.class);
	        savitbpschemeapplmstbean.setUnumStudentId(studentMasterBeanSaved.getUnumStudentId());
	        GmstSwSavitbpSchemeApplMst gmstSwSavitbpSchemeApplMst = BeanUtils.copyProperties(savitbpschemeapplmstbean, GmstSwSavitbpSchemeApplMst.class);
			dateConverstion(savitbpschemeapplmstbean, df, gmstSwSavitbpSchemeApplMst);
			gmstSwSavitbpSchemeApplMstRepository.save(gmstSwSavitbpSchemeApplMst);
		}
		     
		 return ServiceResponse.builder()
	                .status(1)
	                .message(language.updateSuccess("Sacheme"))
	                .build();
	}

	private void dateConverstion(SavitbpSchemeApplMstBean savitbpschemeapplmstbean, DateFormat df,
			GmstSwSavitbpSchemeApplMst gmstSwSavitbpSchemeApplMst) throws ParseException {
		 Date d1 = new Date();
		if(savitbpschemeapplmstbean.getUdtStuDob()!=null && (!savitbpschemeapplmstbean.getUdtStuDob().isEmpty()) )
				gmstSwSavitbpSchemeApplMst.setUdtStuDob(df.parse(savitbpschemeapplmstbean.getUdtStuDob()));
		if(savitbpschemeapplmstbean.getUdtPossibleCourseCompDt()!=null && (!savitbpschemeapplmstbean.getUdtPossibleCourseCompDt().isEmpty()))
			gmstSwSavitbpSchemeApplMst.setUdtPossibleCourseCompDt(df.parse(savitbpschemeapplmstbean.getUdtPossibleCourseCompDt()));
		if(savitbpschemeapplmstbean.getUnumSavitbpApplicationdt() != null && (!savitbpschemeapplmstbean.getUnumSavitbpApplicationdt().isEmpty()) )
			gmstSwSavitbpSchemeApplMst.setUnumSavitbpApplicationdt(df.parse(savitbpschemeapplmstbean.getUnumSavitbpApplicationdt()));
		if(savitbpschemeapplmstbean.getUdtEffFrom() != null && (!savitbpschemeapplmstbean.getUdtEffFrom().isEmpty()) )
			gmstSwSavitbpSchemeApplMst.setUdtEffFrom(df.parse(savitbpschemeapplmstbean.getUdtEffFrom()));
		else {
			gmstSwSavitbpSchemeApplMst.setUdtEffFrom(d1);
		}
		if(savitbpschemeapplmstbean.getUdtEntryDate() != null && (!savitbpschemeapplmstbean.getUdtEntryDate().isEmpty()))
			gmstSwSavitbpSchemeApplMst.setUdtEntryDate(df.parse(savitbpschemeapplmstbean.getUdtEntryDate()));
		else {
			gmstSwSavitbpSchemeApplMst.setUdtEntryDate(d1);
		}
	}

	@Transactional
	public ServiceResponse saveDraftSavitbpScheme(@Valid SavitbpSchemeApplMstBean savitbpschemeapplmstbean) throws Exception {
		//student duplicate Check
				StudentMasterBean  studentBean= restUtility.getOrThrow(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_STUDENT+savitbpschemeapplmstbean.getUstrEnrollmentNo(), StudentMasterBean.class);
		        
				fileValidations(savitbpschemeapplmstbean);
		        
		        StudentMasterBean studentMasterBean = BeanUtils.copyProperties(savitbpschemeapplmstbean, StudentMasterBean.class);
		        
		        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		        Date d1 = commonDataSetting(savitbpschemeapplmstbean);
		        savitbpschemeapplmstbean.setUnumIsvalid(2);
				//save case 
				if (studentBean == null) {
					savitbpschemeapplmstbean.setUdtLstModDate(d1);
					StudentMasterBean  studentBeanDraft= restUtility.getOrThrow(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_STUDENT_DRAFT+savitbpschemeapplmstbean.getUstrEnrollmentNo(), StudentMasterBean.class);
			        if(studentBeanDraft == null) {
			        	studentMasterBean.setUnumStudentId(1L);
			        }
			        else {
			        	studentMasterBean.setUnumStudentId(studentBeanDraft.getUnumStudentId());
			        }
			        studentMasterBean.setUnumIsvalid(2);
			        GmstSwSavitbpSchemeApplMst gmstSwSavitbpSchemeApplMstDraft  =gmstSwSavitbpSchemeApplMstRepository.findByUnumIsvalidAndUnumStudentIdAndUnumUnivId(2,studentMasterBean.getUnumStudentId(),RequestUtility.getUniversityId());
			        if(gmstSwSavitbpSchemeApplMstDraft == null) {
			        	savitbpschemeapplmstbean.setUnumSavitbpApplicationid( gmstSwSavitbpSchemeApplMstRepository.getNextId());
				        savitbpschemeapplmstbean.setUnumEntryUid(RequestUtility.getUserId());
			        }
			        else {
			        	savitbpschemeapplmstbean.setUnumSavitbpApplicationid(gmstSwSavitbpSchemeApplMstDraft.getUnumSavitbpApplicationid());
			        	gmstSwSavitbpSchemeApplMstRepository.delete(gmstSwSavitbpSchemeApplMstDraft);
			        }
			        StudentMasterBean studentMasterBeanSaved = restUtility.postForData(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_UPDATE_SAVE_STUDENT_DRAFT,studentMasterBean, StudentMasterBean.class);
					savitbpschemeapplmstbean.setUnumStudentId(studentMasterBeanSaved.getUnumStudentId());
			        //cloning
					GmstSwSavitbpSchemeApplMst gmstSwSavitbpSchemeApplMst = BeanUtils.copyProperties(savitbpschemeapplmstbean, GmstSwSavitbpSchemeApplMst.class);
					
					dateConverstion(savitbpschemeapplmstbean, df, gmstSwSavitbpSchemeApplMst);
					gmstSwSavitbpSchemeApplMstRepository.save(gmstSwSavitbpSchemeApplMst);

				} //update case
				else {
					savitbpschemeapplmstbean.setUdtLstModDate(d1);
					studentMasterBean.setUnumStudentId(studentBean.getUnumStudentId());
					studentMasterBean.setUnumIsvalid(1);
			        GmstSwSavitbpSchemeApplMst gmstSwSavitbpSchemeApplMstDraft  =gmstSwSavitbpSchemeApplMstRepository.findByUnumIsvalidAndUnumStudentIdAndUnumUnivId(2,studentMasterBean.getUnumStudentId(),RequestUtility.getUniversityId());
			        if(gmstSwSavitbpSchemeApplMstDraft == null) {
			        	savitbpschemeapplmstbean.setUnumSavitbpApplicationid( gmstSwSavitbpSchemeApplMstRepository.getNextId());
			        	savitbpschemeapplmstbean.setUnumEntryUid(RequestUtility.getUserId());
			        }
			        else {
			        	savitbpschemeapplmstbean.setUnumSavitbpApplicationid(gmstSwSavitbpSchemeApplMstDraft.getUnumSavitbpApplicationid());
			        	gmstSwSavitbpSchemeApplMstRepository.delete(gmstSwSavitbpSchemeApplMstDraft);
			        }
			        StudentMasterBean studentMasterBeanSaved = restUtility.postForData(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_UPDATE_SAVE_STUDENT_DRAFT,studentMasterBean, StudentMasterBean.class);
					savitbpschemeapplmstbean.setUnumStudentId(studentMasterBeanSaved.getUnumStudentId());
					GmstSwSavitbpSchemeApplMst gmstSwSavitbpSchemeApplMst = BeanUtils.copyProperties(savitbpschemeapplmstbean, GmstSwSavitbpSchemeApplMst.class);
					dateConverstion(savitbpschemeapplmstbean, df, gmstSwSavitbpSchemeApplMst);
			        gmstSwSavitbpSchemeApplMstRepository.save(gmstSwSavitbpSchemeApplMst);
				}
				     
				 return ServiceResponse.builder()
			                .status(1)
			                .message(language.updateSuccess("Sacheme"))
			                .build();

	}

	private Date commonDataSetting(SavitbpSchemeApplMstBean savitbpschemeapplmstbean) throws Exception {
		Date d1 = new Date();
		//savitbpschemeapplmstbean.setUdtEntryDate(d1);
		//savitbpschemeapplmstbean.setUdtEffFrom(d1);
		savitbpschemeapplmstbean.setUdtLstModDate(d1);
		savitbpschemeapplmstbean.setUnumUnivId(RequestUtility.getUniversityId());
		savitbpschemeapplmstbean.setUnumLstModUid(RequestUtility.getUserId());
		return d1;
	}

	private void fileValidations(SavitbpSchemeApplMstBean savitbpschemeapplmstbean) {
		//file upload
		if (savitbpschemeapplmstbean.getUstrAadhaarPath() != null && !savitbpschemeapplmstbean.getUstrAadhaarPath().isBlank()
			|| savitbpschemeapplmstbean.getUstrStuPhotoPath() != null && !savitbpschemeapplmstbean.getUstrStuPhotoPath().isBlank()	
			|| savitbpschemeapplmstbean.getUstrIncomeProofPath() != null && !savitbpschemeapplmstbean.getUstrIncomeProofPath().isBlank()
			|| savitbpschemeapplmstbean.getUstrCasteCertificatePath() != null && !savitbpschemeapplmstbean.getUstrCasteCertificatePath().isBlank()
			|| savitbpschemeapplmstbean.getUstrPassingMarksheetPath() != null && !savitbpschemeapplmstbean.getUstrPassingMarksheetPath().isBlank()) {
			if (!ftpUtility.moveFileFromTempToFinalDirectory(savitbpschemeapplmstbean.getUstrAadhaarPath())) {
		        throw new ApplicationException("Unable to upload file Aadhaar ");
		    }
		    if (!ftpUtility.moveFileFromTempToFinalDirectory(savitbpschemeapplmstbean.getUstrStuPhotoPath())) {
		        throw new ApplicationException("Unable to upload file Student Photo");
		    }
		    if (!ftpUtility.moveFileFromTempToFinalDirectory(savitbpschemeapplmstbean.getUstrIncomeProofPath())) {
		        throw new ApplicationException("Unable to upload file Income Proof");
		    }
		    if (!ftpUtility.moveFileFromTempToFinalDirectory(savitbpschemeapplmstbean.getUstrPassingMarksheetPath())) {
		        throw new ApplicationException("Unable to upload file Marks sheet ");
		    }
		}
	}

	public ServiceResponse getDraftSavitbpScheme(String studentEnrollmentNo) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		StudentMasterBean  studentBean= restUtility.getOrThrow(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_STUDENT_DRAFT+studentEnrollmentNo, StudentMasterBean.class);
		if(studentBean == null)
        	throw new DataNotFoundForGivenEnrollmentNumber("No Student Found With Given Enrollment Number in Draft");
        GmstSwSavitbpSchemeApplMst gmstSwSavitbpSchemeApplMstDraft  = gmstSwSavitbpSchemeApplMstRepository.findByUnumIsvalidAndUnumStudentIdAndUnumUnivId(2,studentBean.getUnumStudentId(),RequestUtility.getUniversityId());
		SavitbpSchemeApplMstBean savitbpschemeapplmstbean =  BeanUtils.copyProperties(gmstSwSavitbpSchemeApplMstDraft, SavitbpSchemeApplMstBean.class);
		savitbpschemeapplmstbean.setUnumSavitbpApplicationdt(df.format(gmstSwSavitbpSchemeApplMstDraft.getUnumSavitbpApplicationdt()));
		savitbpschemeapplmstbean.setUdtStuDob(df.format(gmstSwSavitbpSchemeApplMstDraft.getUdtStuDob()));
		return ServiceResponse.successObject(savitbpschemeapplmstbean);
	}

	public ServiceResponse getSavitbpScheme(String studentEnrollmentNo) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		StudentMasterBean  studentBean= restUtility.getOrThrow(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_STUDENT+studentEnrollmentNo, StudentMasterBean.class);
        if(studentBean == null)
         	throw new DataNotFoundForGivenEnrollmentNumber("No Student Found With Given Enrollment Number");
		GmstSwSavitbpSchemeApplMst gmstSwSavitbpSchemeApplMst  = gmstSwSavitbpSchemeApplMstRepository.findByUnumIsvalidAndUnumStudentIdAndUnumUnivId(1,studentBean.getUnumStudentId(),RequestUtility.getUniversityId());
        SavitbpSchemeApplMstBean savitbpschemeapplmstbean =  BeanUtils.copyProperties(gmstSwSavitbpSchemeApplMst, SavitbpSchemeApplMstBean.class);
		savitbpschemeapplmstbean.setUnumSavitbpApplicationdt(df.format(gmstSwSavitbpSchemeApplMst.getUnumSavitbpApplicationdt()));
		savitbpschemeapplmstbean.setUdtStuDob(df.format(gmstSwSavitbpSchemeApplMst.getUdtStuDob()));
        return ServiceResponse.successObject(savitbpschemeapplmstbean);
	}
}
