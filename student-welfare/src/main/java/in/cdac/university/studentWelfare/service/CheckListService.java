package in.cdac.university.studentWelfare.service;

import in.cdac.university.studentWelfare.bean.CheckListBean;
import in.cdac.university.studentWelfare.bean.CheckListItems;
import in.cdac.university.studentWelfare.bean.HeadClassCheckList;
import in.cdac.university.studentWelfare.entity.GbltConfigApplicationChklstDataDtl;
import in.cdac.university.studentWelfare.repository.CheckListRepository;
import in.cdac.university.studentWelfare.repository.TemplateRepository;
import in.cdac.university.studentWelfare.util.BeanUtils;
import in.cdac.university.studentWelfare.util.Language;
import in.cdac.university.studentWelfare.util.RequestUtility;
import in.cdac.university.studentWelfare.util.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class CheckListService {

	@Autowired
    private TemplateRepository templateRepository;

	@Autowired
	private CheckListRepository checkListRepository;

    @Autowired
    private Language language;
    
    @Transactional
    public ServiceResponse saveChecklist(CheckListBean checkListBean) throws Exception {
		Long unumTempleId = checkListBean.getUnumTempleId();
		templateRepository.updateChecklistData(unumTempleId);

		for(HeadClassCheckList headClassCheckList: checkListBean.getHeadClass()){
			for(CheckListItems checkListItem: headClassCheckList.getCheckListItems()) {
				if (checkListItem.getUstrChecklistItemName() != null && !checkListItem.getUstrChecklistItemName().isEmpty() &&
						checkListItem.getUnumChecklistItemOrderno() != null) {
					templateRepository.updateChecklist(unumTempleId,
							headClassCheckList.getHeaderId(),
							headClassCheckList.getComponentId(),
							checkListItem.getUnumTempleItemId(),
							checkListBean.getUstrChecklistName(),
							checkListBean.getUnumChecklistId(),
							checkListItem.getUstrChecklistItemName(),
							checkListItem.getUnumChecklistItemOrderno());
				}
			}
		}
        return ServiceResponse.builder().status(1).message(language.saveSuccess("Template")).build();
    }

	public ServiceResponse getCheckListByApplicationId(Long applicationId) throws Exception {
		List<GbltConfigApplicationChklstDataDtl> gbltChecklstDataDtlList = checkListRepository.findByUnumApplicationIdAndUnumIsvalidAndUnumUnivId(
				applicationId, 1, RequestUtility.getUniversityId()
		);

		return ServiceResponse.successObject(BeanUtils.copyListProperties(gbltChecklstDataDtlList, CheckListBean.class));
	}

	public List<GbltConfigApplicationChklstDataDtl> saveApplicationChecklist(List<GbltConfigApplicationChklstDataDtl> checklist) {
		return checkListRepository.saveAll(checklist);
	}
}
