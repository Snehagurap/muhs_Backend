package in.cdac.university.globalService.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.*;
import java.io.UncheckedIOException;
import java.text.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.cdac.university.globalService.bean.SubjectBean;
import in.cdac.university.globalService.bean.TemplateBean;
import in.cdac.university.globalService.bean.TemplateComponentBean;
import in.cdac.university.globalService.bean.TemplateComponentDtlsBean;
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
import static java.util.Objects.nonNull;
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

	@Transactional
	public ServiceResponse delete(List<Long> idsToDelete) throws Exception {
		Long entryUserId = RequestUtility.getUserId();

		// Getting existing active/ inactive records
		List<GmstConfigTemplateComponentMst> gmstConfigTemplateComponentMstlist = templateComponentRepository
				.findByUnumTempleCompIdInAndUnumIsvalidIn(idsToDelete, List.of(1, 2));

		List<GmstConfigTemplateComponentDtl> gmstConfigTemplateComponentDtlList = templateComponentDetailRepository
				.findByUnumTempleCompIdInAndUnumIsvalidIn(idsToDelete, List.of(1, 2));

		// creating log for exiting active record of template component
		Integer deletedCount = templateComponentRepository.deleteTemplateComponentRecord(idsToDelete);
		if (deletedCount > 0) {
			// creating log for exiting active record of template component item
			templateComponentDetailRepository.deleteTemplateComponentItemRecord(idsToDelete);
		} else {
			return ServiceResponse.builder().status(1).message(language.deleteError("Component")).build();
		}

		gmstConfigTemplateComponentMstlist.forEach(component -> {
			component.setUnumIsvalid(0);
			component.setUdtEntryDate(new Date());
			component.setUnumEntryUid(entryUserId);
		});
		templateComponentRepository.saveAll(gmstConfigTemplateComponentMstlist);

		gmstConfigTemplateComponentDtlList.forEach(item -> {
			item.setUnumIsvalid(0);
			item.setUdtEntryDate(new Date());
			item.setUnumEntryUid(entryUserId);
		});
		templateComponentDetailRepository.saveAll(gmstConfigTemplateComponentDtlList);

		return ServiceResponse.builder().status(1).message(language.deleteSuccess("Component")).build();

	}

	public ServiceResponse saveAndUpdateTemplateComponent(TemplateComponentBean templateBean, boolean isSave)
			throws Exception {
		GmstConfigTemplateComponentMst gmstConfigTemplateComponentMst = new GmstConfigTemplateComponentMst();
		BeanUtils.copyProperties(templateBean, gmstConfigTemplateComponentMst);

		if (!isSave) {
			Integer updatedRow = templateComponentRepository
					.updateTemplateComponentRecord(templateBean.getUnumTempleCompId());
			if (updatedRow > 0) {
				templateComponentDetailRepository.updateTemplateComponentItemRecord(templateBean.getUnumTempleCompId());
			} else {
				log.info("No Active Component found to Update for ID : {} ", templateBean.getUnumTempleCompId());
				return ServiceResponse.builder().status(1).message(language.updateError("Component")).build();
			}

		} else {
			gmstConfigTemplateComponentMst.setUnumTempleCompId(templateComponentRepository.getNextUnumTempleCompId());
		}

		gmstConfigTemplateComponentMst.setUnumIsvalid(1);
		gmstConfigTemplateComponentMst.setUdtEffFrom(new Date());

		gmstConfigTemplateComponentMst.setUnumUnivId(RequestUtility.getUniversityId());
		gmstConfigTemplateComponentMst.setUnumEntryUid(RequestUtility.getUserId());
		gmstConfigTemplateComponentMst.setUdtEntryDate(new Date());
 
		log.info("univ id {}", gmstConfigTemplateComponentMst.getUnumUnivId());
		templateComponentRepository.save(gmstConfigTemplateComponentMst);

		int count = 1;// new AtomicInteger(0);

		List<GmstConfigTemplateComponentDtl> gmstConfigTemplateComponentDtlEntityList = new ArrayList<>();
		for (TemplateComponentDtlsBean templateComponentDtls : templateBean.getTemplateComponentDtlsBeanList()) {
			GmstConfigTemplateComponentDtl gmstConfigTemplateComponentDtl = new GmstConfigTemplateComponentDtl();
			gmstConfigTemplateComponentDtlEntityList.add(gmstConfigTemplateComponentDtl);

			BeanUtils.copyProperties(templateComponentDtls, gmstConfigTemplateComponentDtl);
			gmstConfigTemplateComponentDtl.setUnumTempleCompItemId(Long.parseLong(
					gmstConfigTemplateComponentMst.getUnumTempleCompId() + StringUtility.padLeftZeros(count++ + "", 5)));
			gmstConfigTemplateComponentDtl.setUnumTempleCompId(gmstConfigTemplateComponentMst.getUnumTempleCompId());
			gmstConfigTemplateComponentDtl.setUnumIsvalid(1);
			gmstConfigTemplateComponentDtl.setUnumEntryUid(RequestUtility.getUserId());
			gmstConfigTemplateComponentDtl.setUdtEffFrom(new Date());
			gmstConfigTemplateComponentDtl.setUnumUnivId(RequestUtility.getUniversityId());
			gmstConfigTemplateComponentDtl.setUdtEntryDate(new Date());
		}
		templateComponentDetailRepository.saveAll(gmstConfigTemplateComponentDtlEntityList);
		if (!isSave)
			return ServiceResponse.builder().status(1).message(language.updateSuccess("Component")).build();
		else
			return ServiceResponse.builder().status(1).message(language.saveSuccess("Component")).build();
	}

	public TemplateComponentBean getCompDetailsByParentID(Long unumTempleCompId) throws Exception {
		TemplateComponentBean templatecompBean = new TemplateComponentBean();
		GmstConfigTemplateComponentMst gmstConfigTemplateComponentMst =  templateComponentRepository.findByUnumTempleCompIdAndUnumIsvalid(unumTempleCompId,1);
		if(nonNull(gmstConfigTemplateComponentMst))
		{
			BeanUtils.copyProperties(templateComponentRepository.findByUnumTempleCompIdAndUnumIsvalid(unumTempleCompId,1), templatecompBean);
			List<TemplateComponentDtlsBean> gmstdtlsbeanList = BeanUtils.copyListProperties(templateComponentDetailRepository.findByUnumTempleCompIdAndUnumIsvalid(unumTempleCompId,1), TemplateComponentDtlsBean.class);
			templatecompBean.setTemplateComponentDtlsBeanList(gmstdtlsbeanList);
		}
		return templatecompBean;
	}

	public List<TemplateComponentBean> getUnumTempleCompIds() throws Exception {

		return BeanUtils.copyListProperties(
				templateComponentRepository.findUnumTempleCompIds(RequestUtility.getUniversityId()),
				TemplateComponentBean.class);
	}

}
