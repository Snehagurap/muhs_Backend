package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.TemplateMasterBean;
import in.cdac.university.globalService.bean.TemplateMasterDtlsBean;
import in.cdac.university.globalService.entity.GmstConfigTemplateDtl;
import in.cdac.university.globalService.entity.GmstConfigTemplateMst;
import in.cdac.university.globalService.repository.TemplateDetailRepository;
import in.cdac.university.globalService.repository.TemplateRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.ComboUtility;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ServiceResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TemplateService {

    @Autowired
    private TemplateRepository templateRepository;
    @Autowired
    private TemplateDetailRepository templateDetailRepository;

    public ServiceResponse getTemplate(Long templateId) {
        return null;
    }
    
    public ServiceResponse getTemplateById(Long templateId) throws Exception
    {
    	TemplateMasterBean templateMasterBean = new TemplateMasterBean();
    	BeanUtils.copyProperties(templateRepository.findByUnumIsvalidAndUnumUnivIdAndUnumTempleId(1,RequestUtility.getUniversityId(),templateId),templateMasterBean) ;
    	log.info("templateMasterBean {}" , templateMasterBean);
    	List<TemplateMasterDtlsBean> templateMasterDtlsBeans = BeanUtils.copyListProperties(templateDetailRepository.findByUnumIsvalidAndUnumUnivIdAndUnumTempleId(1,RequestUtility.getUniversityId(),templateId), TemplateMasterDtlsBean.class) ;
    	log.info("templateMasterDtlsBeans {}" , templateMasterDtlsBeans);
    	templateMasterBean.setTemplateMasterDtlsBeanList(templateMasterDtlsBeans);
    	return ServiceResponse.successObject(templateMasterBean);
    }
    
    public ServiceResponse getAllTemplate() throws Exception
    {
    	List<GmstConfigTemplateMst> gmstConfigTemplateMsts = templateRepository.findByUnumIsvalidAndUnumUnivId(1,RequestUtility.getUniversityId());
    	
    	
    	return ServiceResponse.successObject(
				ComboUtility.generateComboData(BeanUtils.copyListProperties(gmstConfigTemplateMsts, TemplateMasterBean.class)));
    }
}