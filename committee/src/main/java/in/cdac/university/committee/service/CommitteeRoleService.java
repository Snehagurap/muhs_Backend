package in.cdac.university.committee.service;

import in.cdac.university.committee.bean.CommitteeRoleBean;
import in.cdac.university.committee.repository.CommitteeRoleRepository;
import in.cdac.university.committee.util.BeanUtils;
import in.cdac.university.committee.util.RequestUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommitteeRoleService {

    @Autowired
    private CommitteeRoleRepository committeeRoleRepository;

    public List<CommitteeRoleBean> getAllCommitteeRoles() throws Exception {
        return BeanUtils.copyListProperties(
                committeeRoleRepository.getAllCommitteeRoles(
                        RequestUtility.getUniversityId()
                ),
                CommitteeRoleBean.class
        );
    }
}
