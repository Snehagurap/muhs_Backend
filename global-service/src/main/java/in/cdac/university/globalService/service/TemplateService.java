package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.CompHeadSubHeader;
import in.cdac.university.globalService.bean.Components;
import in.cdac.university.globalService.bean.HeadClass;
import in.cdac.university.globalService.bean.TemplateMasterBean;
import in.cdac.university.globalService.bean.TemplateMasterDtlsBean;
import in.cdac.university.globalService.bean.TemplateMasterDtlsChildBean;
import in.cdac.university.globalService.bean.TempleHeadCompBean;
import in.cdac.university.globalService.entity.*;
import in.cdac.university.globalService.exception.ApplicationException;
import in.cdac.university.globalService.repository.*;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.Language;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

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
    private TemplateComponentRepository templateComponentRepository;

    @Autowired
    private TemplateComponentDetailRepository templateComponentDetailRepository;

    @Autowired
    private TemplateHeaderRepository templateHeaderRepository;

    @Autowired
    private TemplateSubHeaderRepository templateSubHeaderRepository;

    @Transactional
    public ServiceResponse save(TemplateMasterBean templateMasterBean) throws Exception {
        saveAndUpdateTemplateMaster(templateMasterBean, true);
        return ServiceResponse.builder().status(1).message(language.saveSuccess("Template")).build();
    }

    @Transactional
    public ServiceResponse update(TemplateMasterBean templateMasterBean) throws Exception {

        saveAndUpdateTemplateMaster(templateMasterBean, false);
        return ServiceResponse.builder().status(1).message(language.updateSuccess("Template")).build();
    }

    public void saveAndUpdateTemplateMaster(TemplateMasterBean templateMasterBean, boolean isSave)
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
                throw new ApplicationException(language.updateError("Template"));
            }
        } else {
            gmstConfigTemplateMst.setUnumTempleId(templateRepository.getNextUnumTempleId());
        }

        gmstConfigTemplateMst.setUnumIsvalid(1);
        gmstConfigTemplateMst.setUdtEffFrom(new Date());
        gmstConfigTemplateMst.setUnumUnivId(RequestUtility.getUniversityId());
        gmstConfigTemplateMst.setUnumEntryUid(RequestUtility.getUserId());
        gmstConfigTemplateMst.setUdtEntryDate(new Date());

        templateRepository.save(gmstConfigTemplateMst);
        List<GmstConfigTemplateDtl> gmstConfigTemplateDtlEntityList = new ArrayList<>();
        GmstConfigTemplateDtl gmstConfigTemplateDtl;
        int index = 1;
        int headCount = 1;
        Map<Long, List<Long>> headCompCountMap = new HashMap<>();
        Map<Long, Integer> headCountMap = new HashMap<>();
        for (TemplateMasterDtlsBean templateMasterDtls : templateMasterBean.getTemplateMasterDtlsBeanList()) {
            gmstConfigTemplateDtl = new GmstConfigTemplateDtl();
            gmstConfigTemplateDtlEntityList.add(gmstConfigTemplateDtl);
            BeanUtils.copyProperties(templateMasterDtls, gmstConfigTemplateDtl);
            gmstConfigTemplateDtl.setUnumTempledtlId(templateDetailRepository.getNextUnumTempledtlId());
            gmstConfigTemplateDtl.setUnumTempleId(gmstConfigTemplateMst.getUnumTempleId());
            gmstConfigTemplateDtl.setUnumIsvalid(1);
            gmstConfigTemplateDtl.setUnumEntryUid(RequestUtility.getUserId());
            gmstConfigTemplateDtl.setUdtEffFrom(new Date());
            gmstConfigTemplateDtl.setUnumUnivId(RequestUtility.getUniversityId());
            gmstConfigTemplateDtl.setUdtEntryDate(new Date()); 
            Long headId = gmstConfigTemplateDtl.getUnumTempleHeadId();
            Long compId = gmstConfigTemplateDtl.getUnumTempleCompId();
            if(!(headCountMap.containsKey(headId))) { 
            	headCountMap.put(headId, headCount);
            	List<Long> list = new ArrayList<>();
            	list.add(compId);
            	headCompCountMap.put(headId, list);
            	gmstConfigTemplateDtl.setUnumHeaderOrderNo(headCount);
            	gmstConfigTemplateDtl.setUnumComponentOrderNo(1);
            	headCount++;
            }
            else {
            	List<Long> listComp = headCompCountMap.get(headId);
            	boolean flag = true;
            	for(Long comp : listComp) {
            		if(comp == compId) {
            			flag=false;
            			break;
            		}
            	}
            	if(flag == true) {
            		listComp.add(compId);
            	}
            	gmstConfigTemplateDtl.setUnumHeaderOrderNo(headCountMap.get(headId));
            	gmstConfigTemplateDtl.setUnumComponentOrderNo(listComp.size());
            }
           
            if (gmstConfigTemplateDtl.getUnumDisplayOrder() == null)
                gmstConfigTemplateDtl.setUnumDisplayOrder(index);
            index++;
        }
        templateDetailRepository.saveAll(gmstConfigTemplateDtlEntityList);
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

    public ServiceResponse getTemplateById(Long templateId) throws Exception {
        TemplateMasterBean templateMasterBean = new TemplateMasterBean();
        BeanUtils.copyProperties(templateRepository.findByUnumIsvalidAndUnumUnivIdAndUnumTempleId(1, RequestUtility.getUniversityId(), templateId), templateMasterBean);
        log.debug("templateMasterBean {}", templateMasterBean);

        log.debug("templateMasterBean {}", templateMasterBean);
        List<TemplateMasterDtlsBean> templateMasterDtlsBeans = BeanUtils.copyListProperties(templateDetailRepository.findByUnumIsvalidAndUnumUnivIdAndUnumTempleId(1, RequestUtility.getUniversityId(), templateId), TemplateMasterDtlsBean.class);
        log.debug("templateMasterDtlsBeans {}", templateMasterDtlsBeans);

        Optional<GmstCoursefacultyMst> gmstCoursefacultyMst = facultyRepository.findByUnumCfacultyIdAndUnumIsvalid(templateMasterBean.getUnumFacultyId(), 1);
        gmstCoursefacultyMst.ifPresent(coursefacultyMst -> templateMasterBean.setUstrCfacultyFname(coursefacultyMst.getUstrCfacultyFname()));


        List<GmstConfigTemplateItemMst> gmstConfigTemplateItemMstList = templateItemRepository.findItemsByTemplateId(templateMasterBean.getUnumUnivId(), Collections.singletonList(templateId));

        Map<Long, GmstConfigTemplateItemMst> itemMstMap = gmstConfigTemplateItemMstList.stream().collect(
                HashMap::new, (m, v) -> m.put(v.getUnumTempleItemId(), v), HashMap::putAll);


        List<GmstConfigTemplateComponentMst> gmstConfigTemplateComponentMstList = templateComponentRepository.findComponentsByTemplateId(templateMasterBean.getUnumUnivId(), Collections.singletonList(templateId));
        Map<Long, String> compMstMap = gmstConfigTemplateComponentMstList.stream().collect(
                Collectors.toMap(GmstConfigTemplateComponentMst::getUnumTempleCompId, GmstConfigTemplateComponentMst::getUstrCompPrintText));

        List<GmstConfigTemplateComponentDtl> gmstConfigTemplateComponentDtlList = templateComponentDetailRepository.findComponentDtlsByTemplateId(templateMasterBean.getUnumUnivId(), Collections.singletonList(templateId));
        Map<Long, String> compDtlsMap = gmstConfigTemplateComponentDtlList.stream().collect(
                Collectors.toMap(GmstConfigTemplateComponentDtl::getUnumTempleCompItemId, GmstConfigTemplateComponentDtl::getUstrDescription));
        List<GmstConfigTemplateHeaderMst> gmstConfigTemplateHeaderMstList = templateHeaderRepository.findHeadersByTemplateId(templateMasterBean.getUnumUnivId(), Collections.singletonList(templateId));
        Map<Long, String> headMstMap = gmstConfigTemplateHeaderMstList.stream().collect(
                HashMap::new, (m, v) -> m.put(v.getUnumTempleHeadId(), v.getUstrHeadPrintText() == null ? "-" : v.getUstrHeadPrintText()), HashMap::putAll);
        List<GmstConfigTemplateSubheaderMst> gmstConfigTemplateSubHeaderMstList = templateSubHeaderRepository.findSubHeadersByTemplateId(templateMasterBean.getUnumUnivId(), Collections.singletonList(templateId));
        Map<Long, String> subHeadMstMap = gmstConfigTemplateSubHeaderMstList.stream().collect(
                Collectors.toMap(GmstConfigTemplateSubheaderMst::getUnumTempleSubheadId, GmstConfigTemplateSubheaderMst::getUstrSubheadPrintText));
        Map<CompHeadSubHeader, List<TemplateMasterDtlsChildBean>> resultmap = new HashMap<>();
        CompHeadSubHeader key;
        GmstConfigTemplateItemMst configTemplateItemMst;
        for (TemplateMasterDtlsBean templateMasterDtlsBean : templateMasterDtlsBeans) {
            configTemplateItemMst = itemMstMap.get(templateMasterDtlsBean.getUnumTempleItemId());
            if (nonNull(configTemplateItemMst)) {
                BeanUtils.copyProperties(configTemplateItemMst, templateMasterDtlsBean);
            }

            templateMasterDtlsBean.setUstrCompPrintText(compMstMap.get(templateMasterDtlsBean.getUnumTempleCompId()));
            templateMasterDtlsBean.setUstrDescription(compDtlsMap.get(templateMasterDtlsBean.getUnumTempleCompItemId()));
            templateMasterDtlsBean.setUstrHeadPrintText(headMstMap.get(templateMasterDtlsBean.getUnumTempleHeadId()));
            templateMasterDtlsBean.setUstrSubheadPrintText(subHeadMstMap.get(templateMasterDtlsBean.getUnumTempleSubheadId()));

            key = new CompHeadSubHeader();

            BeanUtils.copyProperties(templateMasterDtlsBean, key);
            List<TemplateMasterDtlsChildBean> childrens;

            if (resultmap.containsKey(key)) {
                childrens = resultmap.get(key);
            } else {
                childrens = new ArrayList<>();
            }
            TemplateMasterDtlsChildBean newChildbean = BeanUtils.copyProperties(templateMasterDtlsBean, TemplateMasterDtlsChildBean.class);
            childrens.add(newChildbean);
            key.setChildren(childrens);
            resultmap.put(key, childrens);
        }
        templateMasterBean.setCompHeadSubHeaders(resultmap.keySet());

        return ServiceResponse.successObject(templateMasterBean);
    }

    public ServiceResponse getAllTemplate() throws Exception {
        List<GmstConfigTemplateMst> gmstConfigTemplateMsts = templateRepository.findByUnumIsvalidAndUnumUnivId(1, RequestUtility.getUniversityId());

        return ServiceResponse.successObject(
                BeanUtils.copyListProperties(gmstConfigTemplateMsts, TemplateMasterBean.class));
    }
    
    @Transactional
    public ServiceResponse manageTemplateOrder(TempleHeadCompBean templeHeadCompBean) throws Exception {
    	
    	Long unumTempleId = templeHeadCompBean.getUnumTempleId();
    	for( HeadClass headClass :  templeHeadCompBean.getHeadClass()  ){
    		templateRepository.updateHeaderOrder(unumTempleId,
    												headClass.getHeaderId(),
    												headClass.getUnumHeaderOrderNo());
    		for(Components components : headClass.getComponents()) {
    			templateRepository.updateCompOrder(unumTempleId,
    					headClass.getHeaderId(),
    					components.getUnumTempleCompId(),
    					components.getUnumComponentOrderNo());
    		}
    	}
    	return ServiceResponse.builder().status(1).message(language.updateSuccess("Template")).build();
    }

}
