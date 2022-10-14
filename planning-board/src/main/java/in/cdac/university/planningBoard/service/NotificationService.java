package in.cdac.university.planningBoard.service;

import in.cdac.university.planningBoard.bean.*;
import in.cdac.university.planningBoard.entity.GbltNotificationDocDtl;
import in.cdac.university.planningBoard.entity.GbltNotificationDtl;
import in.cdac.university.planningBoard.entity.GbltNotificationMaster;
import in.cdac.university.planningBoard.entity.GbltNotificationMasterPK;
import in.cdac.university.planningBoard.exception.ApplicationException;
import in.cdac.university.planningBoard.repository.NotificationDetailRepository;
import in.cdac.university.planningBoard.repository.NotificationDocumentRepository;
import in.cdac.university.planningBoard.repository.NotificationMasterRepository;
import in.cdac.university.planningBoard.util.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationMasterRepository masterRepository;

    @Autowired
    private NotificationDetailRepository detailRepository;

    @Autowired
    private NotificationDocumentRepository documentRepository;

    @Autowired
    private Language language;

    @Autowired
    private RestUtility restUtility;

    @Transactional
    public ServiceResponse save(NotificationBean notificationBean) {
        // Upload Documents
        for (int i = 0; i < notificationBean.getDocuments().size(); i++) {
            NotificationDocumentBean notificationDocumentBean = notificationBean.getDocuments().get(i);
            if (notificationDocumentBean.getUstrFilePath() != null && !notificationDocumentBean.getUstrFilePath().isBlank()) {
                // Save the file to permanent location on FTP
                FtpBean ftpBean = new FtpBean(notificationDocumentBean.getUstrFilePath());
                String response = restUtility.postForMessage(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_MOVE_FILE, ftpBean, String.class);
                if (response == null) {
                    throw new ApplicationException("Unable to upload file");
                }
            }
        }


        GbltNotificationMaster gbltNotificationMaster = BeanUtils.copyProperties(notificationBean, GbltNotificationMaster.class);
        Long notificationId = masterRepository.getNextId();
        gbltNotificationMaster.setUnumNid(notificationId);
        gbltNotificationMaster = masterRepository.save(gbltNotificationMaster);

        List<GbltNotificationDtl> gbltNotificationDtls = new ArrayList<>();
        if (notificationBean.getNotificationDetails() != null && notificationBean.getNotificationDetails().size() > 0) {
            for (int i = 0; i < notificationBean.getNotificationDetails().size(); i++) {
                GbltNotificationDtl gbltNotificationDtl = BeanUtils.copyProperties(notificationBean.getNotificationDetails().get(i), GbltNotificationDtl.class);
                gbltNotificationDtl.setUdtEntryDate(notificationBean.getUdtEntryDate());
                gbltNotificationDtl.setUnumUnivId(notificationBean.getUnumUnivId());
                gbltNotificationDtl.setUnumEntryUid(notificationBean.getUnumEntryUid());
                gbltNotificationDtl.setUnumNid(notificationBean.getUnumNid());
                gbltNotificationDtl.setUnumIsvalid(1);
                gbltNotificationDtl.setUnumNid(gbltNotificationMaster.getUnumNid());
                gbltNotificationDtl.setUnumNdtlId(Long.valueOf(gbltNotificationMaster.getUnumNid() + StringUtils.leftPad(Integer.toString(i+1), 5, '0')));
                gbltNotificationDtl.setUnumSNo(i+1);
                gbltNotificationDtls.add(gbltNotificationDtl);
            }
        }
        if (!gbltNotificationDtls.isEmpty()) {
            detailRepository.saveAll(gbltNotificationDtls);
        }

        List<GbltNotificationDocDtl> gbltNotificationDocDtls = new ArrayList<>();
        int noOfDocumentsUploaded = 0;
        for (int i = 0; i < notificationBean.getDocuments().size(); i++) {
            NotificationDocumentBean notificationDocumentBean = notificationBean.getDocuments().get(i);
            if (notificationDocumentBean.getUstrFilePath() != null && !notificationDocumentBean.getUstrFilePath().isBlank()) {
                GbltNotificationDocDtl gbltNotificationDocDtl = BeanUtils.copyProperties(notificationDocumentBean, GbltNotificationDocDtl.class);
                gbltNotificationDocDtl.setUdtEntryDate(notificationBean.getUdtEntryDate());
                gbltNotificationDocDtl.setUnumUnivId(notificationBean.getUnumUnivId());
                gbltNotificationDocDtl.setUnumEntryUid(notificationBean.getUnumEntryUid());
                gbltNotificationDocDtl.setUnumNid(notificationBean.getUnumNid());
                gbltNotificationDocDtl.setUnumIsvalid(1);
                gbltNotificationDocDtl.setUnumNid(gbltNotificationMaster.getUnumNid());
                gbltNotificationDocDtl.setUnumNdocid(Long.valueOf(gbltNotificationMaster.getUnumNid() + StringUtils.leftPad(Integer.toString(i+1), 5, '0')));
                gbltNotificationDocDtl.setUnumSNo(i+1);
                gbltNotificationDocDtls.add(gbltNotificationDocDtl);
                noOfDocumentsUploaded++;
            }
        }
        if (noOfDocumentsUploaded == 0)
            throw new ApplicationException("No Document uploaded");

        if (!gbltNotificationDocDtls.isEmpty()) {
            documentRepository.saveAll(gbltNotificationDocDtls);
        }

        return ServiceResponse.builder()
                .status(1)
                .message(language.saveSuccess("Notification"))
                .build();
    }

    public List<NotificationBean> getListPageData(String year) {
        NotificationTypeBean[] notificationTypes = restUtility.get(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_NOTIFICATION_TYPE, NotificationTypeBean[].class);
        Map<Integer, String> mapNotificationType = Arrays.stream(notificationTypes)
                .collect(Collectors.toMap(NotificationTypeBean::getUnumNtypeId, NotificationTypeBean::getUstrNtypeFname));

        return masterRepository.findByUnumIsvalidAndUstrAcademicYearOrderByUdtNDtDesc(1, year)
                .stream()
                .map(gbltNotificationMaster -> {
                    NotificationBean notificationBean = BeanUtils.copyProperties(gbltNotificationMaster, NotificationBean.class);
                    notificationBean.setNotificationTypeName(mapNotificationType.getOrDefault(notificationBean.getUnumNtypeId(), ""));
                    return notificationBean;
                })
                .toList();
    }

    public ServiceResponse getNotificationById(Long notificationId) throws Exception {
        Optional<GbltNotificationMaster> gbltNotificationMasterOptional = masterRepository.findById(new GbltNotificationMasterPK(notificationId, 1));
        if (gbltNotificationMasterOptional.isEmpty())
            return ServiceResponse.errorResponse(language.notFoundForId("Notification", notificationId));

        GbltNotificationMaster gbltNotificationMaster = gbltNotificationMasterOptional.get();

        // Get Notification Type
        NotificationTypeBean notificationTypeBean = restUtility.get(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_NOTIFICATION_TYPE_BY_ID + gbltNotificationMaster.getUnumNtypeId(), NotificationTypeBean.class);
        if (notificationTypeBean == null)
            return ServiceResponse.errorResponse(language.notFoundForId("Notification Type", gbltNotificationMaster.getUnumNtypeId()));

        NotificationBean notificationBean = BeanUtils.copyProperties(gbltNotificationMaster, NotificationBean.class);
        notificationBean.setNotificationTypeName(notificationTypeBean.getUstrNtypeFname());

        // Get Notification Details
        List<GbltNotificationDtl> gbltNotificationDtls = detailRepository.findByUnumIsvalidAndUnumUnivIdAndUnumNidOrderByUnumSnoDisplayorderAsc(
                1, RequestUtility.getUniversityId(), notificationId
        );

        notificationBean.setNotificationDetails(BeanUtils.copyListProperties(gbltNotificationDtls, NotificationDetailBean.class));

        // Get Documents Details
        List<GbltNotificationDocDtl> gbltNotificationDocDtls = documentRepository.findByUnumIsvalidAndUnumUnivIdAndUnumNidOrderByUnumSnoDisplayorderAsc(
                1, RequestUtility.getUniversityId(), notificationId
        );

        notificationBean.setDocuments(BeanUtils.copyListProperties(gbltNotificationDocDtls, NotificationDocumentBean.class));

        return ServiceResponse.successObject(notificationBean);
    }
}
