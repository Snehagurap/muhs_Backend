package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.*;
import in.cdac.university.globalService.entity.*;
import in.cdac.university.globalService.exception.ApplicationException;
import in.cdac.university.globalService.repository.*;
import in.cdac.university.globalService.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MasterTemplateService {

    @Autowired
    private MasterTemplateRepository masterTemplateRepository;

    @Autowired
    private MasterTemplateDetailRepository masterTemplateDetailRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private TemplateDetailRepository templateDetailRepository;

    @Autowired
    private TemplateItemRepository templateItemRepository;

    @Autowired
    private TemplateHeaderRepository templateHeaderRepository;

    @Autowired
    private TemplateComponentRepository templateComponentRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private CheckListService checkListService;

    @Autowired
    private Language language;

    @Autowired
    private RestUtility restUtility;

    @Autowired
    private ConfigApplicationDataMasterRepository applicationDataMasterRepository;

    @Autowired
    private ConfigApplicationDataDetailRepository applicationDataDetailRepository;

    @Autowired
    ApplicationTrackerRepository applicationTrackerRepository;

    @Autowired
    ApplicationTrackerDtlRepository applicationTrackerDtlRepository;

    @Autowired
    private FtpUtility ftpUtility;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private TemplateService templateService;


    public ServiceResponse getTemplate(Long masterTemplateId, Long notificationId, Long notificationDetailId) throws Exception {
        log.debug("Master Template: {}", masterTemplateId);
        log.debug("Notification Id: {}", notificationId);
        log.debug("Notification Details Id: {}", notificationDetailId);
        Optional<GmstConfigMastertemplateMst> templateByIdOptional = masterTemplateRepository.findById(new GmstConfigMastertemplateMstPK(masterTemplateId, 1));
        if (templateByIdOptional.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Master Template", masterTemplateId));
        }
        MasterTemplateBean masterTemplateBean = BeanUtils.copyProperties(templateByIdOptional.get(), MasterTemplateBean.class);
        Integer universityId = RequestUtility.getUniversityId();

        // Get Notification Detail
        NotificationBean notificationBean = restUtility.get(RestUtility.SERVICE_TYPE.PLANNING_BOARD, Constants.URL_GET_NOTIFICATION_BY_ID + notificationId, NotificationBean.class);
        if (notificationBean == null)
            return ServiceResponse.errorResponse(language.notFoundForId("Notification", notificationId));

        NotificationDetailBean notificationDetailBean = notificationBean.getNotificationDetails().stream()
                .filter(bean -> bean.getUnumNdtlId().equals(notificationDetailId))
                .findFirst()
                .orElse(null);

        if (notificationDetailBean == null)
            return ServiceResponse.errorResponse(language.notFoundForId("Notification Detail", notificationDetailId));

        Optional<GmstCoursefacultyMst> facultyById = facultyRepository.findById(new GmstCoursefacultyMstPK(notificationDetailBean.getUnumFacultyId(), 1));
        String tempFacultyName = "";
        if (facultyById.isPresent()) {
            tempFacultyName = facultyById.get().getUstrCfacultyFname();
        }

        final String facultyName = tempFacultyName;
        final String purpose = notificationBean.getUstrNSubHeading() == null ? "" : notificationBean.getUstrNSubHeading();
        final String academicYear = notificationBean.getUstrAcademicYear();

        // Check if application already exists
        Map<Long, String> mapItemValues = new HashMap<>();
        Optional<GbltConfigApplicationDataMst> applicationOptional = applicationDataMasterRepository.getApplication(universityId, RequestUtility.getUserId(), notificationId, notificationDetailId);
        if (applicationOptional.isPresent()) {
            GbltConfigApplicationDataMst applicationDataMst = applicationOptional.get();
            masterTemplateBean.setUnumApplicationEntryStatus(applicationDataMst.getUnumApplicationEntryStatus());
            masterTemplateBean.setUnumApplicationId(applicationDataMst.getUnumApplicationId());

            // Get details of application item wise
            List<GbltConfigApplicationDataDtl> applicationDetails = applicationDataDetailRepository.findByUnumIsvalidAndUnumUnivIdAndUnumApplicationId(1, universityId, applicationDataMst.getUnumApplicationId());
            mapItemValues = applicationDetails.stream()
                    .collect(Collectors.toMap(GbltConfigApplicationDataDtl::getUnumTempleItemId,
                            applicationDetail -> applicationDetail.getUstrItemValue() == null ? "" : applicationDetail.getUstrItemValue(),
                            (v1, v2) -> v2));
        } else {
            masterTemplateBean.setUnumApplicationEntryStatus(0);
        }
        final Map<Long, String> itemMap = mapItemValues;

        List<GmstConfigMastertemplateTemplatedtl> masterTemplateDetails = masterTemplateDetailRepository.findByUnumIsvalidAndUnumUnivIdAndUnumMtempleId(1, universityId, masterTemplateBean.getUnumMtempleId());

        // Template ids for the given Master template
        Map<Long, Long> masterTemplateDetailIds = masterTemplateDetails.stream()
                .collect(Collectors.toMap(GmstConfigMastertemplateTemplatedtl::getUnumTempleId, GmstConfigMastertemplateTemplatedtl::getUnumMtempledtlId));
        log.debug("Master Templates Map: {}", masterTemplateDetailIds);

        List<Long> templateIds = new ArrayList<>(masterTemplateDetailIds.keySet());
        log.debug("Templates on the basis of Master template: {}", templateIds);

        // Get the template on the basis of master template
        List<TemplateBean> templateBeans = getTemplateBeans(masterTemplateDetailIds, templateIds);
        masterTemplateBean.setTemplateList(templateBeans);

        // Get Template details
        processTemplateItems(notificationDetailBean.getUnumFacultyId(), universityId, facultyName, purpose, itemMap, templateIds, templateBeans, academicYear);

        return ServiceResponse.successObject(masterTemplateBean);
    }

    public ServiceResponse getTemplate(Long templateIdToPreview, Integer facultyId) throws Exception {
        log.debug("Master Template: {}", templateIdToPreview);
        Integer universityId = RequestUtility.getUniversityId();
        final String facultyName = "#faculty_name#";
        final String purpose = "#application_purpose#";
        final String academicYear = "#acad_year#";

        final Map<Long, String> itemMap = new HashMap<>();

        // Template ids for the given Master template
        Map<Long, Long> masterTemplateDetailIds = new HashMap<>();
        masterTemplateDetailIds.put(templateIdToPreview, 0L);
        log.debug("Master Templates Map: {}", masterTemplateDetailIds);

        List<Long> templateIds = new ArrayList<>(masterTemplateDetailIds.keySet());
        log.debug("Templates on the basis of Master template: {}", templateIds);

        MasterTemplateBean masterTemplateBean = new MasterTemplateBean();
        masterTemplateBean.setUnumMtempleId(0L);

        // Get the template on the basis of master template
        List<TemplateBean> templateBeans = getTemplateBeans(masterTemplateDetailIds, templateIds);
        masterTemplateBean.setTemplateList(templateBeans);

        // Get Template details
        processTemplateItems(facultyId, universityId, facultyName, purpose, itemMap, templateIds, templateBeans, academicYear);

        return ServiceResponse.successObject(masterTemplateBean);
    }

    public ServiceResponse getTemplate(Long applicationId) throws Exception {
        Integer universityId = RequestUtility.getUniversityId();

        Optional<GbltConfigApplicationDataMst> applicationOptional = applicationDataMasterRepository.findByUnumApplicationIdAndUnumIsvalidAndUnumUnivId(applicationId, 1, universityId);
        if (applicationOptional.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Application", applicationId));
        }
        GbltConfigApplicationDataMst application = applicationOptional.get();

        long masterTemplateId = application.getUnumMtempleId();
        Optional<GmstConfigMastertemplateMst> templateByIdOptional = masterTemplateRepository.findById(new GmstConfigMastertemplateMstPK(masterTemplateId, 1));
        if (templateByIdOptional.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Master Template", masterTemplateId));
        }
        MasterTemplateBean masterTemplateBean = BeanUtils.copyProperties(templateByIdOptional.get(), MasterTemplateBean.class);

        long notificationId = application.getUnumNid();
        long notificationDetailId = application.getUnumNdtlId();

        // Get Notification Detail
        NotificationBean notificationBean = restUtility.get(RestUtility.SERVICE_TYPE.PLANNING_BOARD, Constants.URL_GET_NOTIFICATION_BY_ID + notificationId, NotificationBean.class);
        if (notificationBean == null)
            return ServiceResponse.errorResponse(language.notFoundForId("Notification", notificationId));

        NotificationDetailBean notificationDetailBean = notificationBean.getNotificationDetails().stream()
                .filter(bean -> bean.getUnumNdtlId().equals(notificationDetailId))
                .findFirst()
                .orElse(null);

        if (notificationDetailBean == null)
            return ServiceResponse.errorResponse(language.notFoundForId("Notification Detail", notificationDetailId));

        Optional<GmstCoursefacultyMst> facultyById = facultyRepository.findById(new GmstCoursefacultyMstPK(notificationDetailBean.getUnumFacultyId(), 1));
        String tempFacultyName = "";
        if (facultyById.isPresent()) {
            tempFacultyName = facultyById.get().getUstrCfacultyFname();
        }

        final String facultyName = tempFacultyName;
        final String purpose = notificationBean.getUstrNSubHeading() == null ? "" : notificationBean.getUstrNSubHeading();
        final String academicYear = notificationBean.getUstrAcademicYear();
        // Check if application already exists

        GbltConfigApplicationDataMst applicationDataMst = applicationOptional.get();
        masterTemplateBean.setUnumApplicationEntryStatus(applicationDataMst.getUnumApplicationEntryStatus());
        masterTemplateBean.setUnumApplicationId(applicationDataMst.getUnumApplicationId());

        // Get details of application item wise
        List<GbltConfigApplicationDataDtl> applicationDetails = applicationDataDetailRepository.findByUnumIsvalidAndUnumUnivIdAndUnumApplicationId(1, universityId, applicationDataMst.getUnumApplicationId());
        Map<Long, String> itemMap = applicationDetails.stream()
                .collect(Collectors.toMap(GbltConfigApplicationDataDtl::getUnumTempleItemId,
                        applicationDetail -> applicationDetail.getUstrItemValue() == null ? "" : applicationDetail.getUstrItemValue(),
                        (v1, v2) -> v2));

        List<GmstConfigMastertemplateTemplatedtl> masterTemplateDetails = masterTemplateDetailRepository.findByUnumIsvalidAndUnumUnivIdAndUnumMtempleId(1, universityId, masterTemplateBean.getUnumMtempleId());

        // Template ids for the given Master template
        Map<Long, Long> masterTemplateDetailIds = masterTemplateDetails.stream()
                .collect(Collectors.toMap(GmstConfigMastertemplateTemplatedtl::getUnumTempleId, GmstConfigMastertemplateTemplatedtl::getUnumMtempledtlId));
        log.debug("Master Templates Map: {}", masterTemplateDetailIds);

        List<Long> templateIds = new ArrayList<>(masterTemplateDetailIds.keySet());
        log.debug("Templates on the basis of Master template: {}", templateIds);

        // Get the template on the basis of master template
        List<TemplateBean> templateBeans = getTemplateBeans(masterTemplateDetailIds, templateIds);
        masterTemplateBean.setTemplateList(templateBeans);

        // Get Template details
        processTemplateItems(notificationDetailBean.getUnumFacultyId(), universityId, facultyName, purpose, itemMap, templateIds, templateBeans, academicYear);

        return ServiceResponse.successObject(masterTemplateBean);
    }

    private void processTemplateItems(Integer facultyId, Integer universityId, String facultyName, String purpose, Map<Long, String> itemMap,
                                      List<Long> templateIds, List<TemplateBean> templateBeans, String academicYear) {
        List<GmstConfigTemplateDtl> templateDetailList = templateDetailRepository.findByUnumIsvalidAndUnumUnivIdAndUnumTempleIdIn(1, universityId, templateIds);
        Map<Long, List<GmstConfigTemplateDtl>> mapTemplateDetails = templateDetailList
                .stream()
                .collect(Collectors.groupingBy(GmstConfigTemplateDtl::getUnumTempleId));

        // Get Headers by Template id
        List<GmstConfigTemplateHeaderMst> headersByTemplateId = templateHeaderRepository.findHeadersByTemplateId(universityId, templateIds);

        // Get component by template id
        List<GmstConfigTemplateComponentMst> componentsByTemplateId = templateComponentRepository.findComponentsByTemplateId(universityId, templateIds);

        // Get items by template id
        List<GmstConfigTemplateItemMst> itemsByTemplateId = templateItemRepository.findItemsByTemplateId(universityId, templateIds)
                .stream()
                .filter(item -> switch (facultyId) {
                    case 10 -> item.getUnumMedicalFlag() != null && item.getUnumMedicalFlag() == 1;
                    case 11 -> item.getUnumDentalFlag() != null && item.getUnumDentalFlag() == 1;
                    case 12 -> item.getUnumAyurvedFlag() != null && item.getUnumAyurvedFlag() == 1;
                    case 13 -> item.getUnumUnaniFlag() != null && item.getUnumUnaniFlag() == 1;
                    case 14 -> item.getUnumHomeopathyFlag() != null && item.getUnumHomeopathyFlag() == 1;
                    case 15 -> item.getUnumNursingFlag() != null && item.getUnumNursingFlag() == 1;
                    case 16 -> item.getUnumPhysiotherapyFlag() != null && item.getUnumPhysiotherapyFlag() == 1;
                    case 17 ->
                            item.getUnumOccupationalTherapyFlag() != null && item.getUnumOccupationalTherapyFlag() == 1;
                    case 18 ->
                            item.getUnumAudiologyAndSpeechFlag() != null && item.getUnumAudiologyAndSpeechFlag() == 1;
                    case 19 -> item.getUnumPAndOFlag() != null && item.getUnumPAndOFlag() == 1;
                    default -> true;
                })
                .collect(Collectors.toList());

        Map<Long, Integer> itemControlMap = itemsByTemplateId.stream()
                .collect(Collectors.toMap(GmstConfigTemplateItemMst::getUnumTempleItemId, GmstConfigTemplateItemMst::getUnumUiControlId));

        for (TemplateBean templateBean : templateBeans) {
            Long templateId = templateBean.getUnumTempleId();
            log.debug("Template Id: {}", templateId);

            CheckListBean checkList = new CheckListBean();
            checkList.setUnumTempleId(templateId);
            List<CheckListItems> checkListItems = new ArrayList<>();
            checkList.setCheckListItems(checkListItems);

            List<GmstConfigTemplateDtl> templateDetailByTemplateId = mapTemplateDetails.get(templateId);
            if (templateDetailByTemplateId == null)
                continue;

            Map<Long, TemplateHeaderBean> headerIdsAdded = new HashMap<>();
            Map<Long, Integer> mapNoOfPageColumns = new HashMap<>();
            List<TemplateHeaderBean> headerBeans = new ArrayList<>();
            boolean isCheckListPresent = false;
            for (GmstConfigTemplateDtl configTemplateDtl : templateDetailByTemplateId) {
                if (configTemplateDtl.getUnumTempleHeadId() == 27 || !headerIdsAdded.containsKey(configTemplateDtl.getUnumTempleHeadId())) {
                    GmstConfigTemplateHeaderMst gmstConfigTemplateHeaderMst = headersByTemplateId.stream()
                            .filter(header -> header.getUnumTempleHeadId().equals(configTemplateDtl.getUnumTempleHeadId()))
                            .findFirst()
                            .orElse(null);
                    if (gmstConfigTemplateHeaderMst == null)
                        continue;

                    TemplateHeaderBean templateHeaderBean = BeanUtils.copyProperties(gmstConfigTemplateHeaderMst, TemplateHeaderBean.class);
                    if (configTemplateDtl.getUnumTempleHeadId() == 27) {
                        templateHeaderBean.setUstrHeadPrintText(configTemplateDtl.getUstrTempledtlDescription());
                    }
                    templateHeaderBean.setUnumHeadDisplayOrder(configTemplateDtl.getUnumHeaderOrderNo() == null ? 0 : configTemplateDtl.getUnumHeaderOrderNo());
                    templateHeaderBean.setUnumIsHidden(configTemplateDtl.getUnumHideHeaderTxt() == null ? 0 : configTemplateDtl.getUnumHideHeaderTxt());
                    templateHeaderBean.setUnumTempledtlId(configTemplateDtl.getUnumTempledtlId());

                    if (configTemplateDtl.getUnumPageColumns() != null) {
                        int unumPageColumns = configTemplateDtl.getUnumPageColumns();
                        mapNoOfPageColumns.put(configTemplateDtl.getUnumTempleHeadId(), unumPageColumns);
                    }
                    templateHeaderBean.setUnumPageColumns(mapNoOfPageColumns.getOrDefault(configTemplateDtl.getUnumTempleHeadId(), 2));
                    templateHeaderBean.setUnumHeadIsmandy(gmstConfigTemplateHeaderMst.getUnumHeadIsmandy() == null ? 0 : gmstConfigTemplateHeaderMst.getUnumHeadIsmandy());
                    templateHeaderBean.setUnumIsMergeWithParent(gmstConfigTemplateHeaderMst.getUnumIsMergeWithParent() == null ? 0 : gmstConfigTemplateHeaderMst.getUnumIsMergeWithParent());

                    // Replace constants
                    templateHeaderBean.setUstrHeadPrintText(replaceConstantString(templateHeaderBean.getUstrHeadPrintText(), facultyName, purpose, academicYear));
                    headerBeans.add(templateHeaderBean);
                    headerIdsAdded.put(configTemplateDtl.getUnumTempleHeadId(), templateHeaderBean);
                } else {
                    TemplateHeaderBean templateHeaderBean = headerIdsAdded.get(configTemplateDtl.getUnumTempleHeadId());
                    if (configTemplateDtl.getUnumPageColumns() != null) {
                        templateHeaderBean.setUnumPageColumns(configTemplateDtl.getUnumPageColumns());
                    }
                }

                // Process checklist items
                if (configTemplateDtl.getUnumChecklistId() != null
                        && configTemplateDtl.getUstrChecklistName() != null
                        && configTemplateDtl.getUstrChecklistItemName() != null
                        && configTemplateDtl.getUnumChecklistItemOrderno() != null) {
                    checkList.setUnumChecklistId(configTemplateDtl.getUnumChecklistId());
                    checkList.setUstrChecklistName(configTemplateDtl.getUstrChecklistName());
                    CheckListItems checkListItem = new CheckListItems();
                    checkListItem.setUnumTempleItemId(configTemplateDtl.getUnumTempleItemId());
                    checkListItem.setUstrChecklistItemName(configTemplateDtl.getUstrChecklistItemName());
                    checkListItem.setUnumChecklistItemOrderno(configTemplateDtl.getUnumChecklistItemOrderno());
                    checkListItem.setUnumUiControlId(itemControlMap.getOrDefault(configTemplateDtl.getUnumTempleItemId(), 0));
                    checkListItem.setUstrItemValue(itemMap.getOrDefault(configTemplateDtl.getUnumTempleItemId(), ""));
                    checkListItems.add(checkListItem);
                    isCheckListPresent = true;
                }
            }
            if (isCheckListPresent) {
                checkListItems.sort(Comparator.comparing(CheckListItems::getUnumChecklistItemOrderno));
                templateBean.setCheckList(checkList);
            }
            headerBeans.sort(Comparator.comparing(TemplateHeaderBean::getUnumHeadDisplayOrder, Comparator.nullsLast(Comparator.naturalOrder())));

            templateBean.setHeaders(headerBeans);

            // Get Component Detail for each header
            int noOfPages = 0;
            for (TemplateHeaderBean templateHeaderBean : headerBeans) {
                Long headerId = templateHeaderBean.getUnumTempleHeadId();
                if (headerId == 27)
                    noOfPages++;
                Map<Long, GmstConfigTemplateDtl> componentsInTemplate = templateDetailByTemplateId.stream()
                        .filter(gmstConfigTemplateDtl -> gmstConfigTemplateDtl.getUnumTempleHeadId().equals(headerId))
                        .collect(Collectors.toMap(GmstConfigTemplateDtl::getUnumTempleCompId, Function.identity(), (u1, u2) -> u1));

                List<TemplateComponentBean> templateComponentBeans = componentsByTemplateId.stream()
                        .filter(gmstConfigTemplateComponentMst -> gmstConfigTemplateComponentMst.getUnumTempleCompId() != null &&
                                componentsInTemplate.containsKey(gmstConfigTemplateComponentMst.getUnumTempleCompId()))
                        .map(gmstConfigTemplateComponentMst -> {
                            TemplateComponentBean templateComponentBean = BeanUtils.copyProperties(gmstConfigTemplateComponentMst, TemplateComponentBean.class);
                            GmstConfigTemplateDtl templateDtl = componentsInTemplate.get(gmstConfigTemplateComponentMst.getUnumTempleCompId());
                            templateComponentBean.setUnumCompDisplayOrder(templateDtl.getUnumComponentOrderNo() == null ? 0 : templateDtl.getUnumComponentOrderNo());
                            templateComponentBean.setUnumIsHidden(templateDtl.getUnumHideComponentTxt() == null ? 0 : templateDtl.getUnumHideComponentTxt());
                            templateComponentBean.setUnumTempledtlId(templateDtl.getUnumTempledtlId());

                            // Replace constants
                            templateComponentBean.setUstrCompPrintText(
                                    replaceConstantString(gmstConfigTemplateComponentMst.getUstrCompPrintText(), facultyName, purpose, academicYear)
                            );

                            return templateComponentBean;
                        })
                        .sorted(Comparator.comparing(TemplateComponentBean::getUnumCompDisplayOrder, Comparator.nullsLast(Comparator.naturalOrder())))
                        .toList();

                templateHeaderBean.setComponents(templateComponentBeans);

                // Get Item for each Component
                for (TemplateComponentBean templateComponentBean : templateComponentBeans) {
                    Long componentId = templateComponentBean.getUnumTempleCompId();
                    Map<Long, GmstConfigTemplateDtl> itemsInTemplate = templateDetailByTemplateId.stream()
                            .filter(gmstConfigTemplateDtl -> gmstConfigTemplateDtl.getUnumTempleHeadId() != null && gmstConfigTemplateDtl.getUnumTempleHeadId().equals(headerId))
                            .filter(gmstConfigTemplateDtl -> gmstConfigTemplateDtl.getUnumTempleCompId() != null
                                    && gmstConfigTemplateDtl.getUnumTempleCompId().equals(componentId))
                            .collect(Collectors.toMap(GmstConfigTemplateDtl::getUnumTempleItemId, Function.identity(), (u1, u2) -> u1));

                    List<TemplateItemBean> templateItemBeans = itemsByTemplateId.stream()
                            .filter(gmstConfigTemplateItemMst -> gmstConfigTemplateItemMst.getUnumTempleItemId() != null &&
                                    itemsInTemplate.containsKey(gmstConfigTemplateItemMst.getUnumTempleItemId()))
                            .filter(gmstConfigTemplateItemMst -> gmstConfigTemplateItemMst.getUnumTempleParentItemId() == null ||
                                    gmstConfigTemplateItemMst.getUnumTempleParentItemId().equals(0L))
                            .map(gmstConfigTemplateItemMst -> {
                                TemplateItemBean templateItemBean = BeanUtils.copyProperties(gmstConfigTemplateItemMst, TemplateItemBean.class);
                                GmstConfigTemplateDtl templateDtl = itemsInTemplate.get(gmstConfigTemplateItemMst.getUnumTempleItemId());
                                templateItemBean.setUnumItemDisplayOrder(templateDtl.getUnumDisplayOrder());
                                templateItemBean.setUnumIsHidden(templateDtl.getUnumHideItemTxt() == null ? 0 : templateDtl.getUnumHideItemTxt());
                                templateItemBean.setUnumTempledtlId(templateDtl.getUnumTempledtlId());
                                templateItemBean.setUnumTempleCompItemId(templateDtl.getUnumTempleCompItemId());
                                templateItemBean.setUstrItemValue(itemMap.getOrDefault(gmstConfigTemplateItemMst.getUnumTempleItemId(), ""));
                                if (templateDtl.getUnumTempleIsmandy() != null)
                                    templateItemBean.setUnumItemIsmandy(templateDtl.getUnumTempleIsmandy());
                                // replace constants
                                templateItemBean.setUstrItemPrintPreText(
                                        replaceConstantString(gmstConfigTemplateItemMst.getUstrItemPrintPreText(), facultyName, purpose, academicYear));
                                templateItemBean.setUstrItemPrintPostText(
                                        replaceConstantString(gmstConfigTemplateItemMst.getUstrItemPrintPostText(), facultyName, purpose, academicYear));

                                return templateItemBean;
                            })
                            .sorted(Comparator.comparing(TemplateItemBean::getUnumItemDisplayOrder, Comparator.nullsLast(Comparator.naturalOrder())))
                            .toList();

                    templateComponentBean.setItems(templateItemBeans);

                    for (TemplateItemBean templateItemBean : templateItemBeans) {
                        fetchSubItems(templateItemBean, itemsByTemplateId, templateDetailByTemplateId, itemMap, facultyName, purpose, academicYear);
                    }
                }
            }
            templateBean.setNoOfPages(noOfPages);
        }
    }

    private List<TemplateBean> getTemplateBeans(Map<Long, Long> masterTemplateDetailIds, List<Long> templateIds) throws Exception {
        int universityId = RequestUtility.getUniversityId();

        List<GmstConfigTemplateMst> templates = templateRepository.findByUnumIsvalidAndUnumUnivIdAndUnumTempleIdIn(1, universityId, templateIds);
        // Set template in master template
        return templates.stream()
                .map(template -> {
                    TemplateBean templateBean = BeanUtils.copyProperties(template, TemplateBean.class);
                    templateBean.setUnumMtempledtlId(masterTemplateDetailIds.get(templateBean.getUnumTempleId()));
                    return templateBean;
                })
                .sorted(Comparator.comparing(TemplateBean::getUnumTempleId))
                .toList();
    }

    private String replaceConstantString(String text, String facultyName, String purpose, String academicYear) {
        if (text == null)
            return null;
        return text.replaceAll("#faculty_name#", facultyName)
                .replaceAll("#application_purpose#", purpose)
                .replaceAll("#acad_year#", academicYear);
    }

    private void fetchSubItems(TemplateItemBean templateItemBean, List<GmstConfigTemplateItemMst> itemsByTemplateId,
                               List<GmstConfigTemplateDtl> templateDetailByTemplateId, Map<Long, String> itemMap,
                               String facultyName, String purpose, String academicYear) {

        List<TemplateItemBean> childItems = itemsByTemplateId.stream()
                .filter(gmstConfigTemplateItemMst -> gmstConfigTemplateItemMst.getUnumTempleParentItemId() != null &&
                        gmstConfigTemplateItemMst.getUnumTempleParentItemId().equals(templateItemBean.getUnumTempleItemId()))
                .map(gmstConfigTemplateItemMst -> {
                    TemplateItemBean itemBean = BeanUtils.copyProperties(gmstConfigTemplateItemMst, TemplateItemBean.class);
                    GmstConfigTemplateDtl gmstConfigTemplateDtl = templateDetailByTemplateId.stream().filter(
                            gmstConfigTemplateDtl1 -> gmstConfigTemplateDtl1.getUnumTempleItemId() != null && gmstConfigTemplateDtl1.getUnumTempleItemId().equals(itemBean.getUnumTempleItemId())
                    ).findFirst().orElse(null);
                    if (gmstConfigTemplateDtl != null) {
                        itemBean.setUnumItemDisplayOrder(gmstConfigTemplateDtl.getUnumDisplayOrder());
                        itemBean.setUnumIsHidden(gmstConfigTemplateDtl.getUnumHideItemTxt() == null ? 0 : gmstConfigTemplateDtl.getUnumHideItemTxt());
                        itemBean.setUnumTempledtlId(gmstConfigTemplateDtl.getUnumTempledtlId());
                        itemBean.setUnumTempleCompItemId(gmstConfigTemplateDtl.getUnumTempleCompItemId());
                        itemBean.setUstrItemValue(itemMap.getOrDefault(gmstConfigTemplateItemMst.getUnumTempleItemId(), ""));
                        if (gmstConfigTemplateDtl.getUnumTempleIsmandy() != null)
                            itemBean.setUnumItemIsmandy(gmstConfigTemplateDtl.getUnumTempleIsmandy());

                        // Replace constants
                        itemBean.setUstrItemPrintPreText(
                                replaceConstantString(gmstConfigTemplateItemMst.getUstrItemPrintPreText(), facultyName, purpose, academicYear));
                        itemBean.setUstrItemPrintPostText(
                                replaceConstantString(gmstConfigTemplateItemMst.getUstrItemPrintPostText(), facultyName, purpose, academicYear));
                    }
                    return itemBean;
                })
                .sorted(Comparator.comparing(TemplateItemBean::getUnumItemDisplayOrder, Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();

        templateItemBean.setChildren(childItems);

        if (childItems.isEmpty())
            return;

        for (TemplateItemBean childItem : childItems) {
            fetchSubItems(childItem, itemsByTemplateId, templateDetailByTemplateId, itemMap, facultyName, purpose, academicYear);
        }
    }

    @Transactional
    public ServiceResponse save(TemplateToSaveBean templateToSaveBean) throws Exception {
        Long userId = RequestUtility.getUserId();
        long applicantId = userId;
        Optional<GmstApplicantMst> applicantMstOptional = applicantRepository.findById(new GmstApplicantMstPK(applicantId, 1));
        if (applicantMstOptional.isEmpty())
            return ServiceResponse.errorResponse(language.message("You are not logged in as an Applicant. Please login with your Applicant credentials."));

        GmstApplicantMst applicant = applicantMstOptional.get();
        if (applicant.getUnumIsVerifiedApplicant() != 1)
            return ServiceResponse.errorResponse(language.message("Applicant is not verified"));

        // Get Master Template Data
        Optional<GmstConfigMastertemplateMst> mastertemplateMstOptional = masterTemplateRepository.findById(new GmstConfigMastertemplateMstPK(templateToSaveBean.getUnumMtempleId(), 1));
        if (mastertemplateMstOptional.isEmpty())
            return ServiceResponse.errorResponse(language.notFoundForId("Master Template", templateToSaveBean.getUnumMtempleId()));

        // Get Notification Detail
        NotificationBean notificationBean = restUtility.get(RestUtility.SERVICE_TYPE.PLANNING_BOARD, Constants.URL_GET_NOTIFICATION_BY_ID + templateToSaveBean.getUnumNid(), NotificationBean.class);
        if (notificationBean == null)
            return ServiceResponse.errorResponse(language.notFoundForId("Notification", templateToSaveBean.getUnumNid()));

        NotificationDetailBean notificationDetailBean = notificationBean.getNotificationDetails().stream()
                .filter(bean -> bean.getUnumNdtlId().equals(templateToSaveBean.getUnumNdtlId()))
                .findFirst()
                .orElse(null);

        if (notificationDetailBean == null)
            return ServiceResponse.errorResponse(language.notFoundForId("Notification Detail", templateToSaveBean.getUnumNdtlId()));

        // Upload all the file to main directory
        boolean isFinalSave = Objects.equals(templateToSaveBean.getUnumApplicationEntryStatus(), Constants.APPLICATION_STATUS_FINAL_SAVE);
        Set<Long> templatesToSave = new HashSet<>();
        if (isFinalSave) {
            for (TemplateToSaveDetailBean templateToSaveDetailBean : templateToSaveBean.getItemDetails()) {
                templatesToSave.add(templateToSaveDetailBean.getUnumTempleId());
                if (templateToSaveDetailBean.getUnumUiControlId() == 9) {
                    if (templateToSaveDetailBean.getUstrItemValue() != null && !templateToSaveDetailBean.getUstrItemValue().isBlank()) {
                        if (!ftpUtility.isFileExists(templateToSaveDetailBean.getUstrItemValue())) {
                            if (!ftpUtility.moveFileFromTempToFinalDirectory(templateToSaveDetailBean.getUstrItemValue()))
                                return ServiceResponse.errorResponse(language.message("Unable to upload file [" + templateToSaveDetailBean.getUstrItemValue() + "]."));
                        }
                    }
                }
            }
        }

        Integer universityId = RequestUtility.getUniversityId();
        Date currentDate = new Date();
        Long applicationId = templateToSaveBean.getUnumApplicationId();
        if (applicationId == null || applicationId == 0L) {
            // Check if Application already exists
            Optional<GbltConfigApplicationDataMst> applicationOptional = applicationDataMasterRepository.getApplication(
                    RequestUtility.getUniversityId(), RequestUtility.getUserId(), notificationBean.getUnumNid(), notificationDetailBean.getUnumNdtlId());
            if (applicationOptional.isPresent()) {
                return ServiceResponse.errorResponse(language.message("Application already saved"));
            }

            applicationId = applicationDataMasterRepository.getNextId();

            GmstConfigMastertemplateMst mastertemplateMst = mastertemplateMstOptional.get();

            GbltConfigApplicationDataMst applicationDataMst = new GbltConfigApplicationDataMst();
            applicationDataMst.setUnumApplicationId(applicationId);
            applicationDataMst.setUnumApplicantId(applicantId);
            applicationDataMst.setUdtApplicationDate(currentDate);
            applicationDataMst.setUdtApplicationEntryDate(currentDate);
            applicationDataMst.setUdtApplicationSubmitDate(currentDate);
            applicationDataMst.setUnumApplicationEntryStatus(templateToSaveBean.getUnumApplicationEntryStatus());
            applicationDataMst.setUnumMtemplateType(mastertemplateMst.getUnumMtemplateType());
            applicationDataMst.setUnumNid(notificationBean.getUnumNid());
            if (notificationBean.getUnumDeptId() != null)
                applicationDataMst.setUnumNDeptId(Long.valueOf(notificationBean.getUnumDeptId().toString()));
            applicationDataMst.setUnumNdtlId(notificationDetailBean.getUnumNdtlId());
            if (notificationDetailBean.getUnumFacultyId() != null)
                applicationDataMst.setUnumNdtlFacultyId(Long.valueOf(notificationDetailBean.getUnumFacultyId().toString()));
            if (notificationDetailBean.getUnumDepartmentId() != null)
                applicationDataMst.setUnumNdtlDepartmentId(Long.valueOf(notificationDetailBean.getUnumDepartmentId().toString()));

            applicationDataMst.setUnumUnivId(universityId);
            applicationDataMst.setUdtEffFrom(currentDate);
            applicationDataMst.setUnumIsvalid(1);
            applicationDataMst.setUnumEntryUid(userId);
            applicationDataMst.setUdtEntryDate(currentDate);
            if (notificationDetailBean.getUnumCoursetypeId() != null)
                applicationDataMst.setUnumCtypeId(notificationDetailBean.getUnumCoursetypeId());
            applicationDataMst.setUnumMtempleId(mastertemplateMst.getUnumMtempleId());

            applicationDataMasterRepository.save(applicationDataMst);
        } else {
            // Delete all entries
            applicationDataDetailRepository.delete(1, universityId, applicationId);

            // Update master table
            Optional<GbltConfigApplicationDataMst> applicationDataMstOptional = applicationDataMasterRepository.findById(new GbltConfigApplicationDataMstPK(
                    applicationId, applicantId, notificationBean.getUnumNid(), notificationDetailBean.getUnumNdtlId(), 1
            ));
            if (applicationDataMstOptional.isEmpty())
                return ServiceResponse.errorResponse(language.notFoundForId("Application", applicationId));
            GbltConfigApplicationDataMst applicationDataMst = applicationDataMstOptional.get();
            applicationDataMst.setUnumApplicationId(applicationId);
            applicationDataMst.setUnumApplicantId(applicantId);
            applicationDataMst.setUnumApplicationEntryStatus(templateToSaveBean.getUnumApplicationEntryStatus());
            applicationDataMst.setUdtLstModDate(currentDate);
            applicationDataMst.setUnumLstModUid(userId);
            applicationDataMst.setUdtApplicationDate(currentDate);
            applicationDataMst.setUdtApplicationSubmitDate(currentDate);
            applicationDataMst.setUdtEntryDate(currentDate);
            applicationDataMasterRepository.save(applicationDataMst);
        }

        Map<String, GmstConfigTemplateDtl> checklistItemsToSave = new HashMap<>();
        if (isFinalSave) {
            checklistItemsToSave = templateDetailRepository.findByUnumIsvalidAndUnumUnivIdAndUnumTempleIdIn(1, universityId, templatesToSave.stream().toList())
                    .stream()
                    .filter(gmstConfigTemplateDtl -> gmstConfigTemplateDtl.getUnumChecklistId() != null && gmstConfigTemplateDtl.getUstrChecklistName() != null &&
                            gmstConfigTemplateDtl.getUstrChecklistItemName() != null && gmstConfigTemplateDtl.getUnumChecklistItemOrderno() != null)
                    .collect(Collectors.toMap(gmstConfigTemplateDtl -> gmstConfigTemplateDtl.getUnumTempleId() + "-" + gmstConfigTemplateDtl.getUnumTempleItemId(),
                            Function.identity()));
        }

        List<GbltConfigApplicationDataDtl> itemDetailList = new ArrayList<>();
        List<GbltConfigApplicationChklstDataDtl> applicationChecklist = new ArrayList<>();
        long index = 1L;
        for (TemplateToSaveDetailBean templateToSaveDetailBean : templateToSaveBean.getItemDetails()) {
            GbltConfigApplicationDataDtl applicationDataDtl = BeanUtils.copyProperties(templateToSaveDetailBean, GbltConfigApplicationDataDtl.class);
            applicationDataDtl.setUnumApplicationdtlId(index);
            applicationDataDtl.setUnumApplicationId(applicationId);
            applicationDataDtl.setUnumApplicantId(applicantId);
            applicationDataDtl.setUnumNid(notificationBean.getUnumNid());
            applicationDataDtl.setUnumNdtlId(notificationDetailBean.getUnumNdtlId());
            applicationDataDtl.setUnumUnivId(universityId);
            applicationDataDtl.setUdtEffFrom(currentDate);
            applicationDataDtl.setUnumIsvalid(1);
            applicationDataDtl.setUnumEntryUid(userId);
            applicationDataDtl.setUdtEntryDate(currentDate);
            itemDetailList.add(applicationDataDtl);

            if (isFinalSave && checklistItemsToSave.containsKey(templateToSaveDetailBean.getUnumTempleId() + "-" + templateToSaveDetailBean.getUnumTempleItemId())) {
                GmstConfigTemplateDtl checklistItem = checklistItemsToSave.get(templateToSaveDetailBean.getUnumTempleId() + "-" + templateToSaveDetailBean.getUnumTempleItemId());
                GbltConfigApplicationChklstDataDtl applicationChklstDataDtl = BeanUtils.copyProperties(templateToSaveDetailBean, GbltConfigApplicationChklstDataDtl.class);
                applicationChklstDataDtl.setUnumApplicationId(applicationId);
                applicationChklstDataDtl.setUnumApplicationdtlId(index);
                applicationChklstDataDtl.setUnumApplicantId(applicantId);
                applicationChklstDataDtl.setUnumNid(notificationBean.getUnumNid());
                applicationChklstDataDtl.setUnumNdtlId(notificationDetailBean.getUnumNdtlId());
                applicationChklstDataDtl.setUnumChecklistId(checklistItem.getUnumChecklistId());
                applicationChklstDataDtl.setUnumIsvalid(1);
                applicationChklstDataDtl.setUdtEffFrom(currentDate);
                applicationChklstDataDtl.setUdtEntryDate(currentDate);
                applicationChklstDataDtl.setUnumApplicationStatusSno(Constants.APPLICATION_STATUS_FINAL_SAVE.longValue());
                applicationChklstDataDtl.setUnumApprovalStatusid(9);
                applicationChklstDataDtl.setUnumChecklistItemOrderno(checklistItem.getUnumChecklistItemOrderno());
                applicationChklstDataDtl.setUnumComponentOrderNo(checklistItem.getUnumComponentOrderNo());
                applicationChklstDataDtl.setUnumEntryUid(userId);
                applicationChklstDataDtl.setUnumHeaderOrderNo(checklistItem.getUnumHeaderOrderNo());
                applicationChklstDataDtl.setUnumOfcScrutinyIsitemverified(0);
                applicationChklstDataDtl.setUnumUnivId(universityId);
                applicationChklstDataDtl.setUstrChecklistName(checklistItem.getUstrChecklistName());
                applicationChklstDataDtl.setUstrChecklistItemName(checklistItem.getUstrChecklistItemName());
                applicationChklstDataDtl.setUstrTempleItemValue(templateToSaveDetailBean.getUstrItemValue());
                applicationChecklist.add(applicationChklstDataDtl);
            }

            index++;
        }
        long startTime = System.currentTimeMillis();
        applicationDataDetailRepository.saveAll(itemDetailList);
        long endTime = System.currentTimeMillis();
        log.info("Total time taken for saving: {} ms", (endTime - startTime));

        // Final save: insert record in tracker detail
        if (isFinalSave) {
            GbltConfigApplicationTracker applicationTracker = new GbltConfigApplicationTracker();
            applicationTracker.setUnumApplicationId(applicationId);
            applicationTracker.setUnumApplicantId(applicantId);
            applicationTracker.setUnumNid(notificationBean.getUnumNid());
            applicationTracker.setUnumNdtlId(notificationDetailBean.getUnumNdtlId());
            applicationTracker.setUnumNdtlFacultyId(notificationDetailBean.getUnumFacultyId());
            applicationTracker.setUnumNdtlDepartmentId(notificationDetailBean.getUnumDepartmentId());
            applicationTracker.setUnumMtempleId(notificationDetailBean.getUnumMtempleId());
            applicationTracker.setUnumApplicationStatusSno(1);
            applicationTracker.setUnumApplicationStatusId(Constants.APPLICATION_STATUS_FINAL_SAVE);
            applicationTracker.setUdtApplicationStatusDt(currentDate);
            applicationTracker.setUstrStatusBy(Long.toString(applicantId));
            applicationTracker.setUnumUnivId(universityId);
            applicationTracker.setUdtEffFrom(currentDate);
            applicationTracker.setUnumIsvalid(1);
            applicationTracker.setUnumEntryUid(RequestUtility.getUserId());
            applicationTracker.setUdtEntryDate(currentDate);
            applicationTracker.setUnumCtypeId(notificationDetailBean.getUnumCoursetypeId());
            applicationTracker.setUnumApplicationLevelId(Constants.APPLICATION_STATUS_FINAL_SAVE);
            applicationTracker.setUdtApplicationDate(currentDate);

            applicationTrackerRepository.save(applicationTracker);

            GbltConfigApplicationTrackerDtl trackerDtl = BeanUtils.copyProperties(applicationTracker, GbltConfigApplicationTrackerDtl.class);
            applicationTrackerDtlRepository.save(trackerDtl);

            // Save checklist Details
            checkListService.saveApplicationChecklist(applicationChecklist);
        }

        TemplateToSaveBean responseObject = new TemplateToSaveBean();
        responseObject.setUnumApplicationId(applicationId);
        return ServiceResponse.successObject(responseObject);
    }

    public List<MasterTemplateBean> getCombo() throws Exception {
        return BeanUtils.copyListProperties(
                masterTemplateRepository.findByUnumIsvalidAndUnumUnivId(1, RequestUtility.getUniversityId()),
                MasterTemplateBean.class
        );
    }


    @Transactional
    public ServiceResponse delete(MasterTemplateBean masterTemplateBean, Long[] idsToDelete) {
        if (idsToDelete == null || idsToDelete.length == 0) {
            return ServiceResponse.errorResponse(language.mandatory("Master Template Id"));
        }

        List<GmstConfigMastertemplateMst> templateMmsts = masterTemplateRepository.findByUnumIsvalidInAndUnumMtempleIdIn(
                List.of(1, 2), List.of(idsToDelete)
        );

        if (templateMmsts.size() != idsToDelete.length) {
            return ServiceResponse.errorResponse(language.notFoundForId("Master Template", Arrays.toString(idsToDelete)));
        }

        // Create Log
        int noOfRowsAffected = masterTemplateRepository.createLog(List.of(idsToDelete));
        if (noOfRowsAffected != idsToDelete.length) {
            throw new ApplicationException(language.deleteError("Master Template"));
        }

        templateMmsts.forEach(mTemp -> {
            mTemp.setUnumIsvalid(0);
            mTemp.setUdtEntryDate(masterTemplateBean.getUdtEntryDate());
            mTemp.setUnumUnivId(masterTemplateBean.getUnumUnivId());
        });

        masterTemplateRepository.saveAll(templateMmsts);
        return ServiceResponse.builder()
                .status(1)
                .message(language.deleteSuccess("Master Template"))
                .build();
    }

    public List<TemplateToSaveBean> scrutinyListPage(Long notificationId, Integer applicationStatus) throws Exception {

        List<GbltConfigApplicationDataMst> applicationDataList = applicationDataMasterRepository.getApplicationByNotification(
                RequestUtility.getUniversityId(), applicationStatus, notificationId
        );

        Map<Long, String> applicants = applicantRepository.findByUnumIsVerifiedApplicantAndUnumIsvalid(1, 1)
                .stream()
                .collect(Collectors.toMap(GmstApplicantMst::getUnumApplicantId, GmstApplicantMst::getUstrApplicantName));

        return applicationDataList.stream()
                .map(applicationData -> {
                    TemplateToSaveBean templateToSaveBean = new TemplateToSaveBean();
                    templateToSaveBean.setUnumApplicationId(applicationData.getUnumApplicationId());
                    templateToSaveBean.setApplicantName(applicants.getOrDefault(applicationData.getUnumApplicantId(), ""));
                    return templateToSaveBean;
                })
                .toList();
    }

    public List<ApplicationStatusBean> getApplicationStatusCombo() {
        return (BeanUtils.copyListProperties(
                applicationDataMasterRepository.getAllApplicationStatus(), ApplicationStatusBean.class)
        );
    }

    public ServiceResponse getApplicationById(Long applicationId) throws Exception {
        if (applicationId == null) {
            return ServiceResponse.errorResponse(language.mandatory("Application Id"));
        }

        Optional<GbltConfigApplicationDataMst> applicationDataMstOptional = applicationDataMasterRepository.findByUnumApplicationIdAndUnumIsvalidAndUnumUnivId(
                applicationId, 1, RequestUtility.getUniversityId()
        );

        if (applicationDataMstOptional.isEmpty())
            return ServiceResponse.errorResponse(language.notFoundForId("Application", applicationId));

        ApplicationDataBean applicationDataBean = BeanUtils.copyProperties(applicationDataMstOptional.get(), ApplicationDataBean.class);

        Optional<GmstApplicantMst> gmstApplicantMstOptional = applicantRepository.findByUnumApplicantIdAndUnumIsvalid(applicationDataBean.getUnumApplicantId(), 1);

        gmstApplicantMstOptional.ifPresent(gmstApplicantMst -> applicationDataBean.setUstrApplicantName(gmstApplicantMst.getUstrApplicantName()));

        return ServiceResponse.successObject(applicationDataBean);
    }

    @Transactional
    public ServiceResponse saveMasterTemplate(MasterTemplateBean masterTemplateBean) throws Exception {

        List<GmstConfigMastertemplateMst> configMastertemplateMsts = masterTemplateRepository.findByUnumIsvalidInAndUstrMtempleNameIgnoreCase(
                List.of(1), masterTemplateBean.getUstrMtempleName());

        if (!configMastertemplateMsts.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("Master Template", masterTemplateBean.getUstrMtempleName()));
        }
        GmstConfigMastertemplateMst gmstConfigMasterTemplateMst = new GmstConfigMastertemplateMst();
        BeanUtils.copyProperties(masterTemplateBean, gmstConfigMasterTemplateMst);

        gmstConfigMasterTemplateMst.setUnumMtempleId(masterTemplateRepository.getNextId());

        masterTemplateRepository.save(gmstConfigMasterTemplateMst);

        Set<Long> mappedTemplateSet = new HashSet<>(masterTemplateBean.getMappedTemplates());

        List<GmstConfigMastertemplateTemplatedtl> templatesToAdd = new ArrayList<>();
        int count = 1;
        for (Long templateId : mappedTemplateSet) {
            GmstConfigMastertemplateTemplatedtl configMastertemplateTemplatedtl = new GmstConfigMastertemplateTemplatedtl();
            configMastertemplateTemplatedtl.setUnumTempleId(templateId);
            configMastertemplateTemplatedtl.setUnumMtempleId(gmstConfigMasterTemplateMst.getUnumMtempleId());
            configMastertemplateTemplatedtl.setUnumMtempledtlId(Long.parseLong(
                    gmstConfigMasterTemplateMst.getUnumMtempleId() + StringUtility.padLeftZeros(count++ + "", 5)));
            configMastertemplateTemplatedtl.setUnumIsvalid(1);
            configMastertemplateTemplatedtl.setUnumEntryUid(RequestUtility.getUserId());
            configMastertemplateTemplatedtl.setUdtEffFrom(new Date());
            configMastertemplateTemplatedtl.setUnumUnivId(RequestUtility.getUniversityId());
            configMastertemplateTemplatedtl.setUdtEntryDate(new Date());
            templatesToAdd.add(configMastertemplateTemplatedtl);
        }

        if (!templatesToAdd.isEmpty())
            masterTemplateDetailRepository.saveAll(templatesToAdd);

        return ServiceResponse.builder().status(1).message(language.saveSuccess("Master Template Save")).build();

    }

    @Transactional
    public ServiceResponse updateMasterTemplate(MasterTemplateBean masterTemplateBean) throws Exception {

        GmstConfigMastertemplateMst gmstConfigMasterTemplateMst = new GmstConfigMastertemplateMst();
        BeanUtils.copyProperties(masterTemplateBean, gmstConfigMasterTemplateMst);
        masterTemplateRepository.createLog(List.of(masterTemplateBean.getUnumMtempleId()));
        masterTemplateDetailRepository.createLog(List.of(masterTemplateBean.getUnumMtempleId()));
        masterTemplateRepository.save(gmstConfigMasterTemplateMst);
        Set<Long> mappedTemplateSet = new HashSet<>(masterTemplateBean.getMappedTemplates());
        List<GmstConfigMastertemplateTemplatedtl> TemplatesToAdd = new ArrayList<>();
        int count = 1;
        for (Long templateId : mappedTemplateSet) {
            GmstConfigMastertemplateTemplatedtl configMastertemplateTemplatedtl = new GmstConfigMastertemplateTemplatedtl();
            configMastertemplateTemplatedtl.setUnumTempleId(templateId);
            configMastertemplateTemplatedtl.setUnumMtempleId(gmstConfigMasterTemplateMst.getUnumMtempleId());
            configMastertemplateTemplatedtl.setUnumMtempledtlId(Long.parseLong(
                    gmstConfigMasterTemplateMst.getUnumMtempleId() + StringUtility.padLeftZeros(count++ + "", 5)));
            configMastertemplateTemplatedtl.setUnumIsvalid(1);
            configMastertemplateTemplatedtl.setUnumEntryUid(RequestUtility.getUserId());
            configMastertemplateTemplatedtl.setUdtEffFrom(new Date());
            configMastertemplateTemplatedtl.setUnumUnivId(RequestUtility.getUniversityId());
            configMastertemplateTemplatedtl.setUdtEntryDate(new Date());
            TemplatesToAdd.add(configMastertemplateTemplatedtl);
        }
        if (!TemplatesToAdd.isEmpty())
            masterTemplateDetailRepository.saveAll(TemplatesToAdd);
        return ServiceResponse.builder().status(1).message(language.saveSuccess("Templatedtl Mapping")).build();
    }

    public List<MasterTemplateBean> getAllMasterTemplate() {
        List<GmstConfigMastertemplateMst> masterTemplateList = masterTemplateRepository.findByUnumIsvalidIn(List.of(1));
        List<MasterTemplateBean> masterTemplateBeanRes = new ArrayList<>();
        
        for (GmstConfigMastertemplateMst gmstConfigMastertemplateMst : masterTemplateList) {
        	StringBuffer templeNameList = new StringBuffer();
            MasterTemplateBean masterTemplateBean = BeanUtils.copyProperties(gmstConfigMastertemplateMst, MasterTemplateBean.class);
            masterTemplateBean.setUnumMtempleId(gmstConfigMastertemplateMst.getUnumMtempleId());
            masterTemplateBean.setUstrMtempleName(gmstConfigMastertemplateMst.getUstrMtempleName());
            List<GmstConfigMastertemplateTemplatedtl> masterTemplateDtlList = masterTemplateDetailRepository.findByUnumIsvalidInAndUnumMtempleId(List.of(1), masterTemplateBean.getUnumMtempleId());
            List<TemplateBean> templateBean = BeanUtils.copyListProperties(masterTemplateDtlList, TemplateBean.class);
            templateBean.forEach(tempBean -> {
                List<GmstConfigTemplateMst> gmstConfigTemplateMst = templateRepository.findByUnumIsvalidAndUnumTempleId(1, tempBean.getUnumTempleId());
                if (!gmstConfigTemplateMst.isEmpty()) {
                    templeNameList.append(gmstConfigTemplateMst.get(0).getUstrTempleName());
                    templeNameList.append(",");
                }
            });

            masterTemplateBean.setUstrtempleName(templeNameList.toString().substring(0,templeNameList.length()-1));
            masterTemplateBeanRes.add(masterTemplateBean);
        }
        return BeanUtils.copyListProperties(masterTemplateBeanRes, MasterTemplateBean.class);
    }

    public ServiceResponse getMasterTemplateById(Long masterId) throws Exception {
        List<GmstConfigMastertemplateTemplatedtl> masterTemplateDtlList = masterTemplateDetailRepository.findByUnumIsvalidInAndUnumMtempleId(List.of(1), masterId);
        List<TemplateBean> templateBean = BeanUtils.copyListProperties(masterTemplateDtlList, TemplateBean.class);
        List<TemplateMasterBean> templateMasterBean = templateService.getAllTemplateCombo();
        templateBean.forEach(tempBean -> {
            List<GmstConfigTemplateMst> gmstConfigTemplateMst = templateRepository.findByUnumIsvalidAndUnumTempleId(1, tempBean.getUnumTempleId());
            if (!gmstConfigTemplateMst.isEmpty())
                tempBean.setUstrTempleName(gmstConfigTemplateMst.get(0).getUstrTempleName());
        });

        List<TemplateMasterBean> templateMasterAdd = new ArrayList<>();
        for (TemplateMasterBean templateMasterBeanT : templateMasterBean) {
            for (TemplateBean templateBeanT : templateBean) {
                if (templateBeanT.getUnumTempleId().equals(templateMasterBeanT.getUnumTempleId())) {
                    templateMasterAdd.add(templateMasterBeanT);
                }
            }
        }
        templateMasterBean.removeAll(templateMasterAdd);
        MappedComboBean mappedComboBean = new MappedComboBean(ComboUtility.generateComboData(templateBean), ComboUtility.generateComboData(templateMasterBean));
        return ServiceResponse.successObject(mappedComboBean);
    }
}
