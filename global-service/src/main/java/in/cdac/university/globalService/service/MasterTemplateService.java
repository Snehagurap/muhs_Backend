package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.MasterTemplateBean;
import in.cdac.university.globalService.entity.GmstConfigMastertemplateMst;
import in.cdac.university.globalService.entity.GmstConfigMastertemplateMstPK;
import in.cdac.university.globalService.entity.GmstConfigMastertemplateTemplatedtl;
import in.cdac.university.globalService.entity.GmstConfigTemplateMst;
import in.cdac.university.globalService.repository.MasterTemplateDetailRepository;
import in.cdac.university.globalService.repository.MasterTemplateRepository;
import in.cdac.university.globalService.repository.TemplateRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.Language;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MasterTemplateService {

    @Autowired
    private MasterTemplateRepository masterTemplateRepository;

    @Autowired
    private MasterTemplateDetailRepository masterTemplateDetailRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private Language language;

    public ServiceResponse getTemplate(Long templateId) throws Exception {
        Optional<GmstConfigMastertemplateMst> templateByIdOptional = masterTemplateRepository.findById(new GmstConfigMastertemplateMstPK(templateId, 1));
        if (templateByIdOptional.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Master Template", templateId));
        }

        MasterTemplateBean masterTemplateBean = BeanUtils.copyProperties(templateByIdOptional.get(), MasterTemplateBean.class);

        Integer universityId = RequestUtility.getUniversityId();

        List<GmstConfigMastertemplateTemplatedtl> masterTemplateDetails = masterTemplateDetailRepository.findByUnumIsvalidAndUnumUnivIdAndUnumMtempleId(1, universityId, masterTemplateBean.getUnumMtempleId());

        List<Long> templateIds = masterTemplateDetails.stream()
                .map(GmstConfigMastertemplateTemplatedtl::getUnumTempleId)
                .toList();

        List<GmstConfigTemplateMst> templates = templateRepository.findByUnumIsvalidAndUnumUnivIdAndUnumTempleIdIn(1, universityId, templateIds);

        return ServiceResponse.successObject(masterTemplateBean);
    }
}
