package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.ApplicationDataBean;
import in.cdac.university.globalService.bean.NotificationBean;
import in.cdac.university.globalService.bean.NotificationDetailBean;
import in.cdac.university.globalService.entity.GbltConfigApplicationDataMst;
import in.cdac.university.globalService.entity.GmstApplicationStatusMst;
import in.cdac.university.globalService.entity.GmstNotificationTypeMst;
import in.cdac.university.globalService.repository.ConfigApplicantDataMasterRepository;
import in.cdac.university.globalService.repository.NotificationTypeRepository;
import in.cdac.university.globalService.repository.TemplateDetailRepository;
import in.cdac.university.globalService.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    @Autowired
    private ConfigApplicantDataMasterRepository applicantDataMasterRepository;

    @Autowired
    private RestUtility restUtility;

    @Autowired
    private NotificationTypeRepository notificationTypeRepository;


    public List<ApplicationDataBean> getApplicationsByUser(Long userId) throws Exception {

        Map<Integer, String> statusMap = applicantDataMasterRepository.getAllApplicationStatus()
                .stream()
                .collect(Collectors.toMap(GmstApplicationStatusMst::getUnumApplicationStatusId, GmstApplicationStatusMst::getUstrApplicationStatusName));

        Map<Integer, String> notificationTypeMap = notificationTypeRepository.getAllValidNotificationTypes(RequestUtility.getUniversityId())
                .stream()
                .collect(Collectors.toMap(GmstNotificationTypeMst::getUnumNtypeId, GmstNotificationTypeMst::getUstrNtypeFname));

        List<GbltConfigApplicationDataMst> applications = applicantDataMasterRepository.findByUnumIsvalidAndUnumUnivIdAndUnumApplicantId(1, RequestUtility.getUniversityId(), userId);
        List<ApplicationDataBean> applicationData = new ArrayList<>();
        for (GbltConfigApplicationDataMst application: applications) {
            ApplicationDataBean applicationDataBean = BeanUtils.copyProperties(application, ApplicationDataBean.class);
            applicationDataBean.setStatusName(statusMap.getOrDefault(application.getUnumApplicationEntryStatus(), ""));

            // Get Notification Detail
            NotificationBean notificationBean = restUtility.get(RestUtility.SERVICE_TYPE.PLANNING_BOARD, Constants.URL_GET_NOTIFICATION_BY_ID + application.getUnumNid(), NotificationBean.class);
            if (notificationBean != null) {
                notificationBean.getNotificationDetails().stream()
                        .filter(bean -> bean.getUnumNdtlId().equals(application.getUnumNdtlId()))
                        .findFirst()
                        .ifPresent(notificationDetailBean ->
                                applicationDataBean.setNotificationName(
                                        notificationTypeMap.getOrDefault(notificationDetailBean.getUnumNotificationTypeId(), "")));

            }

            applicationData.add(applicationDataBean);
        }

        return applicationData;
    }
    
    
    public List<ApplicationDataBean> getApplicationByunumApplicationId(Long unumApplicationId) throws Exception {
    	
    	return BeanUtils.copyListProperties(applicantDataMasterRepository.getApplicationByUnumApplicationIdAndUnumUnivIdAndUnumIsvalid(unumApplicationId,RequestUtility.getUniversityId(),1), ApplicationDataBean.class) ;
    	
    }
    
}
