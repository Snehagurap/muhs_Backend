package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.DataBeanForTemplateItem;
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

import java.util.*;
import java.util.stream.Collectors;

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


        if (templateItemBean.getUnumUiControlId() == 3 || templateItemBean.getUnumUiControlId() == 18) {

            String childList = templateItemBean.getDataBean().stream().map(DataBeanForTemplateItem::getUnumTempleItemChildId).collect(Collectors.joining(","));

            GmstConfigTemplateItemMst gmstConfigTemplateItemMst = BeanUtils.copyProperties(templateItemBean, GmstConfigTemplateItemMst.class);

            gmstConfigTemplateItemMst.setUnumTempleItemId(templateItemRepository.getNextId());
            gmstConfigTemplateItemMst.setUstrParentValChildlist(childList);
            templateItemRepository.save(gmstConfigTemplateItemMst);

            List<GmstConfigTemplateItemMst> allTemplateItemsMsts = templateItemRepository.getAllItems(templateItemBean.getUnumUnivId());

            for (DataBeanForTemplateItem D : templateItemBean.getDataBean()) {
                Optional<GmstConfigTemplateItemMst> templateItemMst = allTemplateItemsMsts.stream().filter(p -> (p.getUnumTempleItemId().equals(Long.valueOf(D.getUnumTempleItemChildId())))).findAny();

                if (templateItemMst.isPresent()) {
                    int noOfRowsAffected = templateItemRepository.updateUnumParentValuCheckFlag(templateItemMst.get().getUnumTempleItemId(), D.getUnumParentValueCheckFlag());
                    if (noOfRowsAffected == 0) {
                        throw new ApplicationException(language.notFoundForId("Template Item Id ", templateItemMst.get().getUnumTempleItemId()));
                    }
                } else {
                    throw new ApplicationException(language.notFoundForId("Template Item Id ", templateItemBean.getUnumTempleItemId()));
                }
            }
        } else {
            GmstConfigTemplateItemMst gmstConfigTemplateItemMst = BeanUtils.copyProperties(templateItemBean, GmstConfigTemplateItemMst.class);
            gmstConfigTemplateItemMst.setUnumTempleItemId(templateItemRepository.getNextId());
            templateItemRepository.save(gmstConfigTemplateItemMst);
        }
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
            return ServiceResponse.errorResponse(language.mandatory("Template Item Id"));

        Optional<GmstConfigTemplateItemMst> itemById = templateItemRepository.findById(new GmstConfigTemplateItemMstPK(itemId, 1));
        if (itemById.isEmpty())
            return ServiceResponse.errorResponse(language.notFoundForId("Template Item", itemId));

        TemplateItemBean templateItemBean = BeanUtils.copyProperties(itemById.get(), TemplateItemBean.class);
        if ((templateItemBean.getUnumUiControlId() == 3 || templateItemBean.getUnumUiControlId() == 18)
                && templateItemBean.getUstrParentValChildlist() != null) {

            List<DataBeanForTemplateItem> beanList = new ArrayList<>();
            String[] childItemIds = templateItemBean.getUstrParentValChildlist().split(",");

            for (String id : childItemIds) {
                Optional<GmstConfigTemplateItemMst> templateItemMst = templateItemRepository.findById(new GmstConfigTemplateItemMstPK(Long.parseLong(id), 1));

                if (templateItemMst.isPresent()) {
                    DataBeanForTemplateItem beanForTemplateItem = new DataBeanForTemplateItem();
                    beanForTemplateItem.setUnumTempleItemChildId(templateItemMst.get().getUnumTempleItemId().toString());
                    beanForTemplateItem.setUnumParentValueCheckFlag(templateItemMst.get().getUnumParentValueCheckFlag());
                    beanList.add(beanForTemplateItem);
                } else {
                    return ServiceResponse.errorResponse(language.notFoundForId("Item", id));
                }
            }
            templateItemBean.setDataBean(beanList);
            return ServiceResponse.successObject(templateItemBean);
        } else {
            return ServiceResponse.successObject(BeanUtils.copyProperties(itemById.get(), TemplateItemBean.class));
        }
    }

    @Transactional
    public ServiceResponse update(TemplateItemBean templateItemBean) {

        if (templateItemBean.getUnumTempleItemId() == null)
            return ServiceResponse.errorResponse(language.mandatory("Template Item Id"));

        // Create Log
        int noOfRecords = templateItemRepository.createLog(List.of(templateItemBean.getUnumTempleItemId()));
        if (noOfRecords == 0) {
            throw new ApplicationException(language.notFoundForId("TemplateItem", templateItemBean.getUnumTempleItemId()));
        }

        if (templateItemBean.getUnumUiControlId() == 3 || templateItemBean.getUnumUiControlId() == 18) {
            String childList = templateItemBean.getDataBean().stream().map(DataBeanForTemplateItem::getUnumTempleItemChildId).collect(Collectors.joining(","));

            GmstConfigTemplateItemMst gmstConfigTemplateItemMst = BeanUtils.copyProperties(templateItemBean, GmstConfigTemplateItemMst.class);

            //Save new Record
            gmstConfigTemplateItemMst.setUstrParentValChildlist(childList);
            templateItemRepository.save(gmstConfigTemplateItemMst);

            List<GmstConfigTemplateItemMst> allTemplateItemsMsts = templateItemRepository.getAllItems(templateItemBean.getUnumUnivId());

            for (DataBeanForTemplateItem D : templateItemBean.getDataBean()) {
                Optional<GmstConfigTemplateItemMst> templateItemMst = allTemplateItemsMsts.stream().filter(p -> (p.getUnumTempleItemId().equals(Long.valueOf(D.getUnumTempleItemChildId())))).findAny();

                if (templateItemMst.isPresent()) {
                    int noOfRowsAffected = templateItemRepository.updateUnumParentValuCheckFlag(templateItemMst.get().getUnumTempleItemId(), D.getUnumParentValueCheckFlag());
                    if (noOfRowsAffected == 0) {
                        throw new ApplicationException(language.notFoundForId("Template Item Id ", templateItemMst.get().getUnumTempleItemId()));
                    }
                } else {
                    throw new ApplicationException(language.notFoundForId("Template Item Id ", templateItemBean.getUnumTempleItemId()));
                }
            }
        } else {
            // Save new Record for unumUiControl id not in 3 or 18
            GmstConfigTemplateItemMst gmstConfigTemplateItemMst = BeanUtils.copyProperties(templateItemBean, GmstConfigTemplateItemMst.class);
            templateItemRepository.save(gmstConfigTemplateItemMst);
        }
        return ServiceResponse.builder()
                .status(1)
                .message(language.updateSuccess("Template Item"))
                .build();

    }

    public ServiceResponse delete(TemplateItemBean templateItemBean, Long[] idsToDelete) {
        if (idsToDelete == null || idsToDelete.length == 0) {
            return ServiceResponse.errorResponse(language.mandatory("Template Item Id"));
        }

        List<GmstConfigTemplateItemMst> templateItemMstList = templateItemRepository.findByUnumTempleItemIdInAndUnumIsvalidInAndUnumUnivId(
                List.of(idsToDelete), List.of(1, 2), templateItemBean.getUnumUnivId()
        );

        if (templateItemMstList.size() != idsToDelete.length) {
            return ServiceResponse.errorResponse(language.notFoundForId("Template item", idsToDelete));
        }

        // Create Log
        int noOfRowsAffected = templateItemRepository.createLog(List.of(idsToDelete));
        if (noOfRowsAffected != idsToDelete.length) {
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
