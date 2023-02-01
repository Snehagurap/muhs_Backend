package in.cdac.university.globalService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cdac.university.globalService.bean.StudentCatMstBean;
import in.cdac.university.globalService.bean.StudentSubcatMstBean;
import in.cdac.university.globalService.entity.GmstStuCatMst;
import in.cdac.university.globalService.entity.GmstStuSubcatMst;
import in.cdac.university.globalService.repository.GmstStuCatMstRepository;
import in.cdac.university.globalService.repository.GmstStuSubcatMstRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.Language;
import in.cdac.university.globalService.util.RequestUtility;

@Service
public class StudentCatMstService {
	
	@Autowired
	private GmstStuCatMstRepository gmstStuCatMstRepository;
	
	@Autowired
	private GmstStuSubcatMstRepository gmstStuSubcatMstRepository;
	
	public List<StudentCatMstBean> getAllCategoryCombo() throws Exception {
		return BeanUtils.copyListProperties(
				gmstStuCatMstRepository.findByUnumIsvalidAndUnumUnivId(1,RequestUtility.getUniversityId()),StudentCatMstBean.class );
	}


	public List<StudentSubcatMstBean> getSubCategoryCombo(Long subCatId) throws Exception {
		return BeanUtils.copyListProperties(
		 gmstStuSubcatMstRepository.findByUnumIsvalidAndUnumUnivIdAndUnumStuCatId(
				1,RequestUtility.getUniversityId(),subCatId
				),StudentSubcatMstBean.class);

	}

}
