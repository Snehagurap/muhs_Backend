package in.cdac.university.globalService.service;


import in.cdac.university.globalService.bean.TemplateMasterBean;
import in.cdac.university.globalService.bean.TemplateMasterDtlsBean;
import in.cdac.university.globalService.entity.GmstConfigTemplateComponentDtl;
import in.cdac.university.globalService.entity.GmstConfigTemplateComponentMst;
import in.cdac.university.globalService.entity.GmstConfigTemplateDtl;
import in.cdac.university.globalService.entity.GmstConfigTemplateMst;
import in.cdac.university.globalService.repository.TemplateDetailRepository;
import in.cdac.university.globalService.repository.TemplateRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ServiceResponse;
import in.cdac.university.globalService.util.StringUtility;
import lombok.extern.slf4j.Slf4j;
import in.cdac.university.globalService.util.Language;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class TemplateService {

    @Autowired
    private TemplateRepository templateRepository;
    
    @Autowired
    private TemplateDetailRepository templateDetailRepository;
    
    @Autowired
	private Language language;

    public ServiceResponse getTemplate(Long templateId) {
        return null;
    }

    @Transactional
	public ServiceResponse save(@Valid TemplateMasterBean templateMasterBean) throws Exception{
		return saveAndUpdateTemplateMaster(templateMasterBean, true);
	}
	
	@Transactional
	public ServiceResponse update(TemplateMasterBean templateMasterBean) throws Exception {
		return saveAndUpdateTemplateMaster(templateMasterBean, false);
	}
	
	public ServiceResponse saveAndUpdateTemplateMaster(TemplateMasterBean templateMasterBean, boolean isSave)
			throws Exception {
		GmstConfigTemplateMst gmstConfigTemplateMst = new GmstConfigTemplateMst();
		BeanUtils.copyProperties(templateMasterBean, gmstConfigTemplateMst);

		if (!isSave) {
			Integer updatedRow = templateRepository
					.updateTemplateMasterRecord(templateMasterBean.getUnumTempleId());
			if (updatedRow > 0) {
				templateDetailRepository.updateTemplateMasterDtlsRecord(templateMasterBean.getUnumTempleId());
			} else {
				log.info("No Active template found to Update for ID : {} ", templateMasterBean.getUnumTempleId());
				return ServiceResponse.builder().status(1).message(language.updateError("Template")).build();
			}

		} else {
			gmstConfigTemplateMst.setUnumTempleId(templateRepository.getNextUnumTempleId());
		}

		
		  gmstConfigTemplateMst.setUnumIsvalid(1);
		  gmstConfigTemplateMst.setUdtEffFrom(new Date());
		  gmstConfigTemplateMst.setUnumUnivId(RequestUtility.getUniversityId());
		  gmstConfigTemplateMst.setUnumEntryUid(RequestUtility.getUserId());
		  gmstConfigTemplateMst.setUdtEntryDate(new Date());
		  
		  log.info("univ id {}", gmstConfigTemplateMst.getUnumUnivId());
		  templateRepository.save(gmstConfigTemplateMst);
		 
		int count = 1;

		
		  List<GmstConfigTemplateDtl> gmstConfigTemplateDtlEntityList = new ArrayList<>(); 
		  for (TemplateMasterDtlsBean templateMasterDtls : templateMasterBean.getTemplateMasterDtlsBeanList()) {
	      GmstConfigTemplateDtl gmstConfigTemplateDtl = new GmstConfigTemplateDtl();
	      gmstConfigTemplateDtlEntityList.add(gmstConfigTemplateDtl);
		  
		  BeanUtils.copyProperties(templateMasterDtls, gmstConfigTemplateDtl);
		  gmstConfigTemplateDtl.setUnumTempledtlId(Long.parseLong(gmstConfigTemplateMst.getUnumTempleId() +StringUtility.padLeftZeros(count++ + "", 5)));
		  gmstConfigTemplateDtl.setUnumTempleId(gmstConfigTemplateMst.getUnumTempleId());
		  gmstConfigTemplateDtl.setUnumIsvalid(1);
		  gmstConfigTemplateDtl.setUnumEntryUid(RequestUtility.getUserId());
		  gmstConfigTemplateDtl.setUdtEffFrom(new Date());
		  gmstConfigTemplateDtl.setUnumUnivId(RequestUtility.getUniversityId()); 
		  gmstConfigTemplateDtl.setUdtEntryDate(new Date()); }
		  templateDetailRepository.saveAll(gmstConfigTemplateDtlEntityList); 
		  if (!isSave)
		  return ServiceResponse.builder().status(1).message(language.updateSuccess( "Template")).build(); 
		  else 
			  return ServiceResponse.builder().status(1).message(language.saveSuccess("Template")).build();
	}
	
	@Transactional
	public ServiceResponse delete(List<Long> idsToDelete) throws Exception {
		Long entryUserId = RequestUtility.getUserId();

		// Getting existing active/ inactive records
		List<GmstConfigTemplateMst> gmstConfigTemplateMstlist = templateRepository.findByUnumTempleIdInAndUnumIsvalidIn(idsToDelete, List.of(1, 2));

		List<GmstConfigTemplateDtl> gmstConfigTemplateDtlList = templateDetailRepository.findByUnumTempleIdInAndUnumIsvalidIn(idsToDelete, List.of(1, 2));

		// creating log for exiting active record of template component
		Integer deletedCount = templateRepository.deleteTemplateMstRecord(idsToDelete);
		if (deletedCount > 0) {
			// creating log for exiting active record of template component item
			templateDetailRepository.deleteTemplateDtlRecord(idsToDelete);
		} else {
			return ServiceResponse.builder().status(1).message(language.deleteError("Template")).build();
		}

		gmstConfigTemplateMstlist.forEach(component -> {
			component.setUnumIsvalid(0);
			component.setUdtEntryDate(new Date());
			component.setUnumEntryUid(entryUserId);
		});
		templateRepository.saveAll(gmstConfigTemplateMstlist);

		gmstConfigTemplateDtlList.forEach(item -> {
			item.setUnumIsvalid(0);
			item.setUdtEntryDate(new Date());
			item.setUnumEntryUid(entryUserId);
		});
		templateDetailRepository.saveAll(gmstConfigTemplateDtlList);

		return ServiceResponse.builder().status(1).message(language.deleteSuccess("Template")).build();

	}
}
