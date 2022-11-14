package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.TemplateItemBean;
import in.cdac.university.globalService.entity.GmstConfigTemplateItemMst;
import in.cdac.university.globalService.entity.GmstConfigTemplateItemMstPK;
import in.cdac.university.globalService.exception.ApplicationException;
import in.cdac.university.globalService.repository.TemplateItemRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.Language;
import in.cdac.university.globalService.util.ServiceResponse;
import in.cdac.university.globalService.util.StringUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TemplateItemService {

    @Autowired
    private TemplateItemRepository templateItemRepository;

    @Autowired
    private Language language;

    @Transactional
    public ServiceResponse save(TemplateItemBean templateItemBean) {
        // Check for text
        String temp = StringUtility.blankIfNull(templateItemBean.getUstrItemPrintPrefixText())
                + StringUtility.blankIfNull(templateItemBean.getUstrItemPrintPreText())
                + StringUtility.blankIfNull(templateItemBean.getUstrItemPrintPostText());

        if (temp.isBlank()) {
            return ServiceResponse.errorResponse("One of Prefix, Pre and Post Text is mandatory.");
        }

        List<GmstConfigTemplateItemMst> duplicate = templateItemRepository.duplicate(templateItemBean.getUnumUnivId(), temp);
        if (!duplicate.isEmpty())
            return ServiceResponse.errorResponse("Item with same Prefix + Pre + Post Text already exists");

        GmstConfigTemplateItemMst gmstConfigTemplateItemMst = BeanUtils.copyProperties(templateItemBean, GmstConfigTemplateItemMst.class);
        gmstConfigTemplateItemMst.setUnumTemplItemId(templateItemRepository.getNextId());
        templateItemRepository.save(gmstConfigTemplateItemMst);

        return ServiceResponse.successMessage(
                language.saveSuccess("Template Item")
        );
    }

    public List<TemplateItemBean> getAllActiveItems(Integer universityId) {
        return BeanUtils.copyListProperties(
                templateItemRepository.getAllActiveItems(universityId),
                TemplateItemBean.class
        );
    }

    public List<TemplateItemBean> listPageData(Integer universityId) {
        return BeanUtils.copyListProperties(
                templateItemRepository.getAllItems(universityId),
                TemplateItemBean.class
        );
    }

    public ServiceResponse getItemById(Long itemId) {
        if (itemId == null)
            return ServiceResponse.errorResponse(language.mandatory("Item Id"));

        Optional<GmstConfigTemplateItemMst> itemById = templateItemRepository.findById(new GmstConfigTemplateItemMstPK(itemId, 1));
        if (itemById.isEmpty())
            return ServiceResponse.errorResponse(language.notFoundForId("Item", itemId));

        return ServiceResponse.successObject(BeanUtils.copyProperties(itemById.get(), TemplateItemBean.class));
    }

    @Transactional
    public ServiceResponse update(TemplateItemBean templateItemBean) {
        if (templateItemBean.getUnumTemplItemId() == null)
            return ServiceResponse.errorResponse(language.mandatory("Template Item Id"));

        // Create Log
        int noOfRecords = templateItemRepository.createLog(List.of(templateItemBean.getUnumTemplItemId()));
        if(noOfRecords == 0) {
            throw new ApplicationException(language.notFoundForId("TemplateItem", templateItemBean.getUnumTemplItemId()));
        }

        // Save new Record
        GmstConfigTemplateItemMst gmstConfigTemplateItemMst = BeanUtils.copyProperties(templateItemBean, GmstConfigTemplateItemMst.class);
        templateItemRepository.save(gmstConfigTemplateItemMst);

        return ServiceResponse.builder()
                .status(1)
                .message(language.updateSuccess("Template Item"))
                .build();
    }

    public ServiceResponse delete(TemplateItemBean templateItemBean, Long[] idsToDelete) {
        if (idsToDelete == null || idsToDelete.length == 0) {
            return ServiceResponse.errorResponse(language.mandatory("Template Item Id"));
        }

        List<GmstConfigTemplateItemMst> templateItemMstList = templateItemRepository.findByUnumTemplItemIdInAndUnumIsvalidInAndUnumUnivId(
                List.of(idsToDelete), List.of(1,2) , templateItemBean.getUnumUnivId()
        );

        if(templateItemMstList.size() != idsToDelete.length) {
            return ServiceResponse.errorResponse(language.notFoundForId("Template item", idsToDelete));
        }

        // Create Log
        int noOfRowsAffected = templateItemRepository.createLog(List.of(idsToDelete));
        if(noOfRowsAffected != idsToDelete.length) {
            throw new ApplicationException(language.deleteError("Template item"));
        }

        templateItemMstList.forEach(templateItem -> {
            templateItem.setUnumIsvalid(0);
            templateItem.setUdtEntryDate(templateItemBean.getUdtEntryDate());
            templateItem.setUnumEntryUid(templateItemBean.getUnumEntryUid());
        });

        templateItemRepository.saveAll(templateItemMstList);
        return ServiceResponse.successMessage(language.deleteSuccess("Template item"));
    }
}
