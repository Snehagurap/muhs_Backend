package in.cdac.university.globalService.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.cdac.university.globalService.bean.CheckListBean;
import in.cdac.university.globalService.bean.CheckListItems;
import in.cdac.university.globalService.bean.CourseBean;
import in.cdac.university.globalService.bean.HeadClassCheckList;
import in.cdac.university.globalService.bean.StreamBean;
import in.cdac.university.globalService.bean.TemplateDetailBean;
import in.cdac.university.globalService.bean.TemplateMasterBean;
import in.cdac.university.globalService.bean.TemplateMasterDtlsBean;
import in.cdac.university.globalService.controller.GmstCourseTypeMst;
import in.cdac.university.globalService.entity.GmstConfigTemplateDtl;
import in.cdac.university.globalService.entity.GmstConfigTemplateMst;
import in.cdac.university.globalService.entity.GmstCourseMst;
import in.cdac.university.globalService.entity.GmstCoursefacultyMst;
import in.cdac.university.globalService.exception.ApplicationException;
import in.cdac.university.globalService.repository.TemplateDetailRepository;
import in.cdac.university.globalService.repository.TemplateRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.Language;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ServiceResponse;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CheckListService {

	@Autowired
    private TemplateRepository templateRepository;
	
	@Autowired
    private TemplateDetailRepository templateDetailRepository;

    @Autowired
    private Language language;
    
    @Transactional
    public ServiceResponse saveChecklist(CheckListBean checkListBean) throws Exception {
    	saveAndUpdateChecklistDetails(checkListBean, true);
        return ServiceResponse.builder().status(1).message(language.saveSuccess("Template")).build();
    }

    @Transactional
    public ServiceResponse updateChecklist(@Valid CheckListBean checkListBean) throws Exception {

    	saveAndUpdateChecklistDetails(checkListBean, false);
        return ServiceResponse.builder().status(1).message(language.updateSuccess("Template")).build();
        
    }
	
    @Transactional
    public void saveAndUpdateChecklistDetails(CheckListBean checkListBean, boolean isSave) throws Exception
    {
    	
    	Long unumTempleId = checkListBean.getUnumTempleId();
    	templateRepository.updateChecklistData(unumTempleId);
    	
    	for(HeadClassCheckList headClassCheckList: checkListBean.getHeadClass()){
    		for(CheckListItems checkListItems: headClassCheckList.getCheckListItems()) {
    			templateRepository.updateChecklist(unumTempleId,
    					headClassCheckList.getHeaderId(),
    					headClassCheckList.getComponentId(),
    					checkListItems.getUnumTempleItemId(),
    					checkListBean.getUstrChecklistName(),
    					checkListBean.getUnumChecklistId(),
    					checkListItems.getUstrChecklistItemName(),
    					checkListItems.getUnumChecklistItemOrderno());
    		}
    	}
    	
    }
    
}
