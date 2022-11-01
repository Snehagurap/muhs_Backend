package in.cdac.university.usm.service;

import in.cdac.university.usm.bean.RoleMenuBean;
import in.cdac.university.usm.entity.UmmtRoleMenuMst;
import in.cdac.university.usm.repository.RoleMenuRepository;
import in.cdac.university.usm.util.Language;
import in.cdac.university.usm.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RoleMenuService {

    @Autowired
    private RoleMenuRepository roleMenuRepository;

    @Autowired
    private Language language;

    public ServiceResponse save(RoleMenuBean roleMenuBean) {
        // Get already mapped menus, Delete if present
        List<UmmtRoleMenuMst> roleMenuMstList = roleMenuRepository.findByGnumRoleIdAndGnumModuleId(roleMenuBean.getGnumRoleId(), roleMenuBean.getGnumModuleId());
//        if (!roleMenuMstList.isEmpty())
//            roleMenuRepository.deleteAllInBatch(roleMenuMstList);

        // Map new Menus
        if (roleMenuBean.getGnumMenuId() != null && roleMenuBean.getGnumMenuId().length > 0) {
            roleMenuMstList.clear();
            for (int i=0;i<roleMenuBean.getGnumMenuId().length;++i) {
                UmmtRoleMenuMst roleMenuMst = new UmmtRoleMenuMst();
                roleMenuMst.setGnumRoleId(roleMenuBean.getGnumRoleId());
                roleMenuMst.setGnumMenuId(roleMenuBean.getGnumMenuId()[i]);
                roleMenuMst.setGnumModuleId(roleMenuBean.getGnumModuleId());
                roleMenuMst.setGdtEntryDate(roleMenuBean.getGdtEntryDate());
                roleMenuMst.setGnumIsvalid(1);
                roleMenuMst.setGnumDisplayOrder(i);
                roleMenuMst.setGnumEntryBy(roleMenuBean.getGnumEntryBy());
                roleMenuMstList.add(roleMenuMst);
            }

            roleMenuRepository.saveAll(roleMenuMstList);
        }

        return ServiceResponse.builder()
                .status(1)
                .message(language.saveSuccess("Role Menu details"))
                .build();
    }
}
