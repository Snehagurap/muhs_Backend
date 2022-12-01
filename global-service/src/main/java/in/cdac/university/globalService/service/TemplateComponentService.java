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
		return BeanUtils.copyListProperties(templateComponentRepository
				.findByUnumIsvalidAndUnumUnivIdOrderByUnumCompDisplayOrderAsc(1, RequestUtility.getUniversityId()),
				TemplateComponentBean.class);
	}
	
	@Transactional
	public ServiceResponse save(TemplateComponentBean templateBean) throws Exception {
		return saveAndUpdateTemplateComponent(templateBean, true);
	}

	@Transactional
	public ServiceResponse update(TemplateComponentBean templateBean) throws Exception {
		return saveAndUpdateTemplateComponent(templateBean, false);
	}
	
	public ServiceResponse saveAndUpdateTemplateComponent(TemplateComponentBean templateBean, boolean isSave) throws Exception {
		GmstConfigTemplateComponentMst gmstConfigTemplateComponentMst = new GmstConfigTemplateComponentMst();
		BeanUtils.copyProperties(templateBean, gmstConfigTemplateComponentMst);
		
		if (!isSave) {
			Integer updatedRow = templateComponentRepository.updateTemplateComponentRecord(templateBean.getUnumTemplCompId());
			if(updatedRow > 0) {
				templateComponentDetailRepository.updateTemplateComponentItemRecord(templateBean.getUnumTemplCompId());
			} else {
				log.info("No Active Component found to Update for ID : {} ", templateBean.getUnumTemplCompId());
				return ServiceResponse.builder().status(1).message(language.updateError("Component")).build();
			}
			
		} else {
			gmstConfigTemplateComponentMst.setUnumTemplCompId(templateComponentRepository.getNextUnumTemplCompId());
		}

		gmstConfigTemplateComponentMst.setUnumIsvalid(1);
		gmstConfigTemplateComponentMst.setUdtEffFrom(new java.sql.Date(System.currentTimeMillis()));

		gmstConfigTemplateComponentMst.setUnumUnivId(RequestUtility.getUniversityId());
		gmstConfigTemplateComponentMst.setUnumEntryUid(RequestUtility.getUserId());
		gmstConfigTemplateComponentMst.setUdtEntryDate(new java.sql.Date(System.currentTimeMillis()));
		
		
		log.info("univ id {}", gmstConfigTemplateComponentMst.getUnumUnivId());
		templateComponentRepository.save(gmstConfigTemplateComponentMst);

		AtomicInteger count = new AtomicInteger(0);

		templateComponentDetailRepository
				.saveAll(templateBean.getTemplateComponentDtlsBeanList().stream().map(templateComponentDtlsBean -> {
					GmstConfigTemplateComponentDtl gmstConfigTemplateComponentDtl = new GmstConfigTemplateComponentDtl();
					try {
						BeanUtils.copyProperties(templateComponentDtlsBean, gmstConfigTemplateComponentDtl);
						gmstConfigTemplateComponentDtl.setUnumTemplCompItemId(
								Long.parseLong(gmstConfigTemplateComponentMst.getUnumTemplCompId()
										+ StringUtility.padLeftZeros(count.incrementAndGet() + "", 5)));
						gmstConfigTemplateComponentDtl.setUnumTemplCompId(gmstConfigTemplateComponentMst.getUnumTemplCompId());
						gmstConfigTemplateComponentDtl.setUnumIsvalid(1);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
					return gmstConfigTemplateComponentDtl;
				}).collect(Collectors.toList()));
		
		return ServiceResponse.builder().status(1).message(language.updateSuccess("Component")).build();
	}

	@Transactional
	public ServiceResponse delete(List<Long> idsToDelete) {

		Integer deletedCount = templateComponentRepository.deleteTemplateComponentRecord(idsToDelete);
		if (deletedCount > 0) {
			templateComponentDetailRepository.deleteTemplateComponentItemRecord(idsToDelete);
		} else {
			return ServiceResponse.builder().status(1).message(language.deleteError("Component")).build();
		}
		return ServiceResponse.builder().status(1).message(language.deleteSuccess("Component")).build();
	}

}
