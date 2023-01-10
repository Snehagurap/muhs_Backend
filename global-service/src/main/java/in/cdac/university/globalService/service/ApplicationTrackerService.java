package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.ApplicationTrackerBean;
import in.cdac.university.globalService.bean.ApplicationTrackerDtlBean;

import in.cdac.university.globalService.bean.NotificationBean;
import in.cdac.university.globalService.entity.GbltConfigApplicationTracker;
import in.cdac.university.globalService.entity.GbltConfigApplicationTrackerDtl;
import in.cdac.university.globalService.entity.GmstApplicantMst;
import in.cdac.university.globalService.entity.GmstCoursefacultyMst;
import in.cdac.university.globalService.exception.ApplicationException;
import in.cdac.university.globalService.repository.ApplicantRepository;
import in.cdac.university.globalService.repository.ApplicationTrackerDtlRepository;
import in.cdac.university.globalService.repository.ApplicationTrackerRepository;
import in.cdac.university.globalService.repository.ConfigApplicationDataMasterRepository;
import in.cdac.university.globalService.repository.FacultyRepository;
import in.cdac.university.globalService.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApplicationTrackerService {

    @Autowired
    private ApplicationTrackerRepository applicationTrackerRepository;

    @Autowired
    private ApplicationTrackerDtlRepository applicationTrackerDtlRepository;

    @Autowired
    private ConfigApplicationDataMasterRepository applicantDataMasterRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private Language language;

    @Autowired
    private FtpUtility ftpUtility;

    @Autowired
    private RestUtility restUtility;

    @Transactional
    public ServiceResponse applicationScrutiny(ApplicationTrackerDtlBean applicationTrackerDtlBean) {
        if (applicationTrackerDtlBean.getUnumApplicationId() == null && applicationTrackerDtlBean.getUnumApplicationStatusId() == null) {
            return ServiceResponse.errorResponse(language.mandatory("Application Id && Application Status"));
        }
        if (applicationTrackerDtlBean.getUstrDocPath() != null && !applicationTrackerDtlBean.getUstrDocPath().isBlank()) {
            boolean isFileMoved = ftpUtility.moveFileFromTempToFinalDirectory(applicationTrackerDtlBean.getUstrDocPath());
            if (!isFileMoved) {
                throw new ApplicationException("Unable to upload file");
            }
        }

        Integer maxApplicationStatusSno = applicationTrackerDtlRepository.getApplicationStatusSno(applicationTrackerDtlBean.getUnumApplicationId());
        applicationTrackerDtlBean.setUnumApplicationStatusSno(maxApplicationStatusSno);

        applicantDataMasterRepository.updateApplicationEntryStatus(
                applicationTrackerDtlBean.getUnumApplicationStatusId(), applicationTrackerDtlBean.getUnumApplicationId(), applicationTrackerDtlBean.getUnumApplicantId(), 1
        );

        GbltConfigApplicationTracker gbltConfigApplicationTracker = BeanUtils.copyProperties(applicationTrackerDtlBean, GbltConfigApplicationTracker.class);
        applicationTrackerRepository.save(gbltConfigApplicationTracker);

        GbltConfigApplicationTrackerDtl gbltConfigApplicationTrackerDtl = BeanUtils.copyProperties(applicationTrackerDtlBean, GbltConfigApplicationTrackerDtl.class);
        applicationTrackerDtlRepository.save(gbltConfigApplicationTrackerDtl);

        return ServiceResponse.successMessage(language.saveSuccess("Application Scrutiny"));
    }


    public ServiceResponse getScrutinyDetailPlanningBoard(Long applicationId, Integer applicationStatus) {
        if (applicationId == null) {
            return ServiceResponse.errorResponse(language.mandatory("ApplicationId"));
        }
        Optional<GbltConfigApplicationTrackerDtl> gbltConfigApplicationTrackerDtlOptional = applicationTrackerDtlRepository.getScrutinyDetails(
                applicationId, applicationStatus
        );

        if (gbltConfigApplicationTrackerDtlOptional.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("ApplicationId", applicationId));
        }

        return ServiceResponse.successObject(
                BeanUtils.copyProperties(gbltConfigApplicationTrackerDtlOptional.get(), ApplicationTrackerDtlBean.class)
        );
    }

    public List<ApplicationTrackerBean> getApplicationForDepartmentScrutiny(Long notificationId, Long notificationDetailId, Integer levelId) throws Exception {

        NotificationBean[] notificationBean = restUtility.get(RestUtility.SERVICE_TYPE.PLANNING_BOARD, Constants.URL_GET_NOTIFICATIONS_BY_YEAR + "", NotificationBean[].class);

        List<GbltConfigApplicationTracker> applicationList = applicationTrackerRepository.getApplicationForDepartmentScrutiny(
                notificationId, notificationDetailId, levelId
        );

        Map<Integer, String> facultyNameMap = facultyRepository.getAllFaculty(RequestUtility.getUniversityId())
                .stream()
                .collect(Collectors.toMap(GmstCoursefacultyMst::getUnumCfacultyId, GmstCoursefacultyMst::getUstrCfacultyFname));

        return applicationList.stream()
                .map(application -> {
                    ApplicationTrackerBean applicationTrackerBean = BeanUtils.copyProperties(application, ApplicationTrackerBean.class);
                    applicationTrackerBean.setFacultyName(facultyNameMap.getOrDefault(application.getUnumNdtlFacultyId(), ""));

                    String applicantName = applicantRepository.findByUnumApplicantIdAndUnumIsvalid(application.getUnumApplicantId(), 1)
                            .map(GmstApplicantMst::getUstrApplicantName)
                            .orElse("");

                    applicationTrackerBean.setApplicantName(applicantName);
                    return applicationTrackerBean;
                })
                .toList();
    }
}
