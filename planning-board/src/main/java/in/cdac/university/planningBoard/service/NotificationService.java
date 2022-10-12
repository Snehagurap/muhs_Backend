package in.cdac.university.planningBoard.service;

import in.cdac.university.planningBoard.bean.FtpBean;
import in.cdac.university.planningBoard.bean.NotificationBean;
import in.cdac.university.planningBoard.bean.NotificationDocumentBean;
import in.cdac.university.planningBoard.entity.GbltNotificationDocDtl;
import in.cdac.university.planningBoard.entity.GbltNotificationDtl;
import in.cdac.university.planningBoard.entity.GbltNotificationMaster;
import in.cdac.university.planningBoard.exception.ApplicationException;
import in.cdac.university.planningBoard.repository.NotificationDetailRepository;
import in.cdac.university.planningBoard.repository.NotificationDocumentRepository;
import in.cdac.university.planningBoard.repository.NotificationMasterRepository;
import in.cdac.university.planningBoard.util.BeanUtils;
import in.cdac.university.planningBoard.util.Language;
import in.cdac.university.planningBoard.util.RestUtility;
import in.cdac.university.planningBoard.util.ServiceResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
                String response = restUtility.post(RestUtility.SERVICE_TYPE.GLOBAL, "file/moveToFinal", ftpBean, String.class);
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
            }
        }
        if (!gbltNotificationDocDtls.isEmpty()) {
            documentRepository.saveAll(gbltNotificationDocDtls);
        }

        return ServiceResponse.builder()
                .status(1)
                .message(language.saveSuccess("Notification"))
                .build();
    }
}
