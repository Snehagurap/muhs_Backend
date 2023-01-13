package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.TemplateItemApiBean;
import in.cdac.university.globalService.entity.GmstConfigTemplateItemApiMst;
import in.cdac.university.globalService.exception.ApplicationException;
import in.cdac.university.globalService.repository.TemplateItemApiRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.Language;
import in.cdac.university.globalService.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TemplateItemApiService {
    @Autowired
    private TemplateItemApiRepository templateItemApiRepository;

    @Autowired
    private Language language;

    public List<TemplateItemApiBean> getAllTemplateItemApis() {
        return BeanUtils.copyListProperties(
                templateItemApiRepository.getAllTemplateItemApis(),
                TemplateItemApiBean.class
        );
    }

    public ServiceResponse getTemplateItemApi(Long apiId) {
        Optional<GmstConfigTemplateItemApiMst> apiMstOptional = templateItemApiRepository.findByUnumIsvalidAndUnumApiId(1, apiId);

        if (apiMstOptional.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Template Item Api", apiId));
        }

        return ServiceResponse.builder()
                .status(1)
                .responseObject(BeanUtils.copyProperties(apiMstOptional.get(), TemplateItemApiBean.class))
                .build();
    }

    public List<TemplateItemApiBean> listPageData() throws Exception {
        return BeanUtils.copyListProperties(
                templateItemApiRepository.listPageData(),
                TemplateItemApiBean.class
        );
    }

    @Transactional
    public ServiceResponse save(TemplateItemApiBean templateItemApiBean) {
        //for duplicate
        List<GmstConfigTemplateItemApiMst> templateItemApiList = templateItemApiRepository.findByUnumIsvalidAndUstrApiNameIgnoreCase(
                1, templateItemApiBean.getUstrApiName()
        );
        if (!templateItemApiList.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("Template Item Api", templateItemApiBean.getUstrApiName()));
        }
        GmstConfigTemplateItemApiMst templateItemApiMst = BeanUtils.copyProperties(templateItemApiBean, GmstConfigTemplateItemApiMst.class);
        Long apiId = templateItemApiRepository.getNextId();
        templateItemApiMst.setUnumApiId(apiId);
        templateItemApiRepository.save(templateItemApiMst);

        return ServiceResponse.builder()
                .status(1)
                .message(language.saveSuccess("Template Item Api"))
                .build();
    }

    //for update
    @Transactional
    public ServiceResponse update(TemplateItemApiBean templateItemApiBean) {
        if (templateItemApiBean.getUnumApiId() == null) {
            return ServiceResponse.errorResponse((language.mandatory("Template Item Api Id")));
        }

        // Duplicate Check
        List<GmstConfigTemplateItemApiMst> templateItemApiMsts = templateItemApiRepository.findByUnumIsvalidAndUstrApiNameIgnoreCaseAndUnumApiIdNot(
                1, templateItemApiBean.getUstrApiName(), templateItemApiBean.getUnumApiId()
        );

        if (!templateItemApiMsts.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("Template Item Api ", templateItemApiBean.getUstrApiName()));
        }

        // Create Log
        int noOfRecordsAffected = templateItemApiRepository.createLog(List.of((templateItemApiBean.getUnumApiId())));
        if (noOfRecordsAffected == 0) {
            throw new ApplicationException(language.notFoundForId("Template Item Api Id", templateItemApiBean.getUnumApiId()));
        }

        // Save new Data
        GmstConfigTemplateItemApiMst templateItemApiMst = BeanUtils.copyProperties(templateItemApiBean, GmstConfigTemplateItemApiMst.class);
        templateItemApiRepository.save(templateItemApiMst);

        return ServiceResponse.builder()
                .status(1)
                .message(language.updateSuccess("Template Item Api"))
                .build();
    }

    @Transactional
    public ServiceResponse delete(TemplateItemApiBean templateItemApiBean, Long[] idsToDelete) {
        if (idsToDelete == null || idsToDelete.length == 0) {
            return ServiceResponse.errorResponse(language.mandatory("Template Item Api"));
        }

        List<GmstConfigTemplateItemApiMst> templateItemApiMsts = templateItemApiRepository.findByUnumIsvalidAndUnumApiIdIn(
                1, List.of(idsToDelete)
        );

        if (templateItemApiMsts.size() != idsToDelete.length) {
            return ServiceResponse.errorResponse(language.notFoundForId("Template Item Api", Arrays.toString(idsToDelete)));
        }

        // Create Log
        int noOfRowsAffected = templateItemApiRepository.createLog(List.of(idsToDelete));
        if (noOfRowsAffected != idsToDelete.length) {
            throw new ApplicationException(language.deleteError("Template Item Api"));
        }

        templateItemApiMsts.forEach(templateItemApi -> {
            templateItemApi.setUnumIsvalid(0);
            templateItemApi.setUdtEntryDate(templateItemApiBean.getUdtEntryDate());
        });

        templateItemApiRepository.saveAll(templateItemApiMsts);
        return ServiceResponse.builder()
                .status(1)
                .message(language.deleteSuccess("Template Item Api"))
                .build();
    }
}
