package in.cdac.university.globalService.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.*;
import java.io.UncheckedIOException;
import java.text.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.cdac.university.globalService.bean.TemplateComponentBean;
import in.cdac.university.globalService.entity.GmstConfigTemplateComponentDtl;
import in.cdac.university.globalService.entity.GmstConfigTemplateComponentMst;
import in.cdac.university.globalService.repository.TemplateComponentDetailRepository;
import in.cdac.university.globalService.repository.TemplateComponentRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.Language;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ServiceResponse;
import in.cdac.university.globalService.util.StringUtility;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TemplateComponentService {

    @Autowired
    private TemplateComponentRepository templateComponentRepository;

    @Autowired
    private TemplateComponentDetailRepository templateComponentDetailRepository;
    
    @Autowired
    private Language language;

    public List<TemplateComponentBean> listPage() throws Exception {
        return BeanUtils.copyListProperties(
            templateComponentRepository.findByUnumIsvalidAndUnumUnivIdOrderByUnumCompDisplayOrderAsc(1, RequestUtility.getUniversityId()),
            TemplateComponentBean.class
        );
    }
    
    
    @Transactional
    public ServiceResponse save(TemplateComponentBean templateBean) throws Exception{
    	GmstConfigTemplateComponentMst gmstConfigTemplateComponentMst = new GmstConfigTemplateComponentMst();
    	BeanUtils.copyProperties(templateBean, gmstConfigTemplateComponentMst);
    	gmstConfigTemplateComponentMst.setUnumIsvalid(1);
    	gmstConfigTemplateComponentMst.setUdtEffFrom(new java.sql.Date(System.currentTimeMillis()));
    	
    	gmstConfigTemplateComponentMst.setUnumUnivId(RequestUtility.getUniversityId());
    	gmstConfigTemplateComponentMst.setUnumEntryUid(RequestUtility.getUserId());
    	gmstConfigTemplateComponentMst.setUdtEntryDate(new java.sql.Date(System.currentTimeMillis()));
    	gmstConfigTemplateComponentMst.setUnumTemplCompId(templateComponentRepository.getNextUnumTemplCompId());
    	log.info("univ id {}",gmstConfigTemplateComponentMst.getUnumUnivId());
    	templateComponentRepository.save(gmstConfigTemplateComponentMst);
    	
    	AtomicInteger count=new AtomicInteger(0);
    	
    	templateComponentDetailRepository.saveAll(templateBean.getTemplateComponentDtlsBeanList().stream()
    	    	.map(templateComponentDtlsBean->{
    	    		GmstConfigTemplateComponentDtl gmstConfigTemplateComponentDtl = new GmstConfigTemplateComponentDtl();
    	    		try {
    	    			BeanUtils.copyProperties(templateComponentDtlsBean, gmstConfigTemplateComponentDtl);
    	    			gmstConfigTemplateComponentDtl.setUnumTemplCompItemId(Long.parseLong(gmstConfigTemplateComponentMst.getUnumTemplCompId() +  StringUtility.padLeftZeros(count.incrementAndGet()+"",5)));
    	    		}
    	    		catch (Exception e) {
    	    			throw new RuntimeException(e);
    				}
    	    		return gmstConfigTemplateComponentDtl;
    	    	}).collect(Collectors.toList()));
    	return ServiceResponse.builder()
                .status(1)
                .message(language.saveSuccess("Saved Successfully"))
                .build();
    }
   
}
