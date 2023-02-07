package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.SalutationBean;
import in.cdac.university.globalService.repository.SalutationRepository;
import in.cdac.university.globalService.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalutationService {

    @Autowired
    private SalutationRepository salutationRepository;

    public List<SalutationBean> getAllSalutations() {
        return BeanUtils.copyListProperties(
                salutationRepository.getAllSalutations(),
                SalutationBean.class);
    }
}
