package in.cdac.university.usm.service;

import in.cdac.university.usm.bean.ComboBean;
import in.cdac.university.usm.bean.MappedComboBean;
import in.cdac.university.usm.bean.RoleBean;
import in.cdac.university.usm.bean.UserRoleBean;
import in.cdac.university.usm.entity.UmmtRoleMst;
import in.cdac.university.usm.entity.UmmtUserRoleMst;
import in.cdac.university.usm.repository.RoleRepository;
import in.cdac.university.usm.repository.UserRoleRepository;
import in.cdac.university.usm.util.BeanUtils;
import in.cdac.university.usm.util.ComboUtility;
import in.cdac.university.usm.util.Language;
import in.cdac.university.usm.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private Language language;

    public ServiceResponse getRoles(Long userId) throws IllegalAccessException {
        List<UmmtRoleMst> allRoleList = roleRepository.findByGblIsvalidOrderByGstrRoleNameAsc(1);
        List<UmmtUserRoleMst> mappedRoles = userRoleRepository.findByGnumUserIdAndGblIsvalid(userId, 1);

        List<ComboBean> mappedRoleCombo = ComboUtility.generateComboData(
                BeanUtils.copyListProperties(mappedRoles, UserRoleBean.class)
        );
        Set<Integer> mappedRoleIds = mappedRoles.stream().map(UmmtUserRoleMst::getGnumRoleId).collect(Collectors.toSet());

        List<ComboBean> notMappedRoleCombo = ComboUtility.generateComboData(
                BeanUtils.copyListProperties(
                        allRoleList.stream().filter(role -> !mappedRoleIds.contains(role.getGnumRoleId())).toList(),
                        RoleBean.class
                )
        );
        MappedComboBean mappedComboBean = new MappedComboBean(mappedRoleCombo, notMappedRoleCombo);

        return ServiceResponse.successObject(mappedComboBean);
    }

    public ServiceResponse save(UserRoleBean userRoleBean) {
        // Delete existing roles
        List<UmmtUserRoleMst> mappedUserRoles = userRoleRepository.findByGnumUserIdAndGblIsvalid(userRoleBean.getGnumUserId(), 1);
        userRoleRepository.deleteAllInBatch(mappedUserRoles);

        if (userRoleBean.getMappedRoles() != null && userRoleBean.getMappedRoles().length > 0) {
            mappedUserRoles.clear();
            for (int i=0;i<userRoleBean.getMappedRoles().length;++i) {
                UmmtUserRoleMst userRole = new UmmtUserRoleMst();
                userRole.setGnumUserId(userRoleBean.getGnumUserId());
                userRole.setGnumRoleId(userRoleBean.getMappedRoles()[i]);
                userRole.setGblIsvalid(1);
                userRole.setGnumIsDefault(0);
                mappedUserRoles.add(userRole);
            }
            userRoleRepository.saveAll(mappedUserRoles);
        }

        return ServiceResponse.builder()
                .status(1)
                .message(language.saveSuccess("Role Mapping"))
                .build();
    }
}
