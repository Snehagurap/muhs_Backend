package in.cdac.university.committee.service;

import in.cdac.university.committee.bean.CommitteeTypeBean;
import in.cdac.university.committee.repository.CommitteeTypeRepository;
import in.cdac.university.committee.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommitteeTypeService {

    @Autowired
    private CommitteeTypeRepository committeeTypeRepository;

    public List<CommitteeTypeBean> getAllCommitteeType(Integer isValid) {
        return BeanUtils.copyListProperties(
                committeeTypeRepository.findByUnumIsvalidOrderByUstrComtypeFnameAsc(isValid),
                CommitteeTypeBean.class
        );
    }
}
