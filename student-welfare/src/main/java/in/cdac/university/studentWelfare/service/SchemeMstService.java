package in.cdac.university.studentWelfare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cdac.university.studentWelfare.util.RequestUtility;
import in.cdac.university.studentWelfare.util.ServiceResponse;
import in.cdac.university.studentWelfare.repository.GmstUswSchemeMstRepository;
import in.cdac.university.studentWelfare.util.BeanUtils;
import in.cdac.university.studentWelfare.bean.SchemeMstBean;
import in.cdac.university.studentWelfare.controller.SchemeMstController;
import in.cdac.university.studentWelfare.entity.GmstUswSchemeMst;

@Service
public class SchemeMstService {
	
	@Autowired
	private GmstUswSchemeMstRepository gmstUswSchemeMstRepository;
	
	
	public SchemeMstBean getStudentDetailsByScheme(Long schemeNo) throws Exception {
		List<GmstUswSchemeMst> gmstUswSchemeMst = gmstUswSchemeMstRepository.findByUnumIsvalidAndUnumUnivIdAndUnumSchemeId(
																					1,RequestUtility.getUniversityId(),schemeNo);
		return 	BeanUtils.copyProperties(gmstUswSchemeMst,SchemeMstBean.class);
	}

}
