package in.cdac.university.usm.service;

import in.cdac.university.usm.bean.RoleBean;
import in.cdac.university.usm.repository.RoleRepository;
import in.cdac.university.usm.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<RoleBean> getAllRoles() {
        return BeanUtils.copyListProperties(roleRepository.findByGblIsvalidOrderByGstrRoleNameAsc(1), RoleBean.class);
    }

    public int saveRole(RoleBean roleBean) {
        return 0;
    }
}
