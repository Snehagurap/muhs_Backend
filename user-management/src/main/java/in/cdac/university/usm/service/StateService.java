package in.cdac.university.usm.service;

import in.cdac.university.usm.bean.StateBean;
import in.cdac.university.usm.repository.StateRepository;
import in.cdac.university.usm.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateService {

    @Autowired
    private StateRepository stateRepository;

    public List<StateBean> getAllStates(Integer countryCode) {
        return BeanUtils.copyListProperties(stateRepository.findAllByGnumIsvalidAndGnumCountrycodeOrderByGstrStatenameAsc(1, countryCode), StateBean.class);
    }
}
