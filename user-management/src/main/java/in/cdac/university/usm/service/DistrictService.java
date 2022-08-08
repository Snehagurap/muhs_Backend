package in.cdac.university.usm.service;

import in.cdac.university.usm.bean.DistrictBean;
import in.cdac.university.usm.bean.StateBean;
import in.cdac.university.usm.repository.DistrictRepository;
import in.cdac.university.usm.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictService {

    @Autowired
    private DistrictRepository districtRepository;

    public List<DistrictBean> getAllDistricts(Integer stateCode) {
        return BeanUtils.copyListProperties(districtRepository.findAllByGnumIsvalidAndGnumStatecodeOrderByStrDistNameAsc(1, stateCode), DistrictBean.class);
    }
}
