package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.AffiliationStatusBean;
import in.cdac.university.globalService.repository.AffiliationStatusRepository;
import in.cdac.university.globalService.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AffiliationStatusService {

    @Autowired
    private AffiliationStatusRepository affiliationStatusRepository;

    public List<AffiliationStatusBean> getAffiliationStatus() {
        return BeanUtils.copyListProperties(
                affiliationStatusRepository.getAffiliationStatus(),
                AffiliationStatusBean.class
        );
    }
}
