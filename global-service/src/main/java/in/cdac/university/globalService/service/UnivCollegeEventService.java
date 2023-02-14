package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.ComboBean;
import in.cdac.university.globalService.bean.FacultyBean;
import in.cdac.university.globalService.bean.UnivCollegeEventBean;
import in.cdac.university.globalService.repository.UnivCollegeEventRespository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.RequestUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnivCollegeEventService {

    @Autowired
    UnivCollegeEventRespository univCollegeEventRespository;

    public List<UnivCollegeEventBean> getCollegeEventCombo() throws Exception {
        Integer universityId = RequestUtility.getUniversityId();
        return BeanUtils.copyListProperties(
                univCollegeEventRespository.getAllCollegeEventCombo(universityId),
                UnivCollegeEventBean.class
        );
    }
}
