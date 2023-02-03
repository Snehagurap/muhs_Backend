package in.cdac.university.studentWelfare.service;

import java.io.Serializable;
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
		StudentMasterBean  studentBean= restUtility.get(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_STUDENT+savitbpschemeapplmstbean.getUstrEnrollmentNo(), StudentMasterBean.class,1);
        
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
        
        StudentMasterBean studentMasterBean = BeanUtils.copyProperties(savitbpschemeapplmstbean, StudentMasterBean.class);
        Date d1 = new Date();
        savitbpschemeapplmstbean.setUdtEntryDate(d1);
        savitbpschemeapplmstbean.setUdtEffFrom(d1);
        savitbpschemeapplmstbean.setUnumSavitbpApplicationdt(d1);
        savitbpschemeapplmstbean.setUnumIsvalid(1);
        savitbpschemeapplmstbean.setUnumUnivId(RequestUtility.getUniversityId());
        savitbpschemeapplmstbean.setUnumEntryUid(RequestUtility.getUserId());

		//save case 
		if (studentBean == null) {
			
			StudentMasterBean  studentBeanDraft= restUtility.get(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_STUDENT_DRAFT+savitbpschemeapplmstbean.getUstrEnrollmentNo(), StudentMasterBean.class,1);
	        if(studentBeanDraft == null) {
	        	studentMasterBean.setUnumStudentId(1L);
	        }
	        else {
	        	studentMasterBean.setUnumStudentId(studentBeanDraft.getUnumStudentId());
	        }
	        studentMasterBean.setUnumIsvalid(1);
	        GmstSwSavitbpSchemeApplMst gmstSwSavitbpSchemeApplMstDraft  = gmstSwSavitbpSchemeApplMstRepository.findByUnumIsvalidAndUnumStudentIdAndUnumUnivId(2,studentMasterBean.getUnumStudentId(),RequestUtility.getUniversityId());
	        if(gmstSwSavitbpSchemeApplMstDraft == null) {
	        	savitbpschemeapplmstbean.setUnumSavitbpApplicationid( gmstSwSavitbpSchemeApplMstRepository.getNextId());
	        }
	        else {
	        	savitbpschemeapplmstbean.setUnumSavitbpApplicationid(gmstSwSavitbpSchemeApplMstDraft.getUnumSavitbpApplicationid());
	        	gmstSwSavitbpSchemeApplMstRepository.delete(gmstSwSavitbpSchemeApplMstDraft);
	        }
			StudentMasterBean studentMasterBeanSaved =  restUtility.postForData(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_UPDATE_SAVE_STUDENT,studentMasterBean, StudentMasterBean.class);
			savitbpschemeapplmstbean.setUnumStudentId(studentMasterBeanSaved.getUnumStudentId());
			
		    //save
			GmstSwSavitbpSchemeApplMst gmstSwSavitbpSchemeApplMst = BeanUtils.copyProperties(savitbpschemeapplmstbean, GmstSwSavitbpSchemeApplMst.class);
			SavitbpSchemeApplTrackermst savitbpSchemeApplTrackermst = BeanUtils.copyProperties(savitbpschemeapplmstbean, SavitbpSchemeApplTrackermst.class);
			SavitbpSchemeApplTrackerdtlBean savitbpSchemeApplTrackerdtlBean = BeanUtils.copyProperties(savitbpschemeapplmstbean, SavitbpSchemeApplTrackerdtlBean.class);
			gmstSwSavitbpSchemeApplMstRepository.save(gmstSwSavitbpSchemeApplMst);
			savitbpSchemeApplTrackermstService.saveSavitbpSchemeApplTrackermst(savitbpSchemeApplTrackermst);
			savitbpSchemeApplTrackerdtlService.saveSavitbpSchemeApplTrackerDtl(savitbpSchemeApplTrackerdtlBean);
		} //update case
		else {
			studentMasterBean.setUnumStudentId(studentBean.getUnumStudentId());
	        GmstSwSavitbpSchemeApplMst gmstSwSavitbpSchemeApplMstDraft  =gmstSwSavitbpSchemeApplMstRepository.findByUnumIsvalidAndUnumStudentIdAndUnumUnivId(2,studentMasterBean.getUnumStudentId(),RequestUtility.getUniversityId());
	        if(gmstSwSavitbpSchemeApplMstDraft == null) {
	        	savitbpschemeapplmstbean.setUnumSavitbpApplicationid( gmstSwSavitbpSchemeApplMstRepository.getNextId());
	        }
	        else {
	        	savitbpschemeapplmstbean.setUnumSavitbpApplicationid(studentMasterBean.getUnumStudentId());
	        }
			GmstSwSavitbpSchemeApplMst gmstSwSavitbpSchemeApplMst = BeanUtils.copyProperties(savitbpschemeapplmstbean, GmstSwSavitbpSchemeApplMst.class);
			gmstSwSavitbpSchemeApplMstRepository.save(gmstSwSavitbpSchemeApplMst);
			restUtility.postForData(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_UPDATE_SAVE_STUDENT,studentMasterBean, StudentMasterBean.class);
		}
		     
		 return ServiceResponse.builder()
	                .status(1)
	                .message(language.updateSuccess("Sacheme"))
	                .build();
	}

	@Transactional
	public ServiceResponse saveDraftSavitbpScheme(@Valid SavitbpSchemeApplMstBean savitbpschemeapplmstbean) throws Exception {
		//student duplicate Check
				StudentMasterBean  studentBean= restUtility.get(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_STUDENT+savitbpschemeapplmstbean.getUstrEnrollmentNo(), StudentMasterBean.class,1);
		        
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
		        
		        StudentMasterBean studentMasterBean = BeanUtils.copyProperties(savitbpschemeapplmstbean, StudentMasterBean.class);
		        Date d1 = new Date();
		        savitbpschemeapplmstbean.setUdtEntryDate(d1);
		        savitbpschemeapplmstbean.setUdtEffFrom(d1);
		        savitbpschemeapplmstbean.setUnumSavitbpApplicationdt(d1);
		        savitbpschemeapplmstbean.setUnumIsvalid(2);
		        savitbpschemeapplmstbean.setUnumUnivId(RequestUtility.getUniversityId());
		        savitbpschemeapplmstbean.setUnumEntryUid(RequestUtility.getUserId());

				//save case 
				if (studentBean == null) {
					
					StudentMasterBean  studentBeanDraft= restUtility.get(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_STUDENT_DRAFT+savitbpschemeapplmstbean.getUstrEnrollmentNo(), StudentMasterBean.class,1);
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
			        }
			        else {
			        	savitbpschemeapplmstbean.setUnumSavitbpApplicationid(gmstSwSavitbpSchemeApplMstDraft.getUnumSavitbpApplicationid());
			        	gmstSwSavitbpSchemeApplMstRepository.delete(gmstSwSavitbpSchemeApplMstDraft);
			        }
			        StudentMasterBean studentMasterBeanSaved = restUtility.postForData(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_UPDATE_SAVE_STUDENT_DRAFT,studentMasterBean, StudentMasterBean.class);
					savitbpschemeapplmstbean.setUnumStudentId(studentMasterBeanSaved.getUnumStudentId());
			        //cloning
					GmstSwSavitbpSchemeApplMst gmstSwSavitbpSchemeApplMst = BeanUtils.copyProperties(savitbpschemeapplmstbean, GmstSwSavitbpSchemeApplMst.class);
					gmstSwSavitbpSchemeApplMstRepository.save(gmstSwSavitbpSchemeApplMst);

				} //update case
				else {
					studentMasterBean.setUnumStudentId(studentBean.getUnumStudentId());
					studentMasterBean.setUnumIsvalid(1);
			        GmstSwSavitbpSchemeApplMst gmstSwSavitbpSchemeApplMstDraft  =gmstSwSavitbpSchemeApplMstRepository.findByUnumIsvalidAndUnumStudentIdAndUnumUnivId(2,studentMasterBean.getUnumStudentId(),RequestUtility.getUniversityId());
			        if(gmstSwSavitbpSchemeApplMstDraft == null) {
			        	savitbpschemeapplmstbean.setUnumSavitbpApplicationid( gmstSwSavitbpSchemeApplMstRepository.getNextId());
			        }
			        else {
			        	savitbpschemeapplmstbean.setUnumSavitbpApplicationid(gmstSwSavitbpSchemeApplMstDraft.getUnumSavitbpApplicationid());
			        	gmstSwSavitbpSchemeApplMstRepository.delete(gmstSwSavitbpSchemeApplMstDraft);
			        }
					restUtility.postForData(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_UPDATE_SAVE_STUDENT_DRAFT,studentMasterBean, StudentMasterBean.class);
					GmstSwSavitbpSchemeApplMst gmstSwSavitbpSchemeApplMst = BeanUtils.copyProperties(savitbpschemeapplmstbean, GmstSwSavitbpSchemeApplMst.class);
					gmstSwSavitbpSchemeApplMstRepository.save(gmstSwSavitbpSchemeApplMst);
				}
				     
				 return ServiceResponse.builder()
			                .status(1)
			                .message(language.updateSuccess("Sacheme"))
			                .build();

	}

	public ServiceResponse getDraftSavitbpScheme(String studentEnrollmentNo) throws Exception {
		StudentMasterBean  studentBean= restUtility.get(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_STUDENT_DRAFT+studentEnrollmentNo, StudentMasterBean.class,1);
        GmstSwSavitbpSchemeApplMst gmstSwSavitbpSchemeApplMstDraft  = gmstSwSavitbpSchemeApplMstRepository.findByUnumIsvalidAndUnumStudentIdAndUnumUnivId(2,studentBean.getUnumStudentId(),RequestUtility.getUniversityId());
        SavitbpSchemeApplMstBean savitbpschemeapplmstbean =  BeanUtils.copyProperties(gmstSwSavitbpSchemeApplMstDraft, SavitbpSchemeApplMstBean.class);
        if(savitbpschemeapplmstbean == null)
        	throw new DataNotFoundForGivenEnrollmentNumber();
        return ServiceResponse.successObject(savitbpschemeapplmstbean);
	}

	public ServiceResponse getSavitbpScheme(String studentEnrollmentNo) throws Exception {
		StudentMasterBean  studentBean= restUtility.get(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_STUDENT+studentEnrollmentNo, StudentMasterBean.class,1);
        GmstSwSavitbpSchemeApplMst gmstSwSavitbpSchemeApplMstDraft  = gmstSwSavitbpSchemeApplMstRepository.findByUnumIsvalidAndUnumStudentIdAndUnumUnivId(1,studentBean.getUnumStudentId(),RequestUtility.getUniversityId());
        SavitbpSchemeApplMstBean savitbpschemeapplmstbean =  BeanUtils.copyProperties(gmstSwSavitbpSchemeApplMstDraft, SavitbpSchemeApplMstBean.class);
        if(savitbpschemeapplmstbean == null)
        	throw new DataNotFoundForGivenEnrollmentNumber();
        return ServiceResponse.successObject(savitbpschemeapplmstbean);
	}
}
