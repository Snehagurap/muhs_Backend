package in.cdac.university.globalService.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.cdac.university.globalService.bean.CompHeadSubHeader;
import in.cdac.university.globalService.bean.TemplateMasterBean;
import in.cdac.university.globalService.bean.TemplateMasterDtlsBean;
import in.cdac.university.globalService.bean.TemplateMasterDtlsChildBean;
import in.cdac.university.globalService.entity.GmstConfigTemplateComponentDtl;
import in.cdac.university.globalService.entity.GmstConfigTemplateComponentMst;
import in.cdac.university.globalService.entity.GmstConfigTemplateDtl;
import in.cdac.university.globalService.entity.GmstConfigTemplateHeaderMst;
import in.cdac.university.globalService.entity.GmstConfigTemplateItemMst;
import in.cdac.university.globalService.entity.GmstConfigTemplateMst;
import in.cdac.university.globalService.entity.GmstConfigTemplateSubheaderMst;
import in.cdac.university.globalService.entity.GmstCoursefacultyMst;
import in.cdac.university.globalService.repository.FacultyRepository;
import in.cdac.university.globalService.repository.TemplateComponentDetailRepository;
import in.cdac.university.globalService.repository.TemplateComponentRepository;
import in.cdac.university.globalService.repository.TemplateDetailRepository;
import in.cdac.university.globalService.repository.TemplateHeaderRepository;
import in.cdac.university.globalService.repository.TemplateItemRepository;
import in.cdac.university.globalService.repository.TemplateRepository;
import in.cdac.university.globalService.repository.TemplateSubHeaderRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.Language;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ServiceResponse;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TemplateService {

    @Autowired
    private TemplateRepository templateRepository;
    
    @Autowired
    private TemplateDetailRepository templateDetailRepository;
    
    @Autowired
	private Language language;
    
    @Autowired
    private FacultyRepository facultyRepository;
    
    @Autowired
    private TemplateItemRepository templateItemRepository;
    
    @Autowired
    private  TemplateComponentRepository templateComponentRepository;
    
    @Autowired
    private TemplateComponentDetailRepository templateComponentDetailRepository;
    
    @Autowired
    private TemplateHeaderRepository templateHeaderRepository;
    
    @Autowired
    private TemplateSubHeaderRepository templateSubHeaderRepository;
    
    
    public ServiceResponse getTemplate(Long templateId) {
        return null;
    }

    @Transactional
	public ServiceResponse save(TemplateMasterBean templateMasterBean) throws Exception{
    	
    	ServiceResponse	resp = saveAndUpdateTemplateMaster(templateMasterBean, true);	
		return ServiceResponse.builder().status(1).message(language.saveSuccess("Templatelist")).build();
	}
	
	@Transactional
	public ServiceResponse update( TemplateMasterBean templateMasterBean) throws Exception {
		
    	ServiceResponse	resp = saveAndUpdateTemplateMaster(templateMasterBean, false);	
		return ServiceResponse.builder().status(1).message(language.updateSuccess("Templatelist")).build();
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
			log.info("UnumTempleId {}",gmstConfigTemplateMst.getUnumTempleId() );
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
		  GmstConfigTemplateDtl gmstConfigTemplateDtl = null;
		  for (TemplateMasterDtlsBean templateMasterDtls : templateMasterBean.getTemplateMasterDtlsBeanList()) {
	      
			 gmstConfigTemplateDtl = new GmstConfigTemplateDtl();
	      gmstConfigTemplateDtlEntityList.add(gmstConfigTemplateDtl);
		  
		  BeanUtils.copyProperties(templateMasterDtls, gmstConfigTemplateDtl);
		  gmstConfigTemplateDtl.setUnumTempledtlId(templateDetailRepository.getNextUnumTempledtlId());
		  log.info("UnumTempledtlId {}",gmstConfigTemplateDtl.getUnumTempledtlId() );
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

    
    public ServiceResponse getTemplateById(Long templateId) throws Exception
    {
    	TemplateMasterBean templateMasterBean = new TemplateMasterBean();
    	BeanUtils.copyProperties(templateRepository.findByUnumIsvalidAndUnumUnivIdAndUnumTempleId(1,RequestUtility.getUniversityId(),templateId),templateMasterBean) ;
    	log.debug("templateMasterBean {}" , templateMasterBean);
    	
    	log.debug("templateMasterBean {}" , templateMasterBean);
    	List<TemplateMasterDtlsBean> templateMasterDtlsBeans = BeanUtils.copyListProperties(templateDetailRepository.findByUnumIsvalidAndUnumUnivIdAndUnumTempleId(1,RequestUtility.getUniversityId(),templateId), TemplateMasterDtlsBean.class) ;
    	log.debug("templateMasterDtlsBeans {}" , templateMasterDtlsBeans);
    	
    	GmstCoursefacultyMst gmstCoursefacultyMst = facultyRepository.findByUnumCfacultyIdAndUnumIsvalid(templateMasterBean.getUnumFacultyId(), 1);
    	templateMasterBean.setUstrCfacultyFname(gmstCoursefacultyMst.getUstrCfacultyFname());
    	templateMasterBean.setUstrCfacultySname(gmstCoursefacultyMst.getUstrCfacultySname());
    	
    	
    	
		 List<GmstConfigTemplateItemMst> gmstConfigTemplateItemMstList = templateItemRepository.findItemsByTemplateId(templateMasterBean.getUnumUnivId(), Arrays.asList(templateId));
		 Map<Long, String> itemMstMap = gmstConfigTemplateItemMstList.stream().collect(
				 Collectors.toMap(GmstConfigTemplateItemMst :: getUnumTempleItemId, GmstConfigTemplateItemMst :: getUstrItemPrintPreText));
		 
		
		 
		 List<GmstConfigTemplateComponentMst> gmstConfigTemplateComponentMstList = templateComponentRepository.findComponentsByTemplateId(templateMasterBean.getUnumUnivId(), Arrays.asList(templateId));
		 Map<Long, String> compMstMap = gmstConfigTemplateComponentMstList.stream().collect(
				 Collectors.toMap(GmstConfigTemplateComponentMst :: getUnumTempleCompId, GmstConfigTemplateComponentMst :: getUstrCompPrintText));
		 
		 log.debug("gmstConfigTemplateComponentMstList {}" , gmstConfigTemplateComponentMstList);
		 
		 List<GmstConfigTemplateComponentDtl> gmstConfigTemplateComponentDtlList = templateComponentDetailRepository.findComponentDtlsByTemplateId(templateMasterBean.getUnumUnivId(), Arrays.asList(templateId));
		 Map<Long, String> compDtlsMap = gmstConfigTemplateComponentDtlList.stream().collect(
				 Collectors.toMap(GmstConfigTemplateComponentDtl :: getUnumTempleCompItemId, GmstConfigTemplateComponentDtl :: getUstrDescription));
		 List<GmstConfigTemplateHeaderMst> gmstConfigTemplateHeaderMstList = templateHeaderRepository.findHeadersByTemplateId(templateMasterBean.getUnumUnivId(), Arrays.asList(templateId));
//		 Map<Long, String> headMstMap = gmstConfigTemplateHeaderMstList.stream().collect(
//				 Collectors.toMap(GmstConfigTemplateHeaderMst :: getUnumTempleHeadId, GmstConfigTemplateHeaderMst ::  getUstrHeadPrintText));
		 Map<Long, String> headMstMap = gmstConfigTemplateHeaderMstList.stream().collect(
				 HashMap::new, (m,v)->m.put(v.getUnumTempleHeadId(), v.getUstrHeadPrintText()==null ? "-":v.getUstrHeadPrintText() ), HashMap::putAll);
		 List<GmstConfigTemplateSubheaderMst> gmstConfigTemplateSubHeaderMstList = templateSubHeaderRepository.findSubHeadersByTemplateId(templateMasterBean.getUnumUnivId(), Arrays.asList(templateId));
		 Map<Long, String> subHeadMstMap = gmstConfigTemplateSubHeaderMstList.stream().collect(
				 Collectors.toMap(GmstConfigTemplateSubheaderMst :: getUnumTempleSubheadId, GmstConfigTemplateSubheaderMst :: getUstrSubheadPrintText));
		 Map<CompHeadSubHeader,List<TemplateMasterDtlsChildBean>> resultmap = new HashMap<CompHeadSubHeader,List<TemplateMasterDtlsChildBean>>();
		 CompHeadSubHeader key = null;
		 for(TemplateMasterDtlsBean templateMasterDtlsBean : templateMasterDtlsBeans) {
			 templateMasterDtlsBean.setUstrItemPrintPreText(itemMstMap.get(templateMasterDtlsBean.getUnumTempleItemId()));
			 templateMasterDtlsBean.setUstrCompPrintText(compMstMap.get(templateMasterDtlsBean.getUnumTempleCompId()));
			 templateMasterDtlsBean.setUstrDescription(compDtlsMap.get(templateMasterDtlsBean.getUnumTempleCompItemId()));
			 templateMasterDtlsBean.setUstrHeadPrintText(headMstMap.get(templateMasterDtlsBean.getUnumTempleHeadId()));
			 templateMasterDtlsBean.setUstrSubheadPrintText(subHeadMstMap.get(templateMasterDtlsBean.getUnumTempleSubheadId()));
			
			 
			 	key = new CompHeadSubHeader();
				 
				BeanUtils.copyProperties(templateMasterDtlsBean, key);
				 List<TemplateMasterDtlsChildBean> childrens;
	    		
	    			if(resultmap.containsKey(key))
	    				{	
	    			    childrens = resultmap.get(key);
	    				}
	    			else {
	    			     childrens = new ArrayList<>();
	    			}
	    			TemplateMasterDtlsChildBean newChildbean = BeanUtils.copyProperties(templateMasterDtlsBean,TemplateMasterDtlsChildBean.class);
	   			    childrens.add(newChildbean); 
	   			    key.setChildren(childrens);
	   			    resultmap.put(key, childrens);
		 }
    	templateMasterBean.setCompHeadSubHeaders(resultmap.keySet());

//    	templateMasterBean.setTemplateMasterDtlsBeanList(templateMasterDtlsBeans);
    	return ServiceResponse.successObject(templateMasterBean);
    }
    
    public ServiceResponse getAllTemplate() throws Exception
    {
    	List<GmstConfigTemplateMst> gmstConfigTemplateMsts = templateRepository.findByUnumIsvalidAndUnumUnivId(1,RequestUtility.getUniversityId());
    	
    	return ServiceResponse.successObject(
				BeanUtils.copyListProperties(gmstConfigTemplateMsts, TemplateMasterBean.class));
    }

}
