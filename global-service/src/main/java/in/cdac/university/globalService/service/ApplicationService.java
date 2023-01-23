package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.ApplicationDataBean;
import in.cdac.university.globalService.bean.NotificationBean;
import in.cdac.university.globalService.bean.NotificationDetailBean;
import in.cdac.university.globalService.controller.GmstCourseTypeMst;
import in.cdac.university.globalService.entity.*;
import in.cdac.university.globalService.repository.*;
import in.cdac.university.globalService.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    @Autowired
    private ConfigApplicationDataMasterRepository applicationDataMasterRepository;

    @Autowired
    private RestUtility restUtility;

    @Autowired
    private NotificationTypeRepository notificationTypeRepository;

    @Autowired
    private Language language;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private ApplicantTypeRepository applicantTypeRepository;
    @Autowired
    private CourseTypeRepository courseTypeRepository;


    public List<ApplicationDataBean> getApplicationsByUser(Long userId) throws Exception {

        Map<Integer, String> statusMap = applicationDataMasterRepository.getAllApplicationStatus().stream().collect(Collectors.toMap(GmstApplicationStatusMst::getUnumApplicationStatusId, GmstApplicationStatusMst::getUstrApplicationStatusName));

        Map<Integer, String> notificationTypeMap = notificationTypeRepository.getAllValidNotificationTypes(RequestUtility.getUniversityId()).stream().collect(Collectors.toMap(GmstNotificationTypeMst::getUnumNtypeId, GmstNotificationTypeMst::getUstrNtypeFname));

        List<GbltConfigApplicationDataMst> applications = applicationDataMasterRepository.findByUnumIsvalidAndUnumUnivIdAndUnumApplicantId(1, RequestUtility.getUniversityId(), userId);
        List<ApplicationDataBean> applicationData = new ArrayList<>();
        for (GbltConfigApplicationDataMst application : applications) {
            ApplicationDataBean applicationDataBean = BeanUtils.copyProperties(application, ApplicationDataBean.class);
            applicationDataBean.setStatusName(statusMap.getOrDefault(application.getUnumApplicationEntryStatus(), ""));
            Optional<GmstCoursefacultyMst> faculty = facultyRepository.findByUnumCfacultyIdAndUnumIsvalid(Math.toIntExact(applicationDataBean.getUnumNdtlFacultyId()), 1);
            faculty.ifPresent(gmstCoursefacultyMst -> applicationDataBean.setFacultyName(gmstCoursefacultyMst.getUstrCfacultyFname()));

            // Get Notification Detail
            NotificationBean notificationBean = restUtility.get(RestUtility.SERVICE_TYPE.PLANNING_BOARD, Constants.URL_GET_NOTIFICATION_BY_ID + application.getUnumNid(), NotificationBean.class);
            if (notificationBean != null) {
                notificationBean.getNotificationDetails()
                        .stream()
                        .filter(bean -> bean.getUnumNdtlId().equals(application.getUnumNdtlId()))
                        .findFirst()
                        .ifPresent(notificationDetailBean -> applicationDataBean.setNotificationName(notificationTypeMap.getOrDefault(notificationDetailBean.getUnumNotificationTypeId(), "")));
            }

            applicationData.add(applicationDataBean);
        }

        return applicationData;
    }


    public ServiceResponse getApplicationByunumApplicationId(Long unumApplicationId) throws Exception {

        Optional<GbltConfigApplicationDataMst> application = applicationDataMasterRepository.getApplicationByUnumApplicationIdAndUnumUnivIdAndUnumIsvalid(
                unumApplicationId, RequestUtility.getUniversityId(), 1);

        if (application.isEmpty())
            return ServiceResponse.errorResponse(language.notFoundForId("Application", unumApplicationId));

        ApplicationDataBean applicationDataBean = BeanUtils.copyProperties(application.get(), ApplicationDataBean.class);

        Optional<GmstCoursefacultyMst> faculty = facultyRepository.findByUnumCfacultyIdAndUnumIsvalid(Math.toIntExact(applicationDataBean.getUnumNdtlFacultyId()), 1);
        faculty.ifPresent(gmstCoursefacultyMst -> applicationDataBean.setFacultyName(gmstCoursefacultyMst.getUstrCfacultyFname()));

        Optional<GmstApplicantMst> applicantOptional = applicantRepository.findByUnumApplicantIdAndUnumIsvalid(applicationDataBean.getUnumApplicantId(), 1);
        if (applicantOptional.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Applicant", applicationDataBean.getUnumApplicantId()));
        }

        GmstApplicantMst applicant = applicantOptional.get();
        applicationDataBean.setUstrApplicantName(applicant.getUstrApplicantName());
        Optional<GmstApplicantTypeMst> applicantType = applicantTypeRepository.findById(new GmstApplicantTypeMstPK(applicant.getUnumApplicantTypeId(), 1));
        applicantType.ifPresent(aType -> applicationDataBean.setApplicantTypeName(aType.getUstrApplicantTypeFname()));
        applicationDataBean.setApplicantUserName(applicant.getUstrUid());

        NotificationBean notificationBean = restUtility.get(RestUtility.SERVICE_TYPE.PLANNING_BOARD, Constants.URL_GET_NOTIFICATION_BY_ID + applicationDataBean.getUnumNid(), NotificationBean.class);
        if (notificationBean == null)
            return ServiceResponse.errorResponse(language.notFoundForId("Notification", applicationDataBean.getUnumNid()));
        applicationDataBean.setUdtNDt(notificationBean.getUdtNDt());

        Optional<NotificationDetailBean> notificationDetail = notificationBean.getNotificationDetails()
                .stream()
                .filter(notificationDetailBean -> notificationDetailBean.getUnumNdtlId().equals(applicationDataBean.getUnumNdtlId()))
                .findFirst();
        if (notificationDetail.isEmpty())
            return ServiceResponse.errorResponse(language.notFoundForId("Notification Detail", applicationDataBean.getUnumNdtlId()));

        int courseTypeId = notificationDetail.get().getUnumCoursetypeId();
        Optional<GmstCourseTypeMst> courseTypeMst = courseTypeRepository.findByUnumIsvalidAndUnumCtypeId(1, (long) courseTypeId);
        courseTypeMst.ifPresent(courseType -> applicationDataBean.setCourseTypeName(courseType.getUstrCtypeFname()));

        return ServiceResponse.successObject(applicationDataBean);
    }

    public ServiceResponse getApplicationDetailByNotificationId(Long notificationId) throws Exception {

        List<GbltConfigApplicationDataMst> application = applicationDataMasterRepository.getApplicationByUnumNidAndUnumUnivIdAndUnumIsvalid(
                notificationId, RequestUtility.getUniversityId(), 1);

        if (application.isEmpty())
            return ServiceResponse.errorResponse(language.notFoundForId("Application", notificationId));

        ApplicationDataBean applicationDataBean = BeanUtils.copyProperties(application.get(0), ApplicationDataBean.class);

        return ServiceResponse.successObject(applicationDataBean);
    }
}
