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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
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

    @Transactional
    public ServiceResponse update(NotificationBean notificationBean) {
        // Check for Notification ID
        Long notificationId = notificationBean.getUnumNid();
        if (notificationId == null) {
            return ServiceResponse.errorResponse(language.mandatory("Notification"));
        }

        Optional<GbltNotificationMaster> gbltNotificationMasterOptional = masterRepository.findById(
                new GbltNotificationMasterPK(notificationId, 1));
        if (gbltNotificationMasterOptional.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Notification", notificationId));
        }

        // Upload Documents
        for (int i = 0; i < notificationBean.getDocuments().size(); i++) {
            NotificationDocumentBean notificationDocumentBean = notificationBean.getDocuments().get(i);
            log.debug("File Path: {}", notificationDocumentBean.getUstrFilePath());
            if (notificationDocumentBean.getUstrFilePath() != null && !notificationDocumentBean.getUstrFilePath().isBlank()) {
                // Save the file to permanent location on FTP
                FtpBean ftpBean = new FtpBean(notificationDocumentBean.getUstrFilePath());
                // Check if file exists in final directory
                String response = restUtility.postForMessage(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_IS_FILE_EXISTS, ftpBean, String.class);
                log.debug("Is File exists {}", response);
                if (response != null) {
                    // File already exists in final directory no need to move
                    continue;
                }

                log.debug("Moving file to final location");

                response = restUtility.postForMessage(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_MOVE_FILE, ftpBean, String.class);
                log.debug("File moved: {}", response);
                if (response == null) {
                    throw new ApplicationException("Unable to upload file");
                }
            }
        }
        // Create Log for Notification Master
        List<Long> notificationList = List.of(notificationId);
        int noOfRowsAffected = masterRepository.createLog(notificationList);
        if (noOfRowsAffected == 0)
            throw new ApplicationException(language.updateError("Notification"));

        detailRepository.createLog(notificationList);
        documentRepository.createLog(notificationList);

        GbltNotificationMaster gbltNotificationMaster = BeanUtils.copyProperties(notificationBean, GbltNotificationMaster.class);
        gbltNotificationMaster.setUnumNid(notificationId);
        masterRepository.save(gbltNotificationMaster);

        List<GbltNotificationDtl> gbltNotificationDtls = new ArrayList<>();
        if (notificationBean.getNotificationDetails() != null && notificationBean.getNotificationDetails().size() > 0) {
            for (int i = 0; i < notificationBean.getNotificationDetails().size(); i++) {
                GbltNotificationDtl gbltNotificationDtl = BeanUtils.copyProperties(notificationBean.getNotificationDetails().get(i), GbltNotificationDtl.class);
                gbltNotificationDtl.setUdtEntryDate(notificationBean.getUdtEntryDate());
                gbltNotificationDtl.setUnumUnivId(notificationBean.getUnumUnivId());
                gbltNotificationDtl.setUnumEntryUid(notificationBean.getUnumEntryUid());
                gbltNotificationDtl.setUnumNid(notificationId);
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
                gbltNotificationDocDtl.setUnumNid(notificationId);
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

    @Transactional
    public ServiceResponse delete(NotificationBean notificationBean, Long[] idsToDelete) {
        // Check for Notification ID
        if (idsToDelete == null || idsToDelete.length == 0) {
            return ServiceResponse.errorResponse(language.mandatory("Notification Id"));
        }
        List<GbltNotificationMasterPK> notificationMasterPKs = Arrays.stream(idsToDelete)
                .map(id -> new GbltNotificationMasterPK(id, 1))
                .toList();

        List<GbltNotificationMaster> gbltNotificationMasterList = masterRepository.findAllById(notificationMasterPKs);

        if (gbltNotificationMasterList.size() != idsToDelete.length) {
            return ServiceResponse.errorResponse(language.notFoundForId("Notification", Arrays.toString(idsToDelete)));
        }

        // Create Log for Notification Master
        List<Long> notificatonList = Arrays.asList(idsToDelete);
        int noOfRowsAffected = masterRepository.createLog(notificatonList);
        if (noOfRowsAffected == 0)
            throw new ApplicationException(language.deleteError("Notification"));

        List<GbltNotificationDtl> notificationDtls = detailRepository.findByUnumIsvalidAndUnumUnivIdAndUnumNidOrderByUnumSnoDisplayorderAsc(
                1, notificationBean.getUnumUnivId(), notificationBean.getUnumNid()
        );

        List<GbltNotificationDocDtl> notificationDocDtls = documentRepository.findByUnumIsvalidAndUnumUnivIdAndUnumNidOrderByUnumSnoDisplayorderAsc(
                1, notificationBean.getUnumUnivId(), notificationBean.getUnumNid()
        );
        detailRepository.createLog(notificatonList);
        documentRepository.createLog(notificatonList);

        // Delete
        // Master
        gbltNotificationMasterList.forEach(gbltNotificationMaster -> {
            gbltNotificationMaster.setUnumIsvalid(0);
            gbltNotificationMaster.setUdtEntryDate(notificationBean.getUdtEntryDate());
            gbltNotificationMaster.setUnumEntryUid(notificationBean.getUnumEntryUid());
        });
        masterRepository.saveAll(gbltNotificationMasterList);

        // Detail
        if (!notificationDtls.isEmpty()) {
            notificationDtls.forEach(notificationDtl -> {
                notificationDtl.setUnumIsvalid(0);
                notificationDtl.setUdtEntryDate(notificationBean.getUdtEntryDate());
                notificationDtl.setUnumEntryUid(notificationBean.getUnumEntryUid());
            });
            detailRepository.saveAll(notificationDtls);
        }

        // Document
        if (!notificationDocDtls.isEmpty()) {
            notificationDocDtls.forEach(notificationDocDtl -> {
                notificationDocDtl.setUnumIsvalid(1);
                notificationDocDtl.setUdtEntryDate(notificationBean.getUdtEntryDate());
                notificationDocDtl.setUnumEntryUid(notificationBean.getUnumEntryUid());
            });
            documentRepository.saveAll(notificationDocDtls);
        }

        return ServiceResponse.successMessage(language.deleteSuccess("Notification"));
    }

    public ServiceResponse getActiveNotifications() {
        Integer universityId = 1; //RequestUtility.getUniversityId();
        List<GbltNotificationMaster> activeNotifications = masterRepository.getActiveNotifications(universityId);

        NotificationTypeBean[] notificationTypes = restUtility.get(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_NOTIFICATION_TYPE, NotificationTypeBean[].class);
        Map<Integer, String> mapNotificationType = Arrays.stream(notificationTypes)
                .collect(Collectors.toMap(NotificationTypeBean::getUnumNtypeId, NotificationTypeBean::getUstrNtypeFname));

        CourseTypeBean[] courseTypeBeans = restUtility.get(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_ALL_COURSE_TYPES, CourseTypeBean[].class);
        Map<Integer, String> mapCourseType = Arrays.stream(courseTypeBeans)
                .collect(Collectors.toMap(CourseTypeBean::getUnumCtypeId, CourseTypeBean::getUstrCtypeFname));

        FacultyBean[] facultyBeans = restUtility.get(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_ALL_FACULTIES, FacultyBean[].class);
        Map<Integer, String> mapFaculty = Arrays.stream(facultyBeans)
                .collect(Collectors.toMap(FacultyBean::getUnumCfacultyId, FacultyBean::getUstrCfacultyFname));

        List<NotificationApplyBean> notificationApplyBeans = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.dateFormat);
        activeNotifications.forEach(activeNotification -> {
            // Get Documents Details
            List<GbltNotificationDocDtl> gbltNotificationDocDtls = documentRepository.findByUnumIsvalidAndUnumUnivIdAndUnumNidOrderByUnumSnoDisplayorderAsc(
                    1, universityId, activeNotification.getUnumNid()
            );
            NotificationApplyBean notificationApplyBean = new NotificationApplyBean();
            String notificationName = activeNotification.getUstrNMainHeading();
            if (activeNotification.getUstrNSubHeading() != null && !activeNotification.getUstrNSubHeading().isBlank()) {
                notificationName += ", " + activeNotification.getUstrNSubHeading();
            }
            notificationApplyBean.setNotificationName(notificationName);
            notificationApplyBean.setNotificationDate(sdf.format(activeNotification.getUdtNDt()));
            notificationApplyBean.setNotificationYear(activeNotification.getUstrAcademicYear());
            notificationApplyBean.setNotificationId(activeNotification.getUnumNid());
            notificationApplyBean.setNotificationDocuments(BeanUtils.copyListProperties(gbltNotificationDocDtls, NotificationDocumentBean.class));
            // Get Notification Details
            List<GbltNotificationDtl> notificationDtlList = detailRepository.findByUnumIsvalidAndUnumUnivIdAndUnumNidOrderByUnumSnoDisplayorderAsc(
                    1, universityId, activeNotification.getUnumNid()
            );

            List<NotificationApplyDetailBean> applyDetails = notificationDtlList.stream()
                    .map(notificationDtl -> {
                        NotificationApplyDetailBean notificationApplyDetailBean = BeanUtils.copyProperties(notificationDtl, NotificationApplyDetailBean.class);

                        String courseTypeName = mapCourseType.getOrDefault(notificationDtl.getUnumCoursetypeId(), "");
                        notificationApplyDetailBean.setCourseType(courseTypeName);

                        String notificationTypeName = mapNotificationType.getOrDefault(notificationDtl.getUnumNotificationTypeId(), "");
                        notificationApplyDetailBean.setNotificationType(notificationTypeName);

                        String facultyName = mapFaculty.getOrDefault(notificationDtl.getUnumFacultyId(), "");
                        notificationApplyDetailBean.setFaculty(facultyName);

                        return notificationApplyDetailBean;
                    })
                    .sorted(Comparator.comparing(NotificationApplyDetailBean::getCourseType)
                            .thenComparing(NotificationApplyDetailBean::getNotificationType)
                            .thenComparing(NotificationApplyDetailBean::getFaculty))
                    .toList();

            notificationApplyBean.setNotificationDetails(applyDetails);
            notificationApplyBeans.add(notificationApplyBean);
        });

        return ServiceResponse.successObject(notificationApplyBeans);
    }


    public List<NotificationDetailBean> getNotificationComboByYear(String year) {

        List<GbltNotificationDtl> notificationMasterList = masterRepository.getNotificationComboByYear(year);

        NotificationTypeBean[] notificationTypes = restUtility.get(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_NOTIFICATION_TYPE, NotificationTypeBean[].class);
        Map<Integer, String> mapNotificationType = Arrays.stream(notificationTypes)
                .collect(Collectors.toMap(NotificationTypeBean::getUnumNtypeId, NotificationTypeBean::getUstrNtypeFname));

        FacultyBean[] facultyBeans = restUtility.get(RestUtility.SERVICE_TYPE.GLOBAL, Constants.URL_GET_ALL_FACULTIES, FacultyBean[].class);
        Map<Integer, String> mapFaculty = Arrays.stream(facultyBeans)
                .collect(Collectors.toMap(FacultyBean::getUnumCfacultyId, FacultyBean::getUstrCfacultyFname));

        return notificationMasterList.stream()
                .map(gbltNotificationDtl -> {
                    NotificationDetailBean notificationDetailBean = new NotificationDetailBean();
                    notificationDetailBean.setUnumNid(gbltNotificationDtl.getUnumNid());
                    notificationDetailBean.setUnumNdtlId(gbltNotificationDtl.getUnumNdtlId());

                    String notificationTypeName = mapNotificationType.getOrDefault(gbltNotificationDtl.getUnumNotificationTypeId(), "");
                    String facultyName = mapFaculty.getOrDefault(gbltNotificationDtl.getUnumFacultyId(), "");

                    notificationDetailBean.setNotificationName(notificationTypeName + " - " + facultyName);
                    return notificationDetailBean;
                })
                .toList();
    }
}
