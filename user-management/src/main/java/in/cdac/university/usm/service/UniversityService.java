package in.cdac.university.usm.service;

import in.cdac.university.usm.bean.UniversityBean;
import in.cdac.university.usm.repository.UniversityRepository;
import in.cdac.university.usm.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UniversityService {

    @Autowired
    private UniversityRepository universityRepository;

    public List<UniversityBean> getAllUniversities(Integer isValid) {
        return BeanUtils.copyListProperties(
                universityRepository.findAllByUnumIsvalidOrderByUstrUnivFname(isValid),
                UniversityBean.class
        );
    }
}
