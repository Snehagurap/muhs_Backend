package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.*;
import in.cdac.university.globalService.entity.*;
import in.cdac.university.globalService.repository.*;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.Language;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private Language language;

    public ServiceResponse getTemplate(Long masterTemplateId) throws Exception {
        Optional<GmstConfigMastertemplateMst> templateByIdOptional = masterTemplateRepository.findById(new GmstConfigMastertemplateMstPK(masterTemplateId, 1));
        if (templateByIdOptional.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Master Template", masterTemplateId));
        }

        MasterTemplateBean masterTemplateBean = BeanUtils.copyProperties(templateByIdOptional.get(), MasterTemplateBean.class);

        Integer universityId = RequestUtility.getUniversityId();

        List<GmstConfigMastertemplateTemplatedtl> masterTemplateDetails = masterTemplateDetailRepository.findByUnumIsvalidAndUnumUnivIdAndUnumMtempleId(1, universityId, masterTemplateBean.getUnumMtempleId());

        // Template ids for the given Master template
        List<Long> templateIds = masterTemplateDetails.stream()
                .map(GmstConfigMastertemplateTemplatedtl::getUnumTempleId)
                .toList();

        // Get the template on the basis of master template
        List<GmstConfigTemplateMst> templates = templateRepository.findByUnumIsvalidAndUnumUnivIdAndUnumTempleIdIn(1, universityId, templateIds);

        // Get Template details
        List<GmstConfigTemplateDtl> templateDetailList = templateDetailRepository.findByUnumIsvalidAndUnumUnivIdAndUnumTempleIdIn(1, universityId, templateIds);
        Map<Long, List<GmstConfigTemplateDtl>> mapTemplateDetails = templateDetailList
                            .stream()
                            .collect(Collectors.groupingBy(GmstConfigTemplateDtl::getUnumTempleId));

        // Set template in master template
        List<TemplateBean> templateBeans = BeanUtils.copyListProperties(templates, TemplateBean.class);
        masterTemplateBean.setTemplateList(templateBeans);

        // Get Headers by Template id
        List<GmstConfigTemplateHeaderMst> headersByTemplateId = templateHeaderRepository.findHeadersByTemplateId(universityId, templateIds);

        // Get component by template id
        List<GmstConfigTemplateComponentMst> componentsByTemplateId = templateComponentRepository.findComponentsByTemplateId(universityId, templateIds);

        // Get items by template id
        List<GmstConfigTemplateItemMst> itemsByTemplateId = templateItemRepository.findItemsByTemplateId(universityId, templateIds);

        for (TemplateBean templateBean: templateBeans) {
            Long templateId = templateBean.getUnumTempleId();
            List<GmstConfigTemplateDtl> templateDetailByTemplateId = mapTemplateDetails.get(templateId);

            // Get all the headers for the given template
            Map<Long, GmstConfigTemplateDtl> headerIds = templateDetailByTemplateId.stream()
                    .collect(Collectors.toMap(GmstConfigTemplateDtl::getUnumTempleHeadId, Function.identity(), (u1, u2) -> u1));

            List<TemplateHeaderBean> headerBeans = headersByTemplateId.stream()
                    .filter(gmstConfigTemplateHeaderMst ->  headerIds.containsKey(gmstConfigTemplateHeaderMst.getUnumTemplHeadId()))
                    .map(gmstConfigTemplateHeaderMst -> {
                        TemplateHeaderBean templateHeaderBean = BeanUtils.copyProperties(gmstConfigTemplateHeaderMst, TemplateHeaderBean.class);
                        GmstConfigTemplateDtl configTemplateDtl = headerIds.get(gmstConfigTemplateHeaderMst.getUnumTemplHeadId());
                        templateHeaderBean.setUnumHeadDisplayOrder(configTemplateDtl.getUnumDisplayOrder());
                        templateHeaderBean.setUnumIsHidden(configTemplateDtl.getUnumHideHeaderTxt() == null ? 0 : configTemplateDtl.getUnumHideHeaderTxt());
                        return templateHeaderBean;
                    })
                    .sorted(Comparator.comparing(TemplateHeaderBean::getUnumHeadDisplayOrder, Comparator.nullsLast(Comparator.naturalOrder())))
                    .toList();

            templateBean.setHeaders(headerBeans);

            // Get Component Detail for each header
            for (TemplateHeaderBean templateHeaderBean: headerBeans) {
                Long headerId = templateHeaderBean.getUnumTemplHeadId();

                Set<Long> componentsInTemplate = templateDetailByTemplateId.stream()
                        .filter(gmstConfigTemplateDtl -> gmstConfigTemplateDtl.getUnumTempleHeadId().equals(headerId))
                        .map(GmstConfigTemplateDtl::getUnumTempleCompId)
                        .collect(Collectors.toSet());

                List<TemplateComponentBean> templateComponentBeans = componentsByTemplateId.stream()
                        .filter(gmstConfigTemplateComponentMst -> gmstConfigTemplateComponentMst.getUnumTemplCompId() != null &&
                                componentsInTemplate.contains(gmstConfigTemplateComponentMst.getUnumTemplCompId()))
                        .map(gmstConfigTemplateComponentMst -> BeanUtils.copyProperties(gmstConfigTemplateComponentMst, TemplateComponentBean.class))
                        .sorted(Comparator.comparing(TemplateComponentBean::getUnumCompDisplayOrder, Comparator.nullsLast(Comparator.naturalOrder())))
                        .toList();

                templateHeaderBean.setComponents(templateComponentBeans);

                // Get Item for each Component
                for (TemplateComponentBean templateComponentBean: templateComponentBeans) {
                    Long componentId = templateComponentBean.getUnumTemplCompId();

                    Set<Long> itemsInTemplate = templateDetailByTemplateId.stream()
                            .filter(gmstConfigTemplateDtl -> gmstConfigTemplateDtl.getUnumTempleCompId() != null
                                    && gmstConfigTemplateDtl.getUnumTempleCompId().equals(componentId))
                            .map(GmstConfigTemplateDtl::getUnumTempleItemId)
                            .collect(Collectors.toSet());

                    List<TemplateItemBean> templateItemBeans = itemsByTemplateId.stream()
                            .filter(gmstConfigTemplateItemMst -> gmstConfigTemplateItemMst.getUnumTemplItemId() != null &&
                                    itemsInTemplate.contains(gmstConfigTemplateItemMst.getUnumTemplItemId()))
                            .filter(gmstConfigTemplateItemMst -> gmstConfigTemplateItemMst.getUnumTemplParentItemId() == null ||
                                    gmstConfigTemplateItemMst.getUnumTemplParentItemId().equals(0L))
                            .map(gmstConfigTemplateItemMst -> BeanUtils.copyProperties(gmstConfigTemplateItemMst, TemplateItemBean.class))
                            .sorted(Comparator.comparing(TemplateItemBean::getUnumItemDisplayOrder, Comparator.nullsLast(Comparator.naturalOrder())))
                            .toList();

                    templateComponentBean.setItems(templateItemBeans);

                    for (TemplateItemBean templateItemBean: templateItemBeans) {
                        fetchSubItems(templateItemBean, itemsByTemplateId);
                    }
                }
            }
        }

        return ServiceResponse.successObject(masterTemplateBean);
    }

    private void fetchSubItems(TemplateItemBean templateItemBean, List<GmstConfigTemplateItemMst> itemsByTemplateId) {
        List<TemplateItemBean> childItems = itemsByTemplateId.stream()
                .filter(gmstConfigTemplateItemMst -> gmstConfigTemplateItemMst.getUnumTemplParentItemId() != null &&
                        gmstConfigTemplateItemMst.getUnumTemplParentItemId().equals(templateItemBean.getUnumTemplItemId()))
                .map(gmstConfigTemplateItemMst -> BeanUtils.copyProperties(gmstConfigTemplateItemMst, TemplateItemBean.class))
                .sorted(Comparator.comparing(TemplateItemBean::getUnumItemDisplayOrder, Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();

        templateItemBean.setChildren(childItems);

        if (childItems.isEmpty())
            return;

        for (TemplateItemBean childItem: childItems) {
            fetchSubItems(childItem, itemsByTemplateId);
        }
    }
}
