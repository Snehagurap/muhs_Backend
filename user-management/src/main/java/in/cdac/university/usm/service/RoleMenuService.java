package in.cdac.university.usm.service;

import in.cdac.university.usm.bean.RoleMenuBean;
import in.cdac.university.usm.entity.UmmtRoleMenuMst;
import in.cdac.university.usm.repository.RoleMenuRepository;
import in.cdac.university.usm.util.Language;
import in.cdac.university.usm.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class RoleMenuService {

    @Autowired
    private RoleMenuRepository roleMenuRepository;

    @Autowired
    private Language language;

    public ServiceResponse save(RoleMenuBean roleMenuBean) {
        // Get already mapped menus, Delete if present
        Set<Integer> menuIds = Set.of(roleMenuBean.getGnumMenuId());
        List<UmmtRoleMenuMst> alreadySavedMenus = roleMenuRepository.findByGnumRoleIdAndGnumModuleId(roleMenuBean.getGnumRoleId(), roleMenuBean.getGnumModuleId());

        List<UmmtRoleMenuMst> menusToDelete = new ArrayList<>();
        Set<Integer> setAlreadySavedMenus = new HashSet<>();
        for (UmmtRoleMenuMst menu: alreadySavedMenus) {
            if (!menuIds.contains(menu.getGnumMenuId())) {
                menusToDelete.add(menu);
            }
            setAlreadySavedMenus.add(menu.getGnumMenuId());
        }

        if (!menusToDelete.isEmpty())
            roleMenuRepository.deleteAllInBatch(menusToDelete);

        List<UmmtRoleMenuMst> menusToAdd = new ArrayList<>();

        // Map new Menus
        if (roleMenuBean.getGnumMenuId() != null && roleMenuBean.getGnumMenuId().length > 0) {
            for (int i=0;i<roleMenuBean.getGnumMenuId().length;++i) {
                if (setAlreadySavedMenus.contains(roleMenuBean.getGnumMenuId()[i]))
                    continue;
                UmmtRoleMenuMst roleMenuMst = new UmmtRoleMenuMst();
                roleMenuMst.setGnumRoleId(roleMenuBean.getGnumRoleId());
                roleMenuMst.setGnumMenuId(roleMenuBean.getGnumMenuId()[i]);
                roleMenuMst.setGnumModuleId(roleMenuBean.getGnumModuleId());
                roleMenuMst.setGdtEntryDate(roleMenuBean.getGdtEntryDate());
                roleMenuMst.setGnumIsvalid(1);
                roleMenuMst.setGnumDisplayOrder(i);
                roleMenuMst.setGnumEntryBy(roleMenuBean.getGnumEntryBy());
                menusToAdd.add(roleMenuMst);
            }

            if (!menusToAdd.isEmpty())
                roleMenuRepository.saveAll(menusToAdd);
        }

        return ServiceResponse.builder()
                .status(1)
                .message(language.saveSuccess("Role Menu details"))
                .build();
    }
}
