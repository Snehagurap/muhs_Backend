package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.ApplicationTrackerBean;
import in.cdac.university.globalService.bean.ApplicationTrackerDtlBean;
import in.cdac.university.globalService.entity.GbltConfigApplicationDataMst;
import in.cdac.university.globalService.entity.GbltConfigApplicationTracker;
import in.cdac.university.globalService.entity.GbltConfigApplicationTrackerDtl;
import in.cdac.university.globalService.exception.ApplicationException;
import in.cdac.university.globalService.repository.ApplicantRepository;
import in.cdac.university.globalService.repository.ApplicationTrackerDtlRepository;
import in.cdac.university.globalService.repository.ApplicationTrackerRepository;
import in.cdac.university.globalService.repository.ConfigApplicantDataMasterRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.FtpUtility;
import in.cdac.university.globalService.util.Language;
import in.cdac.university.globalService.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplicationTrackerService {

    @Autowired
    ApplicationTrackerRepository applicationTrackerRepository;

    @Autowired
    ApplicationTrackerDtlRepository applicationTrackerDtlRepository;

    @Autowired
    private ConfigApplicantDataMasterRepository applicantDataMasterRepository;

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
}
