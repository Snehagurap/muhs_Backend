package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.TemplateSubHeaderBean;
import in.cdac.university.globalService.entity.GmstConfigTemplateSubheaderMst;
import in.cdac.university.globalService.repository.TemplateSubHeaderRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.Language;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TemplateSubHeaderService {

    @Autowired
    private TemplateSubHeaderRepository templateSubHeaderRepository;

    @Autowired
    private Language language;

    public List<TemplateSubHeaderBean> listPageData(Long headerId) throws Exception {
        List<GmstConfigTemplateSubheaderMst> subHeadList = templateSubHeaderRepository.findByUnumIsvalidAndUnumUnivIdAndUnumTempleHeadIdOrderByUnumSubheadDisplayOrderAsc(
                1, RequestUtility.getUniversityId(), headerId
        );
        return BeanUtils.copyListProperties(subHeadList, TemplateSubHeaderBean.class);
    }

    @Transactional
    public ServiceResponse save(TemplateSubHeaderBean templateSubHeaderBean) {
        GmstConfigTemplateSubheaderMst gmstConfigTemplateSubheaderMst = BeanUtils.copyProperties(templateSubHeaderBean, GmstConfigTemplateSubheaderMst.class);
        gmstConfigTemplateSubheaderMst.setUnumTempleSubheadId(templateSubHeaderRepository.getNextId());
        templateSubHeaderRepository.save(gmstConfigTemplateSubheaderMst);
        return ServiceResponse.successMessage(language.saveSuccess("Template Sub Header"));
    }

    public ServiceResponse getSubHeaderById(Long subHeaderId) throws Exception {
        Optional<GmstConfigTemplateSubheaderMst> gmstConfigTemplateSubheaderMstOptional = templateSubHeaderRepository.findByUnumTempleSubheadIdAndUnumIsvalidAndUnumUnivId(
                subHeaderId, 1, RequestUtility.getUniversityId()
        );

        if(gmstConfigTemplateSubheaderMstOptional.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Sub Header Id", subHeaderId));
        }

        TemplateSubHeaderBean templateSubHeaderBean = BeanUtils.copyProperties(gmstConfigTemplateSubheaderMstOptional.get(), TemplateSubHeaderBean.class);

        return ServiceResponse.successObject(templateSubHeaderBean);
    }

    @Transactional
    public ServiceResponse update(TemplateSubHeaderBean templateSubHeaderBean) {
        if(templateSubHeaderBean == null) {
            return ServiceResponse.errorResponse(language.mandatory("Sub Header Id"));
        }

        // Create Log
        int noOfRowsAffected = templateSubHeaderRepository.createLog(List.of(templateSubHeaderBean.getUnumTempleSubheadId()));
        if(noOfRowsAffected == 0) {
            return ServiceResponse.errorResponse(language.notFoundForId("Sub Header", templateSubHeaderBean.getUnumTempleSubheadId()));
        }

        // Save new Record
        GmstConfigTemplateSubheaderMst gmstConfigTemplateSubheaderMst = BeanUtils.copyProperties(templateSubHeaderBean, GmstConfigTemplateSubheaderMst.class);
        templateSubHeaderRepository.save(gmstConfigTemplateSubheaderMst);

        return ServiceResponse.successMessage(language.updateSuccess("Sub Header"));
    }

    @Transactional
    public ServiceResponse delete(TemplateSubHeaderBean templateSubHeaderBean, Long[] idsToDelete) throws Exception {
        if(idsToDelete == null || idsToDelete.length == 0) {
            return ServiceResponse.errorResponse(language.mandatory("Sub Header Id"));
        }

        List<GmstConfigTemplateSubheaderMst> gmstConfigTemplateSubheaderMstList = templateSubHeaderRepository.findByUnumTempleSubheadIdInAndUnumIsvalidAndUnumUnivId(
          List.of(idsToDelete), 1, RequestUtility.getUniversityId()
        );

        if(gmstConfigTemplateSubheaderMstList.size() != idsToDelete.length) {
            return ServiceResponse.errorResponse(language.notFoundForId("Sub Header", Arrays.toString(idsToDelete)));
        }

        // Create Log
        int noOfRowsAffected = templateSubHeaderRepository.createLog(List.of(idsToDelete));
        if(noOfRowsAffected != idsToDelete.length) {
            return ServiceResponse.errorResponse(language.deleteError("Sub Header"));
        }

        gmstConfigTemplateSubheaderMstList.forEach(templateSubHeaderMst -> {
            templateSubHeaderMst.setUnumIsvalid(0);
            templateSubHeaderMst.setUdtEntryDate(templateSubHeaderBean.getUdtEntryDate());
            templateSubHeaderMst.setUnumEntryUid(templateSubHeaderMst.getUnumEntryUid());
        });

        templateSubHeaderRepository.saveAll(gmstConfigTemplateSubheaderMstList);

        return ServiceResponse.successMessage(language.deleteSuccess("Sub Header"));
    }
}
