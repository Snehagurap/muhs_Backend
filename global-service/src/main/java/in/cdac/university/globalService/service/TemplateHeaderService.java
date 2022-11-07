package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.TemplateHeaderBean;
import in.cdac.university.globalService.entity.GmstConfigTemplateHeaderMst;
import in.cdac.university.globalService.exception.ApplicationException;
import in.cdac.university.globalService.repository.TemplateHeaderRepository;
import in.cdac.university.globalService.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TemplateHeaderService {

    @Autowired
    private Language language;

    @Autowired
    private TemplateHeaderRepository templateHeaderRepository;

    public List<TemplateHeaderBean> listPageData() throws Exception {
        return BeanUtils.copyListProperties(
                templateHeaderRepository.findByUnumIsvalidAndUnumUnivIdOrderByUstrTemplHeadCodeAsc(1, RequestUtility.getUniversityId()),
                TemplateHeaderBean.class
        );
    }

    public ServiceResponse getTemplateHeaderById(Long headerId) throws Exception {
        Optional<GmstConfigTemplateHeaderMst> gmstConfigTemplateHeaderMstOptional = templateHeaderRepository.findByUnumTemplHeadIdAndUnumIsvalidAndUnumUnivId(
                headerId, 1, RequestUtility.getUniversityId());

        if(gmstConfigTemplateHeaderMstOptional.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Template Header", headerId));
        }

        TemplateHeaderBean templateHeaderBean = BeanUtils.copyProperties(gmstConfigTemplateHeaderMstOptional.get(), TemplateHeaderBean.class);

        return ServiceResponse.builder()
                .status(1)
                .responseObject(templateHeaderBean)
                .build();
    }
    
    @Transactional
    public ServiceResponse save(TemplateHeaderBean templateHeaderBean) {
        //Duplicate Check
        List<GmstConfigTemplateHeaderMst> gmstConfigTemplateHeaderMstList = templateHeaderRepository.findByUstrHeadPrintTextIgnoreCaseAndUnumIsvalidAndUnumUnivId(
                templateHeaderBean.getUstrHeadPrintText(), 1, templateHeaderBean.getUnumUnivId()
        );

        if(!gmstConfigTemplateHeaderMstList.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("Header", templateHeaderBean.getUstrHeadPrintText()));
        }

        // Generate new header id
        GmstConfigTemplateHeaderMst gmstConfigTemplateHeaderMst = BeanUtils.copyProperties(templateHeaderBean, GmstConfigTemplateHeaderMst.class);
        gmstConfigTemplateHeaderMst.setUnumTemplHeadId(templateHeaderRepository.getNextId());
        templateHeaderRepository.save(gmstConfigTemplateHeaderMst);

        return ServiceResponse.builder()
                .status(1)
                .message(language.saveSuccess("Template Header"))
                .build();
    }

    @Transactional
    public ServiceResponse update(TemplateHeaderBean templateHeaderBean) {
        if(templateHeaderBean.getUnumTemplHeadId() == null) {
            return ServiceResponse.errorResponse(language.mandatory("Header Id"));
        }

        //Duplicate Check
        List<GmstConfigTemplateHeaderMst> gmstConfigTemplateHeaderMstList = templateHeaderRepository.findByUnumTemplHeadIdNotAndUstrHeadPrintTextIgnoreCaseAndUnumIsvalidAndUnumUnivId(
                templateHeaderBean.getUnumTemplHeadId(), templateHeaderBean.getUstrHeadPrintText(), 1, templateHeaderBean.getUnumUnivId()
        );

        if(!gmstConfigTemplateHeaderMstList.isEmpty()) {
            return ServiceResponse.errorResponse(language.duplicate("Header", templateHeaderBean.getUstrHeadPrintText()));
        }

        // Create Log
        int noOfRowsAffected = templateHeaderRepository.createLog(List.of(templateHeaderBean.getUnumTemplHeadId()));
        if(noOfRowsAffected == 0) {
            throw new ApplicationException(language.notFoundForId("Header", templateHeaderBean.getUnumTemplHeadId()));
        }

        // Save New Data
        GmstConfigTemplateHeaderMst gmstConfigTemplateHeaderMst = BeanUtils.copyProperties(templateHeaderBean, GmstConfigTemplateHeaderMst.class);
        templateHeaderRepository.save(gmstConfigTemplateHeaderMst);

        return ServiceResponse.builder()
                .status(1)
                .message(language.updateSuccess("Header"))
                .build();
    }

    @Transactional
    public ServiceResponse delete(TemplateHeaderBean templateHeaderBean, Long[] idsToDelete) {
        if(idsToDelete == null || idsToDelete.length == 0) {
            return ServiceResponse.errorResponse(language.mandatory("Header Id"));
        }

        List<GmstConfigTemplateHeaderMst> gmstConfigTemplateHeaderMstList = templateHeaderRepository.findByUnumTemplHeadIdInAndUnumIsvalidAndUnumUnivId(
                        List.of(idsToDelete), 1, templateHeaderBean.getUnumUnivId()
        );

        if(gmstConfigTemplateHeaderMstList.size() != idsToDelete.length) {
            return ServiceResponse.errorResponse(language.notFoundForId("Header", Arrays.toString(idsToDelete)));
        }

        // Create Log
        int noOfRowsAffected = templateHeaderRepository.createLog(List.of(idsToDelete));
        if(noOfRowsAffected != idsToDelete.length){
            throw new ApplicationException(language.deleteError("Header"));
        }

        gmstConfigTemplateHeaderMstList.forEach(header -> {
            header.setUnumIsvalid(0);
            header.setUdtEntryDate(templateHeaderBean.getUdtEntryDate());
            header.setUnumEntryUid(header.getUnumEntryUid());
        });

        templateHeaderRepository.saveAll(gmstConfigTemplateHeaderMstList);

        return ServiceResponse.builder()
                .status(1)
                .message(language.deleteSuccess("Header"))
                .build();
    }
}
