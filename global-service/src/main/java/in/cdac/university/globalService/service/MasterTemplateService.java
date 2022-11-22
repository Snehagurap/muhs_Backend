package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.*;
import in.cdac.university.globalService.entity.*;
import in.cdac.university.globalService.repository.*;
import in.cdac.university.globalService.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    private Language language;

    @Autowired
    private RestUtility restUtility;

    @Autowired
    private ConfigApplicantDataMasterRepository applicantDataMasterRepository;

    @Autowired
    private ConfigApplicationDataDetailRepository applicationDataDetailRepository;

    @Autowired
    private FtpUtility ftpUtility;

    public ServiceResponse getTemplate(Long masterTemplateId) throws Exception {
        Optional<GmstConfigMastertemplateMst> templateByIdOptional = masterTemplateRepository.findById(new GmstConfigMastertemplateMstPK(masterTemplateId, 1));
        if (templateByIdOptional.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Master Template", masterTemplateId));
        }

        MasterTemplateBean masterTemplateBean = BeanUtils.copyProperties(templateByIdOptional.get(), MasterTemplateBean.class);

        Integer universityId = RequestUtility.getUniversityId();

        List<GmstConfigMastertemplateTemplatedtl> masterTemplateDetails = masterTemplateDetailRepository.findByUnumIsvalidAndUnumUnivIdAndUnumMtempleId(1, universityId, masterTemplateBean.getUnumMtempleId());

        // Template ids for the given Master template
        Map<Long, Long> masterTemplateDetailIds = masterTemplateDetails.stream()
                .collect(Collectors.toMap(GmstConfigMastertemplateTemplatedtl::getUnumTempleId, GmstConfigMastertemplateTemplatedtl::getUnumMtempledtlId));

        List<Long> templateIds = new ArrayList<>(masterTemplateDetailIds.keySet());

        // Get the template on the basis of master template
        List<GmstConfigTemplateMst> templates = templateRepository.findByUnumIsvalidAndUnumUnivIdAndUnumTempleIdIn(1, universityId, templateIds);
        // Set template in master template
        List<TemplateBean> templateBeans = templates.stream()
                .map(template -> {
                    TemplateBean templateBean = BeanUtils.copyProperties(template, TemplateBean.class);
                    templateBean.setUnumMtempledtlId(masterTemplateDetailIds.get(templateBean.getUnumTempleId()));
                    return templateBean;
                }).toList();
        masterTemplateBean.setTemplateList(templateBeans);

        // Get Template details
        List<GmstConfigTemplateDtl> templateDetailList = templateDetailRepository.findByUnumIsvalidAndUnumUnivIdAndUnumTempleIdIn(1, universityId, templateIds);
        Map<Long, List<GmstConfigTemplateDtl>> mapTemplateDetails = templateDetailList
                            .stream()
                            .collect(Collectors.groupingBy(GmstConfigTemplateDtl::getUnumTempleId));

        // Get Headers by Template id
        List<GmstConfigTemplateHeaderMst> headersByTemplateId = templateHeaderRepository.findHeadersByTemplateId(universityId, templateIds);

        // Get component by template id
        List<GmstConfigTemplateComponentMst> componentsByTemplateId = templateComponentRepository.findComponentsByTemplateId(universityId, templateIds);

        // Get items by template id
        List<GmstConfigTemplateItemMst> itemsByTemplateId = templateItemRepository.findItemsByTemplateId(universityId, templateIds);

        for (TemplateBean templateBean: templateBeans) {
            Long templateId = templateBean.getUnumTempleId();
            List<GmstConfigTemplateDtl> templateDetailByTemplateId = mapTemplateDetails.get(templateId);

            Map<Long, TemplateHeaderBean> headerIdsAdded = new HashMap<>();
            Map<Long, Integer> mapNoOfPageColumns = new HashMap<>();
            List<TemplateHeaderBean> headerBeans = new ArrayList<>();
            for (GmstConfigTemplateDtl configTemplateDtl: templateDetailByTemplateId) {
                if (configTemplateDtl.getUnumTempleHeadId() == 27 || !headerIdsAdded.containsKey(configTemplateDtl.getUnumTempleHeadId())) {
                    GmstConfigTemplateHeaderMst gmstConfigTemplateHeaderMst = headersByTemplateId.stream()
                            .filter(header -> header.getUnumTemplHeadId().equals(configTemplateDtl.getUnumTempleHeadId()))
                            .findFirst()
                            .orElse(null);
                    if (gmstConfigTemplateHeaderMst == null)
                        continue;

                    TemplateHeaderBean templateHeaderBean = BeanUtils.copyProperties(gmstConfigTemplateHeaderMst, TemplateHeaderBean.class);
                    if (configTemplateDtl.getUnumTempleHeadId() == 27) {
                        templateHeaderBean.setUstrHeadPrintText(configTemplateDtl.getUstrTempledtlDescription());
                    }
                    templateHeaderBean.setUnumHeadDisplayOrder(configTemplateDtl.getUnumDisplayOrder());
                    templateHeaderBean.setUnumIsHidden(configTemplateDtl.getUnumHideHeaderTxt() == null ? 0 : configTemplateDtl.getUnumHideHeaderTxt());
                    templateHeaderBean.setUnumTempledtlId(configTemplateDtl.getUnumTempledtlId());

                    if (configTemplateDtl.getUnumPageColumns() != null) {
                        int unumPageColumns = configTemplateDtl.getUnumPageColumns();
                        mapNoOfPageColumns.put(configTemplateDtl.getUnumTempleHeadId(), unumPageColumns);
                    }
                    templateHeaderBean.setUnumPageColumns(mapNoOfPageColumns.getOrDefault(configTemplateDtl.getUnumTempleHeadId(), 2));
                    templateHeaderBean.setUnumHeadIsmandy(gmstConfigTemplateHeaderMst.getUnumHeadIsmandy() == null ? 0 : gmstConfigTemplateHeaderMst.getUnumHeadIsmandy());
                    templateHeaderBean.setUnumIsMergeWithParent(gmstConfigTemplateHeaderMst.getUnumIsMergeWithParent() == null ? 0 : gmstConfigTemplateHeaderMst.getUnumIsMergeWithParent());
                    headerBeans.add(templateHeaderBean);
                    headerIdsAdded.put(configTemplateDtl.getUnumTempleHeadId(), templateHeaderBean);
                } else {
                    TemplateHeaderBean templateHeaderBean = headerIdsAdded.get(configTemplateDtl.getUnumTempleHeadId());
                    if (configTemplateDtl.getUnumPageColumns() != null) {
                        templateHeaderBean.setUnumPageColumns(configTemplateDtl.getUnumPageColumns());
                    }
                }
            }
            headerBeans.sort(Comparator.comparing(TemplateHeaderBean::getUnumHeadDisplayOrder, Comparator.nullsLast(Comparator.naturalOrder())));

            templateBean.setHeaders(headerBeans);

            // Get Component Detail for each header
            int noOfPages = 0;
            for (TemplateHeaderBean templateHeaderBean: headerBeans) {
                Long headerId = templateHeaderBean.getUnumTemplHeadId();
                if (headerId == 27)
                    noOfPages++;
                Map<Long, GmstConfigTemplateDtl> componentsInTemplate = templateDetailByTemplateId.stream()
                        .filter(gmstConfigTemplateDtl -> gmstConfigTemplateDtl.getUnumTempleHeadId().equals(headerId))
                        .collect(Collectors.toMap(GmstConfigTemplateDtl::getUnumTempleCompId, Function.identity(), (u1, u2) -> u1));

                List<TemplateComponentBean> templateComponentBeans = componentsByTemplateId.stream()
                        .filter(gmstConfigTemplateComponentMst -> gmstConfigTemplateComponentMst.getUnumTemplCompId() != null &&
                                componentsInTemplate.containsKey(gmstConfigTemplateComponentMst.getUnumTemplCompId()))
                        .map(gmstConfigTemplateComponentMst -> {
                            TemplateComponentBean templateComponentBean = BeanUtils.copyProperties(gmstConfigTemplateComponentMst, TemplateComponentBean.class);
                            GmstConfigTemplateDtl templateDtl = componentsInTemplate.get(gmstConfigTemplateComponentMst.getUnumTemplCompId());
                            templateComponentBean.setUnumCompDisplayOrder(templateDtl.getUnumDisplayOrder());
                            templateComponentBean.setUnumIsHidden(templateDtl.getUnumHideComponentTxt() == null ? 0 : templateDtl.getUnumHideComponentTxt());
                            templateComponentBean.setUnumTempledtlId(templateDtl.getUnumTempledtlId());
                            return templateComponentBean;
                        })
                        .sorted(Comparator.comparing(TemplateComponentBean::getUnumCompDisplayOrder, Comparator.nullsLast(Comparator.naturalOrder())))
                        .toList();

                templateHeaderBean.setComponents(templateComponentBeans);

                // Get Item for each Component
                for (TemplateComponentBean templateComponentBean: templateComponentBeans) {
                    Long componentId = templateComponentBean.getUnumTemplCompId();
                    Map<Long, GmstConfigTemplateDtl> itemsInTemplate = templateDetailByTemplateId.stream()
                            .filter(gmstConfigTemplateDtl -> gmstConfigTemplateDtl.getUnumTempleHeadId() != null && gmstConfigTemplateDtl.getUnumTempleHeadId().equals(headerId))
                            .filter(gmstConfigTemplateDtl -> gmstConfigTemplateDtl.getUnumTempleCompId() != null
                                    && gmstConfigTemplateDtl.getUnumTempleCompId().equals(componentId))
                            .collect(Collectors.toMap(GmstConfigTemplateDtl::getUnumTempleItemId, Function.identity(), (u1, u2) -> u1));

                    List<TemplateItemBean> templateItemBeans = itemsByTemplateId.stream()
                            .filter(gmstConfigTemplateItemMst -> gmstConfigTemplateItemMst.getUnumTemplItemId() != null &&
                                    itemsInTemplate.containsKey(gmstConfigTemplateItemMst.getUnumTemplItemId()))
                            .filter(gmstConfigTemplateItemMst -> gmstConfigTemplateItemMst.getUnumTemplParentItemId() == null ||
                                    gmstConfigTemplateItemMst.getUnumTemplParentItemId().equals(0L))
                            .map(gmstConfigTemplateItemMst -> {
                                TemplateItemBean templateItemBean = BeanUtils.copyProperties(gmstConfigTemplateItemMst, TemplateItemBean.class);
                                GmstConfigTemplateDtl templateDtl = itemsInTemplate.get(gmstConfigTemplateItemMst.getUnumTemplItemId());
                                templateItemBean.setUnumItemDisplayOrder(templateDtl.getUnumDisplayOrder());
                                templateItemBean.setUnumIsHidden(templateDtl.getUnumHideItemTxt() == null ? 0 : templateDtl.getUnumHideItemTxt());
                                templateItemBean.setUnumTempledtlId(templateDtl.getUnumTempledtlId());
                                templateItemBean.setUnumTemplCompItemId(templateDtl.getUnumTemplCompItemId());
                                return templateItemBean;
                            })
                            .sorted(Comparator.comparing(TemplateItemBean::getUnumItemDisplayOrder, Comparator.nullsLast(Comparator.naturalOrder())))
                            .toList();

                    templateComponentBean.setItems(templateItemBeans);

                    for (TemplateItemBean templateItemBean: templateItemBeans) {
                        fetchSubItems(templateItemBean, itemsByTemplateId, templateDetailByTemplateId);
                    }
                }
            }
            templateBean.setNoOfPages(noOfPages);
        }

        return ServiceResponse.successObject(masterTemplateBean);
    }

    private void fetchSubItems(TemplateItemBean templateItemBean, List<GmstConfigTemplateItemMst> itemsByTemplateId, List<GmstConfigTemplateDtl> templateDetailByTemplateId) {

        List<TemplateItemBean> childItems = itemsByTemplateId.stream()
                .filter(gmstConfigTemplateItemMst -> gmstConfigTemplateItemMst.getUnumTemplParentItemId() != null &&
                        gmstConfigTemplateItemMst.getUnumTemplParentItemId().equals(templateItemBean.getUnumTemplItemId()))
                .map(gmstConfigTemplateItemMst -> {
                    TemplateItemBean itemBean = BeanUtils.copyProperties(gmstConfigTemplateItemMst, TemplateItemBean.class);
                    GmstConfigTemplateDtl gmstConfigTemplateDtl = templateDetailByTemplateId.stream().filter(
                            gmstConfigTemplateDtl1 -> gmstConfigTemplateDtl1.getUnumTempleItemId() != null && gmstConfigTemplateDtl1.getUnumTempleItemId().equals(itemBean.getUnumTemplItemId())
                    ).findFirst().orElse(null);
                    if (gmstConfigTemplateDtl != null) {
                        itemBean.setUnumItemDisplayOrder(gmstConfigTemplateDtl.getUnumDisplayOrder());
                        itemBean.setUnumIsHidden(gmstConfigTemplateDtl.getUnumHideItemTxt() == null ? 0 : gmstConfigTemplateDtl.getUnumHideItemTxt());
                        itemBean.setUnumTempledtlId(gmstConfigTemplateDtl.getUnumTempledtlId());
                        itemBean.setUnumTemplCompItemId(gmstConfigTemplateDtl.getUnumTemplCompItemId());
                    }
                    return itemBean;
                })
                .sorted(Comparator.comparing(TemplateItemBean::getUnumItemDisplayOrder, Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();

        templateItemBean.setChildren(childItems);

        if (childItems.isEmpty())
            return;

        for (TemplateItemBean childItem: childItems) {
            fetchSubItems(childItem, itemsByTemplateId, templateDetailByTemplateId);
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
        for (TemplateToSaveDetailBean templateToSaveDetailBean: templateToSaveBean.getItemDetails()) {
            if (templateToSaveDetailBean.getUnumUiControlId() == 9) {
                if (templateToSaveDetailBean.getUstrItemValue() != null && !templateToSaveDetailBean.getUstrItemValue().isBlank()) {
                    if (!ftpUtility.moveFileFromTempToFinalDirectory(templateToSaveDetailBean.getUstrTempleItemValue()))
                        return ServiceResponse.errorResponse(language.message("Unable to upload file [" + templateToSaveDetailBean.getUstrTempleItemValue() + "]."));
                }
            }
        }

        Integer universityId = RequestUtility.getUniversityId();
        Date currentDate = new Date();
        Long applicationId = templateToSaveBean.getUnumApplicationId();
        if (applicationId == null || applicationId == 0L) {
            applicationId = applicantDataMasterRepository.getNextId();

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

            applicantDataMasterRepository.save(applicationDataMst);
        } else {
            // Delete all entries
            applicationDataDetailRepository.delete(1, universityId, applicationId);
        }

        List<GbltConfigApplicationDataDtl> itemDetailList = new ArrayList<>();
        long index = 1L;
        for (TemplateToSaveDetailBean templateToSaveDetailBean: templateToSaveBean.getItemDetails()) {
            GbltConfigApplicationDataDtl applicationDataDtl = BeanUtils.copyProperties(templateToSaveDetailBean, GbltConfigApplicationDataDtl.class);
            applicationDataDtl.setUnumApplicationdtlId(index++);
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
        }
        applicationDataDetailRepository.saveAll(itemDetailList);

        TemplateToSaveBean responseObject = new TemplateToSaveBean();
        responseObject.setUnumApplicationId(applicationId);
        return ServiceResponse.successObject(responseObject);
    }
}
