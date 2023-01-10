package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.*;
import in.cdac.university.globalService.entity.GmstConfigTemplateComponentDtl;
import in.cdac.university.globalService.entity.GmstConfigTemplateHeaderMst;
import in.cdac.university.globalService.entity.GmstConfigTemplateItemMst;
import in.cdac.university.globalService.exception.ApplicationException;
import in.cdac.university.globalService.repository.TemplateComponentDetailRepository;
import in.cdac.university.globalService.repository.TemplateHeaderRepository;
import in.cdac.university.globalService.repository.TemplateItemRepository;
import in.cdac.university.globalService.repository.TemplateSubHeaderRepository;
import in.cdac.university.globalService.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TemplateHeaderService {
    @Autowired
    private TemplateComponentDetailRepository templateComponentDetailRepository;

    @Autowired
    private Language language;

    @Autowired
    private TemplateHeaderRepository templateHeaderRepository;

    @Autowired
    private TemplateSubHeaderRepository templateSubHeaderRepository;

    @Autowired
    private TemplateItemRepository templateItemRepository;

    public List<TemplateHeaderBean> listPageData() throws Exception {
        return BeanUtils.copyListProperties(templateHeaderRepository
                        .findByUnumIsvalidAndUnumUnivIdOrderByUstrTempleHeadCodeAsc(1, RequestUtility.getUniversityId()),
                TemplateHeaderBean.class);
    }

    public ServiceResponse getTemplateHeaderById(Long headerId) throws Exception {
        Optional<GmstConfigTemplateHeaderMst> gmstConfigTemplateHeaderMstOptional = templateHeaderRepository
                .findByUnumTempleHeadIdAndUnumIsvalidAndUnumUnivId(headerId, 1, RequestUtility.getUniversityId());

        if (gmstConfigTemplateHeaderMstOptional.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Template Header", headerId));
        }

        TemplateHeaderBean templateHeaderBean = BeanUtils.copyProperties(gmstConfigTemplateHeaderMstOptional.get(),
                TemplateHeaderBean.class);

        return ServiceResponse.builder().status(1).responseObject(templateHeaderBean).build();
    }

    @Transactional
    public ServiceResponse save(TemplateHeaderBean templateHeaderBean) {
        // Duplicate Check
        List<GmstConfigTemplateHeaderMst> gmstConfigTemplateHeaderMstList = templateHeaderRepository
                .findByUstrHeadPrintTextIgnoreCaseAndUnumIsvalidAndUnumUnivId(templateHeaderBean.getUstrHeadPrintText(),
                        1, templateHeaderBean.getUnumUnivId());

        if (!gmstConfigTemplateHeaderMstList.isEmpty()) {
            return ServiceResponse
                    .errorResponse(language.duplicate("Header", templateHeaderBean.getUstrHeadPrintText()));
        }

        // Generate new header id
        GmstConfigTemplateHeaderMst gmstConfigTemplateHeaderMst = BeanUtils.copyProperties(templateHeaderBean,
                GmstConfigTemplateHeaderMst.class);
        gmstConfigTemplateHeaderMst.setUnumTempleHeadId(templateHeaderRepository.getNextId());
        templateHeaderRepository.save(gmstConfigTemplateHeaderMst);

        return ServiceResponse.builder().status(1).message(language.saveSuccess("Template Header")).build();
    }

    @Transactional
    public ServiceResponse update(TemplateHeaderBean templateHeaderBean) {
        if (templateHeaderBean.getUnumTempleHeadId() == null) {
            return ServiceResponse.errorResponse(language.mandatory("Header Id"));
        }

        // Duplicate Check
        List<GmstConfigTemplateHeaderMst> gmstConfigTemplateHeaderMstList = templateHeaderRepository
                .findByUnumTempleHeadIdNotAndUstrHeadPrintTextIgnoreCaseAndUnumIsvalidAndUnumUnivId(
                        templateHeaderBean.getUnumTempleHeadId(), templateHeaderBean.getUstrHeadPrintText(), 1,
                        templateHeaderBean.getUnumUnivId());

        if (!gmstConfigTemplateHeaderMstList.isEmpty()) {
            return ServiceResponse
                    .errorResponse(language.duplicate("Header", templateHeaderBean.getUstrHeadPrintText()));
        }

        // Create Log
        int noOfRowsAffected = templateHeaderRepository.createLog(List.of(templateHeaderBean.getUnumTempleHeadId()));
        if (noOfRowsAffected == 0) {
            throw new ApplicationException(language.notFoundForId("Header", templateHeaderBean.getUnumTempleHeadId()));
        }

        // Save New Data
        GmstConfigTemplateHeaderMst gmstConfigTemplateHeaderMst = BeanUtils.copyProperties(templateHeaderBean,
                GmstConfigTemplateHeaderMst.class);
        templateHeaderRepository.save(gmstConfigTemplateHeaderMst);

        return ServiceResponse.builder().status(1).message(language.updateSuccess("Header")).build();
    }

    @Transactional
    public ServiceResponse delete(TemplateHeaderBean templateHeaderBean, Long[] idsToDelete) {
        if (idsToDelete == null || idsToDelete.length == 0) {
            return ServiceResponse.errorResponse(language.mandatory("Header Id"));
        }

        List<GmstConfigTemplateHeaderMst> gmstConfigTemplateHeaderMstList = templateHeaderRepository
                .findByUnumTempleHeadIdInAndUnumIsvalidAndUnumUnivId(List.of(idsToDelete), 1,
                        templateHeaderBean.getUnumUnivId());

        if (gmstConfigTemplateHeaderMstList.size() != idsToDelete.length) {
            return ServiceResponse.errorResponse(language.notFoundForId("Header", Arrays.toString(idsToDelete)));
        }

        // Create Log
        int noOfRowsAffected = templateHeaderRepository.createLog(List.of(idsToDelete));
        if (noOfRowsAffected != idsToDelete.length) {
            throw new ApplicationException(language.deleteError("Header"));
        }

        gmstConfigTemplateHeaderMstList.forEach(header -> {
            header.setUnumIsvalid(0);
            header.setUdtEntryDate(templateHeaderBean.getUdtEntryDate());
            header.setUnumEntryUid(header.getUnumEntryUid());
        });

        templateHeaderRepository.saveAll(gmstConfigTemplateHeaderMstList);

        return ServiceResponse.builder().status(1).message(language.deleteSuccess("Header")).build();
    }

    public ServiceResponse getTemplateHeaderCombo() throws Exception {
        List<GmstConfigTemplateHeaderMst> allHeaders = templateHeaderRepository
                .findAllHeaders(RequestUtility.getUniversityId());
        return ServiceResponse.successObject(
                ComboUtility.generateComboData(BeanUtils.copyListProperties(allHeaders, TemplateHeaderBean.class)));
    }

    public List<TemplateSubHeaderBean> getSubHeaderComboID(Long unumTempleHeadId) throws Exception {
        return BeanUtils.copyListProperties(
                templateSubHeaderRepository.findByunumTempleHeadId(unumTempleHeadId, RequestUtility.getUniversityId()),
                TemplateSubHeaderBean.class);

    }

    public ServiceResponse getItemsByHeaderSubHeaderComponents(@Valid TemplateHeaderSubHeaderBean templateHeaderSubHeaderBean) {
        List<Long> componentList = templateHeaderSubHeaderBean.getCompItemBean().stream().map(TemplateComponentItemBean::getUnumTempleCompId).toList();

        List<GmstConfigTemplateComponentDtl> components = templateComponentDetailRepository.findByUnumTempleCompIdInAndUnumIsvalidIn(componentList, List.of(1));

        templateHeaderSubHeaderBean.getCompItemBean().forEach(requestbean -> {
            List<GmstConfigTemplateItemMst> itemsList = templateItemRepository.findAllByUnumTempleItemIdAndUnumIsvalid(requestbean.getUnumTempleCompId(), 1);
            List<TemplateItemBean> items = itemsList.stream()
                    .map(item -> {
                        Long componentItemId = components.stream().filter(component -> item.getUnumTempleItemId().equals(component.getUnumTempleItemId()))
                                .map(GmstConfigTemplateComponentDtl::getUnumTempleCompItemId)
                                .findFirst()
                                .orElse(0L);

                        TemplateItemBean templateItemBean = BeanUtils.copyProperties(item, TemplateItemBean.class);
                        templateItemBean.setUnumTempleCompItemId(componentItemId);
                        return templateItemBean;
                    })
                    .toList();

            requestbean.setItems(items);
        });

        return ServiceResponse.successObject(templateHeaderSubHeaderBean);
    }
}
