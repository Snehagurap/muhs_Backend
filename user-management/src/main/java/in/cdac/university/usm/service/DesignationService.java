package in.cdac.university.usm.service;

import in.cdac.university.usm.bean.CountryBean;
import in.cdac.university.usm.bean.DesignationBean;
import in.cdac.university.usm.repository.DesignationRepository;
import in.cdac.university.usm.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesignationService {

    @Autowired
    private DesignationRepository designationRepository;

    public List<DesignationBean> getAllDesignations() {
        return BeanUtils.copyListProperties(designationRepository.findAllByGnumIsvalidOrderByGstrDesignationNameAsc(1), DesignationBean.class);
    }
}
