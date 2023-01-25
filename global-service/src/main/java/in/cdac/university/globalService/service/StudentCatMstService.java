package in.cdac.university.globalService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cdac.university.globalService.repository.GmstStuCatMstRepository;
import in.cdac.university.globalService.util.Language;

@Service
public class StudentCatMstService {
	
	@Autowired
	private GmstStuCatMstRepository gmstStuCatMstRepository;
	
	@Autowired
	private Language language;

}
