package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.DesignationBean;
import in.cdac.university.globalService.repository.DesignationRepository;
import in.cdac.university.globalService.util.BeanUtils;
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
