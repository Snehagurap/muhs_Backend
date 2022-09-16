package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.CollegeBean;
import in.cdac.university.globalService.repository.CollegeRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.RequestUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollegeService {

    @Autowired
    private CollegeRepository collegeRepository;

    public List<CollegeBean> getColleges() throws Exception {
        int universityId = RequestUtility.getUniversityId();
        return BeanUtils.copyListProperties(
                collegeRepository.findColleges(universityId),
                CollegeBean.class
        );
    }
}
