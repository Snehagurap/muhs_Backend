package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.TemplateComponentBean;
import in.cdac.university.globalService.bean.TemplateComponentDtlsBean;
import in.cdac.university.globalService.bean.TemplateComponentItemBean;
import in.cdac.university.globalService.bean.TemplateDetailBean;
import in.cdac.university.globalService.bean.TemplateHeaderBean;
import in.cdac.university.globalService.bean.TemplateHeaderSubHeaderBean;
import in.cdac.university.globalService.bean.TemplateItemBean;
import in.cdac.university.globalService.bean.TemplateSubHeaderBean;
import in.cdac.university.globalService.entity.GmstConfigTemplateComponentMst;
import in.cdac.university.globalService.entity.GmstConfigTemplateDtl;
import in.cdac.university.globalService.entity.GmstConfigTemplateHeaderMst;
import in.cdac.university.globalService.entity.GmstConfigTemplateItemMst;
import in.cdac.university.globalService.exception.ApplicationException;
import in.cdac.university.globalService.repository.TemplateComponentDetailRepository;
import in.cdac.university.globalService.repository.TemplateDetailRepository;
import in.cdac.university.globalService.repository.TemplateHeaderRepository;
import in.cdac.university.globalService.repository.TemplateItemRepository;
import in.cdac.university.globalService.repository.TemplateSubHeaderRepository;
import in.cdac.university.globalService.util.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@Service
@Slf4j
public class TemplateHeaderService {

	@Autowired
	private Language language;

	@Autowired
	private TemplateHeaderRepository templateHeaderRepository;

	@Autowired
	private TemplateSubHeaderRepository templateSubHeaderRepository;

	@Autowired
	private TemplateDetailRepository templateDetailRepository;

	@Autowired
	private TemplateItemRepository templateItemRepository;

	public List<TemplateHeaderBean> listPageData() throws Exception {
		return BeanUtils.copyListProperties(templateHeaderRepository
				.findByUnumIsvalidAndUnumUnivIdOrderByUstrTempleHeadCodeAsc(1, RequestUtility.getUniversityId()),
				TemplateHeaderBean.class);
	}

	public ServiceResponse getTemplateHeaderById(Long headerId) throws Exception {
		Optional<GmstConfigTemplateHeaderMst> gmstConfigTemplateHeaderMstOptional = templateHeaderRepository
				.findByUnumTempleHeadIdAndUnumIsvalidAndUnumUnivId(headerId, 1, RequestUtility.getUniversityId());

		if (gmstConfigTemplateHeaderMstOptional.isEmpty()) {
			return ServiceResponse.errorResponse(language.notFoundForId("Template Header", headerId));
		}

		TemplateHeaderBean templateHeaderBean = BeanUtils.copyProperties(gmstConfigTemplateHeaderMstOptional.get(),
				TemplateHeaderBean.class);

		return ServiceResponse.builder().status(1).responseObject(templateHeaderBean).build();
	}

	@Transactional
	public ServiceResponse save(TemplateHeaderBean templateHeaderBean) {
		// Duplicate Check
		List<GmstConfigTemplateHeaderMst> gmstConfigTemplateHeaderMstList = templateHeaderRepository
				.findByUstrHeadPrintTextIgnoreCaseAndUnumIsvalidAndUnumUnivId(templateHeaderBean.getUstrHeadPrintText(),
						1, templateHeaderBean.getUnumUnivId());

		if (!gmstConfigTemplateHeaderMstList.isEmpty()) {
			return ServiceResponse
					.errorResponse(language.duplicate("Header", templateHeaderBean.getUstrHeadPrintText()));
		}

		// Generate new header id
		GmstConfigTemplateHeaderMst gmstConfigTemplateHeaderMst = BeanUtils.copyProperties(templateHeaderBean,
				GmstConfigTemplateHeaderMst.class);
		gmstConfigTemplateHeaderMst.setUnumTempleHeadId(templateHeaderRepository.getNextId());
		templateHeaderRepository.save(gmstConfigTemplateHeaderMst);

		return ServiceResponse.builder().status(1).message(language.saveSuccess("Template Header")).build();
	}

	@Transactional
	public ServiceResponse update(TemplateHeaderBean templateHeaderBean) {
		if (templateHeaderBean.getUnumTempleHeadId() == null) {
			return ServiceResponse.errorResponse(language.mandatory("Header Id"));
		}

		// Duplicate Check
		List<GmstConfigTemplateHeaderMst> gmstConfigTemplateHeaderMstList = templateHeaderRepository
				.findByUnumTempleHeadIdNotAndUstrHeadPrintTextIgnoreCaseAndUnumIsvalidAndUnumUnivId(
						templateHeaderBean.getUnumTempleHeadId(), templateHeaderBean.getUstrHeadPrintText(), 1,
						templateHeaderBean.getUnumUnivId());

		if (!gmstConfigTemplateHeaderMstList.isEmpty()) {
			return ServiceResponse
					.errorResponse(language.duplicate("Header", templateHeaderBean.getUstrHeadPrintText()));
		}

		// Create Log
		int noOfRowsAffected = templateHeaderRepository.createLog(List.of(templateHeaderBean.getUnumTempleHeadId()));
		if (noOfRowsAffected == 0) {
			throw new ApplicationException(language.notFoundForId("Header", templateHeaderBean.getUnumTempleHeadId()));
		}

		// Save New Data
		GmstConfigTemplateHeaderMst gmstConfigTemplateHeaderMst = BeanUtils.copyProperties(templateHeaderBean,
				GmstConfigTemplateHeaderMst.class);
		templateHeaderRepository.save(gmstConfigTemplateHeaderMst);

		return ServiceResponse.builder().status(1).message(language.updateSuccess("Header")).build();
	}

	@Transactional
	public ServiceResponse delete(TemplateHeaderBean templateHeaderBean, Long[] idsToDelete) {
		if (idsToDelete == null || idsToDelete.length == 0) {
			return ServiceResponse.errorResponse(language.mandatory("Header Id"));
		}

		List<GmstConfigTemplateHeaderMst> gmstConfigTemplateHeaderMstList = templateHeaderRepository
				.findByUnumTempleHeadIdInAndUnumIsvalidAndUnumUnivId(List.of(idsToDelete), 1,
						templateHeaderBean.getUnumUnivId());

		if (gmstConfigTemplateHeaderMstList.size() != idsToDelete.length) {
			return ServiceResponse.errorResponse(language.notFoundForId("Header", Arrays.toString(idsToDelete)));
		}

		// Create Log
		int noOfRowsAffected = templateHeaderRepository.createLog(List.of(idsToDelete));
		if (noOfRowsAffected != idsToDelete.length) {
			throw new ApplicationException(language.deleteError("Header"));
		}

		gmstConfigTemplateHeaderMstList.forEach(header -> {
			header.setUnumIsvalid(0);
			header.setUdtEntryDate(templateHeaderBean.getUdtEntryDate());
			header.setUnumEntryUid(header.getUnumEntryUid());
		});

		templateHeaderRepository.saveAll(gmstConfigTemplateHeaderMstList);

		return ServiceResponse.builder().status(1).message(language.deleteSuccess("Header")).build();
	}

	public ServiceResponse getTemplateHeaderCombo() throws Exception {
		List<GmstConfigTemplateHeaderMst> allHeaders = templateHeaderRepository
				.findAllHeaders(RequestUtility.getUniversityId());
		return ServiceResponse.successObject(
				ComboUtility.generateComboData(BeanUtils.copyListProperties(allHeaders, TemplateHeaderBean.class)));
	}

	public List<TemplateSubHeaderBean> getSubHeaderComboID(Long unumTempleHeadId) throws Exception {
		return BeanUtils.copyListProperties(
				templateSubHeaderRepository.findByunumTempleHeadId(unumTempleHeadId, RequestUtility.getUniversityId()),
				TemplateSubHeaderBean.class);

	}

	public ServiceResponse getItemsByHeaderSubHeaderComponents(
			@Valid TemplateHeaderSubHeaderBean templateHeaderSubHeaderBean) {

		List<Long> unumTemplCompIdlist = new ArrayList<Long>();
		templateHeaderSubHeaderBean.getCompItemBean().forEach(requestbean->{
			unumTemplCompIdlist.add(requestbean.getUnumTemplCompId());
			List<TemplateItemBean> itemsList = BeanUtils.copyListProperties(
					templateItemRepository.findAllByUnumTemplItemIdAndUnumIsvalid(requestbean.getUnumTemplCompId(),1),
					TemplateItemBean.class);
			log.info("itemsList {}",itemsList);
			requestbean.setItems(itemsList);
		});

		return ServiceResponse.successObject(templateHeaderSubHeaderBean);

	}
}
