package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.ApplicationTrackerDtlBean;

import in.cdac.university.globalService.entity.GbltConfigApplicationTracker;
import in.cdac.university.globalService.entity.GbltConfigApplicationTrackerDtl;
import in.cdac.university.globalService.entity.GmstCoursefacultyMst;
import in.cdac.university.globalService.exception.ApplicationException;
import in.cdac.university.globalService.repository.ApplicantRepository;
import in.cdac.university.globalService.repository.ApplicationTrackerDtlRepository;
import in.cdac.university.globalService.repository.ApplicationTrackerRepository;
import in.cdac.university.globalService.repository.ConfigApplicantDataMasterRepository;
import in.cdac.university.globalService.repository.FacultyRepository;
import in.cdac.university.globalService.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ApplicationTrackerService {

    @Autowired
    ApplicationTrackerRepository applicationTrackerRepository;

    @Autowired
    ApplicationTrackerDtlRepository applicationTrackerDtlRepository;

    @Autowired
    private ConfigApplicantDataMasterRepository applicantDataMasterRepository;
	
	@Autowired
    private FacultyRepository facultyRepository;    
	
	@Autowired
	private ApplicantRepository applicantRepository  ; 
	
	
	
    @Autowired
    private Language language;

    @Autowired
    private FtpUtility ftpUtility;

    @Transactional
    public ServiceResponse applicationScrutiny(ApplicationTrackerDtlBean applicationTrackerDtlBean) {
        if(applicationTrackerDtlBean.getUnumApplicationId() == null && applicationTrackerDtlBean.getUnumApplicationStatusId() == null) {
            return ServiceResponse.errorResponse(language.mandatory("Application Id && Application Status"));
        }
        if(applicationTrackerDtlBean.getUstrDocPath() != null && !applicationTrackerDtlBean.getUstrDocPath().isBlank()){
            boolean isFileMoved = ftpUtility.moveFileFromTempToFinalDirectory(applicationTrackerDtlBean.getUstrDocPath());
            if (!isFileMoved) {
                throw new ApplicationException("Unable to upload file");
            }
        }

        Integer maxApplicationStatusSno = applicationTrackerDtlRepository.getApplicationStatusSno(applicationTrackerDtlBean.getUnumApplicationId());
        applicationTrackerDtlBean.setUnumApplicationStatusSno(maxApplicationStatusSno);

        applicantDataMasterRepository.updateApplicationEntryStatus(
                applicationTrackerDtlBean.getUnumApplicationStatusId(),applicationTrackerDtlBean.getUnumApplicationId(), applicationTrackerDtlBean.getUnumApplicantId(), 1
        );

        GbltConfigApplicationTracker gbltConfigApplicationTracker = BeanUtils.copyProperties(applicationTrackerDtlBean, GbltConfigApplicationTracker.class);
        applicationTrackerRepository.save(gbltConfigApplicationTracker);

        GbltConfigApplicationTrackerDtl gbltConfigApplicationTrackerDtl = BeanUtils.copyProperties(applicationTrackerDtlBean, GbltConfigApplicationTrackerDtl.class);
        applicationTrackerDtlRepository.save(gbltConfigApplicationTrackerDtl);

        return ServiceResponse.successMessage(language.saveSuccess("Application Scrutiny"));
    }



    public ServiceResponse getScrutinyDetailPlanningBoard(Long applicationId, Integer applicationStatus) {
        if(applicationId == null) {
            return ServiceResponse.errorResponse(language.mandatory("ApplicationId"));
        }
        Optional<GbltConfigApplicationTrackerDtl> gbltConfigApplicationTrackerDtlOptional = applicationTrackerDtlRepository.getScrutinyDetails(
                applicationId, applicationStatus
        );

        if(gbltConfigApplicationTrackerDtlOptional.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("ApplicationId", applicationId));
        }

        return ServiceResponse.successObject(
                BeanUtils.copyProperties(gbltConfigApplicationTrackerDtlOptional.get(), ApplicationTrackerDtlBean.class)
        );
    }
	
	
	public ServiceResponse getDepartmentDetails(Long notificationId, Long notificationDetailId) {
        if(notificationId == null ) {
            return ServiceResponse.errorResponse(language.mandatory("NotificationId"));
        }
        Optional<GbltConfigApplicationTrackerDtl> gbltConfigApplicationTrackerDtlOptional = applicationTrackerDtlRepository.getDepDetails(
        		notificationId, notificationDetailId
        );

        if(gbltConfigApplicationTrackerDtlOptional.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("NotificationId", notificationId));
        }
        
        GbltConfigApplicationTrackerDtl gbltConfigApplicationTrackerDtl = gbltConfigApplicationTrackerDtlOptional.get();
        if(gbltConfigApplicationTrackerDtl.getUnumApplicantId() != null) {
        		Long applicantId = gbltConfigApplicationTrackerDtl.getUnumApplicantId(); 
        		Long facultyId   = gbltConfigApplicationTrackerDtl.getUnumNdtlFacultyId(); 
        		String faculty_name   =   facultyRepository.findByUnumCfacultyIdAndUnumIsvalid(facultyId.intValue(), 1).getUstrCfacultyFname();
        		String applicant_name =   applicantRepository.findByUnumApplicantIdAndUnumIsvalid(applicantId, 1 ).get().getUstrApplicantName();
        		gbltConfigApplicationTrackerDtlOptional.get().setFacultyName(faculty_name);
        		gbltConfigApplicationTrackerDtlOptional.get().setApplicantName(applicant_name);		
        		return ServiceResponse.successObject(
                BeanUtils.copyProperties(gbltConfigApplicationTrackerDtlOptional.get(), ApplicationTrackerDtlBean.class)
        );
        }
        else
        {
        	return ServiceResponse.errorResponse(language.notFoundForId("ApplicantId", null)); 
        }
    }
}
