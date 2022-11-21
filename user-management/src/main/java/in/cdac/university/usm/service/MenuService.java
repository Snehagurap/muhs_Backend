package in.cdac.university.usm.service;

import in.cdac.university.usm.bean.IntermediateMenuBean;
import in.cdac.university.usm.bean.MenuBean;
import in.cdac.university.usm.bean.MenuToDisplay;
import in.cdac.university.usm.entity.UmmtMenuMst;
import in.cdac.university.usm.entity.UmmtRoleMenuMst;
import in.cdac.university.usm.entity.UmmtUserRoleMst;
import in.cdac.university.usm.repository.MenuRepository;
import in.cdac.university.usm.repository.RoleMenuRepository;
import in.cdac.university.usm.repository.UserRoleRepository;
import in.cdac.university.usm.util.BeanUtils;
import in.cdac.university.usm.util.Language;
import in.cdac.university.usm.util.RequestUtility;
import in.cdac.university.usm.util.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@Transactional
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private Language language;

    @Autowired
    private RoleMenuRepository roleMenuRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    public List<MenuBean> getList(Integer moduleId, Integer status) {
        return BeanUtils.copyListProperties(
                menuRepository.findByGnumModuleIdAndGnumIsvalid(moduleId, status),
                MenuBean.class
        );
    }

    public ServiceResponse getMenu(Integer menuId) {
        if (menuId == null) {
            return ServiceResponse.errorResponse(language.mandatory("Menu Id"));
        }

        Optional<UmmtMenuMst> menuMstOptional = menuRepository.findByGnumMenuIdAndGnumIsvalidIn(
                menuId, List.of(1, 2)
        );

        if (menuMstOptional.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Menu", menuId));
        }

        MenuBean menuBean = BeanUtils.copyProperties(menuMstOptional.get(), MenuBean.class);

        return ServiceResponse.successObject(menuBean);
    }

    public ServiceResponse save(MenuBean menuBean) {
        if (menuBean.getGnumMenuLevel() == 1) {
            menuBean.setGnumParentId(0);
            menuBean.setGstrMenuName(
                    switch (menuBean.getRootMenuId()) {
                        case 1: yield "Admin";
                        case 2: yield "Reports";
                        case 3: yield "Services";
                        default: yield "";
                    }
            );
        } else if (menuBean.getGnumMenuLevel() == 2) {
            Optional<UmmtMenuMst> menuMstOptional = menuRepository.findByGnumModuleIdAndRootMenuIdAndGnumIsvalidAndGnumParentId(menuBean.getGnumModuleId(), menuBean.getRootMenuId(), 1, 0);
            if (menuMstOptional.isEmpty()) {
                return ServiceResponse.errorResponse(language.mandatory("Parent Menu"));
            }
            Integer parentId = menuMstOptional.get().getGnumMenuId();

            menuBean.setGnumParentId(parentId);
        }
        if (menuBean.getGstrMenuName() == null || menuBean.getGstrMenuName().isBlank()) {
            return ServiceResponse.errorResponse(language.mandatory("Menu Name"));
        }

        // Duplicate check
        Optional<UmmtMenuMst> menuMstOptional = menuRepository.findByGstrMenuNameIgnoreCaseAndGnumModuleIdAndGnumIsvalidIn(
                menuBean.getGstrMenuName(), menuBean.getGnumModuleId(), List.of(1, 2));
        if (menuMstOptional.isPresent()) {
            return ServiceResponse.errorResponse(language.duplicate("Menu Name", menuBean.getGstrMenuName()));
        }

        UmmtMenuMst menuMst = BeanUtils.copyProperties(menuBean, UmmtMenuMst.class);
        menuMst = menuRepository.save(menuMst);
        int status = 0;
        String message;
        if (menuMst.getGnumMenuId() != null) {
            status = 1;
            message = language.saveSuccess("Menu");
        } else {
            message = language.saveError("Menu");
        }
        return ServiceResponse.builder()
                .status(status)
                .message(message)
                .build();
    }

    public ServiceResponse update(MenuBean menuBean) {
        if (menuBean.getGnumMenuId() == null) {
            return ServiceResponse.errorResponse(language.mandatory("MenuId"));
        }

        // Check if menu exists
        Optional<UmmtMenuMst> menuMst = menuRepository.findById(menuBean.getGnumMenuId());
        if (menuMst.isEmpty() || menuMst.get().getGnumIsvalid() != 1) {
            return ServiceResponse.errorResponse(language.notFoundForId("Menu", menuBean.getGnumMenuId()));
        }

        // Duplicate check
        Optional<UmmtMenuMst> menuMstOptional = menuRepository.findByGstrMenuNameIgnoreCaseAndGnumMenuIdNotAndGnumIsvalidIn(menuBean.getGstrMenuName(), menuBean.getGnumMenuId(), List.of(1, 2));
        if (menuMstOptional.isPresent()) {
            return ServiceResponse.errorResponse(language.duplicate("Menu Name", menuBean.getGstrMenuName()));
        }

        UmmtMenuMst menuMstToUpdate = menuMst.get();
        menuMstToUpdate.setGstrMenuName(menuBean.getGstrMenuName());
        menuMstToUpdate.setGstrUrl(menuBean.getGstrUrl());
        menuMstToUpdate = menuRepository.save(menuMstToUpdate);

        int status = 0;
        String message;
        if (menuMstToUpdate.getGstrMenuName().equals(menuBean.getGstrMenuName())) {
            status = 1;
            message = language.updateSuccess("Menu");
        } else {
            message = language.updateError("Menu");
        }

        return ServiceResponse.builder()
                .status(status)
                .message(message)
                .build();
    }

    public ServiceResponse delete(MenuBean menuBean) {
        if (menuBean.getIdsToDelete() == null || menuBean.getIdsToDelete().length == 0) {
            return ServiceResponse.errorResponse(language.mandatory("MenuId"));
        }

        List<Integer> idsToDelete = Stream.of(menuBean.getIdsToDelete())
                .map(Integer::valueOf)
                .toList();

        // Check if menu exists
        List<UmmtMenuMst> menuMst = menuRepository.findAllById(idsToDelete);
        if (menuMst.isEmpty() || menuMst.size() != idsToDelete.size()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Menu", Arrays.toString(menuBean.getIdsToDelete())));
        }

        menuMst.forEach(
                menu -> menu.setGnumIsvalid(0)
        );
        menuRepository.saveAll(menuMst);

        // Delete Role Menu Mapping
        List<UmmtRoleMenuMst> roleMenuMstList = roleMenuRepository.findByGnumMenuIdIn(idsToDelete);
        if (!roleMenuMstList.isEmpty()) {
            roleMenuRepository.deleteAllInBatch(roleMenuMstList);
        }

        return ServiceResponse.builder()
                .status(1)
                .message(language.deleteSuccess("Menu(s)"))
                .build();
    }

    public List<IntermediateMenuBean> getIntermediateMenu(MenuBean menuBean) {

        List<UmmtMenuMst> menuMsts = menuRepository.findByGnumMenuLevelAndGnumModuleIdAndGnumIsvalidAndRootMenuId(
                menuBean.getGnumMenuLevel(), menuBean.getGnumModuleId(), 1, menuBean.getRootMenuId());

        return menuMsts.stream()
                .map(menu -> new IntermediateMenuBean(menu.getGnumMenuId().toString(), menu.getGstrMenuName(), menu.getGstrUrl()))
                .collect(Collectors.toList());
    }

    private Set<Integer> getMenusMappedWithRole(MenuBean menuBean, Integer roleId) {
        return roleMenuRepository.findByGnumRoleIdAndGnumModuleId(roleId, menuBean.getGnumModuleId())
                .stream()
                .map(UmmtRoleMenuMst::getGnumMenuId)
                .collect(Collectors.toSet());
    }

    private List<IntermediateMenuBean> getMenusWithPredicate(MenuBean menuBean, Predicate<UmmtMenuMst> menuMstPredicate) {

        List<UmmtMenuMst> menuMsts = menuRepository.findByGnumMenuLevelAndGnumModuleIdAndGnumIsvalidAndRootMenuId(
                menuBean.getGnumMenuLevel(), menuBean.getGnumModuleId(), 1, menuBean.getRootMenuId());

        return menuMsts.stream()
                .filter(menuMstPredicate)
                .map(menu -> new IntermediateMenuBean(menu.getGnumMenuId().toString(), menu.getGstrMenuName(), menu.getGstrUrl()))
                .collect(Collectors.toList());
    }

    public List<IntermediateMenuBean> getIntermediateMenu(MenuBean menuBean, Integer roleId) {
        // Already Mapped Menus
        Set<Integer> mappedMenus = getMenusMappedWithRole(menuBean, roleId);
        Predicate<UmmtMenuMst> menuMstPredicate = menu -> (menu.getGstrUrl() == null || menu.getGstrUrl().isBlank()) ||  !mappedMenus.contains(menu.getGnumMenuId());
        return getMenusWithPredicate(menuBean, menuMstPredicate);
    }

    public List<IntermediateMenuBean> getIntermediateMappedMenu(MenuBean menuBean, Integer roleId) {
        // Already Mapped Menus
        Set<Integer> mappedMenus = getMenusMappedWithRole(menuBean, roleId);
        Predicate<UmmtMenuMst> menuMstPredicate = menu -> (menu.getGstrUrl() != null || !menu.getGstrUrl().isBlank()) &&  mappedMenus.contains(menu.getGnumMenuId());
        return getMenusWithPredicate(menuBean, menuMstPredicate);
    }

    private List<IntermediateMenuBean> getIntermediateMenuBeans(MenuBean menuBean, Predicate<UmmtMenuMst> menuMstPredicate) {
        List<UmmtMenuMst> menuMsts = menuRepository.findByGnumMenuLevelAndGnumModuleIdAndGnumIsvalidAndRootMenuIdAndGnumParentId(
                menuBean.getGnumMenuLevel(), menuBean.getGnumModuleId(), 1, menuBean.getRootMenuId(), menuBean.getGnumParentId());

        return menuMsts.stream()
                .filter(menuMstPredicate)
                .map(menu -> new IntermediateMenuBean(menu.getGnumMenuId().toString(), menu.getGstrMenuName(), menu.getGstrUrl()))
                .collect(Collectors.toList());
    }

    public List<IntermediateMenuBean> getIntermediateMenuWithParent(MenuBean menuBean, Integer roleId) {
        // Already Mapped Menus
        Set<Integer> mappedMenus = getMenusMappedWithRole(menuBean, roleId);
        Predicate<UmmtMenuMst> menuMstPredicate = menu -> (menu.getGstrUrl() == null || menu.getGstrUrl().isBlank()) || !mappedMenus.contains(menu.getGnumMenuId());
        return getIntermediateMenuBeans(menuBean, menuMstPredicate);
    }

    public List<IntermediateMenuBean> getMappedMenus(MenuBean menuBean, Integer roleId) {
        // Already Mapped Menus
        Set<Integer> mappedMenus = getMenusMappedWithRole(menuBean, roleId);
        Predicate<UmmtMenuMst> menuMstPredicate = menu -> (menu.getGstrUrl() == null || menu.getGstrUrl().isBlank()) || mappedMenus.contains(menu.getGnumMenuId());
        return getIntermediateMenuBeans(menuBean, menuMstPredicate);
    }

    public ServiceResponse getMenusMappedWithUser(Long userId) throws Exception {
        // Get User Category
        int userCategory = RequestUtility.getUserCategory();
        log.debug("User Category: {}", userCategory);
        List<UmmtUserRoleMst> mappedRoles;
        if (userCategory == 5) {
            // Draft Applicant
            mappedRoles = new ArrayList<>();
            UmmtUserRoleMst ummtUserRoleMst = new UmmtUserRoleMst();
            ummtUserRoleMst.setGnumRoleId(0);
            mappedRoles.add(ummtUserRoleMst);
        } else if (userCategory == 6) {
            // Applicant
            // Get Roles Mapped with User
            mappedRoles = new ArrayList<>();
            UmmtUserRoleMst ummtUserRoleMst = new UmmtUserRoleMst();
            ummtUserRoleMst.setGnumRoleId(-1);
            mappedRoles.add(ummtUserRoleMst);
        } else {
            mappedRoles = userRoleRepository.findByGnumUserIdAndGblIsvalid(userId, 1);
        }

        List<UmmtMenuMst> mappedMenus = new ArrayList<>();
        Map<Integer, Integer> menuOrder = new HashMap<>();
        if (!mappedRoles.isEmpty()) {
            List<UmmtRoleMenuMst> mappedMenusWithRole = roleMenuRepository.findByGnumRoleIdInAndGnumIsvalidOrderByGnumDisplayOrder(
                    mappedRoles.stream().map(UmmtUserRoleMst::getGnumRoleId).toList(),
                    1
            );

            List<Integer> menuIds = new ArrayList<>();
            for (UmmtRoleMenuMst roleMenuMst: mappedMenusWithRole) {
                menuOrder.put(roleMenuMst.getGnumMenuId(), roleMenuMst.getGnumDisplayOrder());
                menuIds.add(roleMenuMst.getGnumMenuId());
            }

            mappedMenus = menuRepository.findByGnumMenuIdInAndGnumIsvalid(menuIds, 1);
        }
        List<String> menuRoutes = mappedMenus.stream().map(UmmtMenuMst::getGstrUrl).toList();

        List<MenuToDisplay> menusToDisplay = processMenus(BeanUtils.copyListProperties(mappedMenus, MenuToDisplay.class));
        if (menusToDisplay.size() == 1) {
            menusToDisplay = menusToDisplay.get(0).getSubMenuList();
        }
        if (!menusToDisplay.isEmpty())
            menusToDisplay.get(0).setMenuRoutes(menuRoutes);

        sortMenus(menusToDisplay, menuOrder);

        return ServiceResponse.successObject(menusToDisplay);
    }

    private void sortMenus(List<MenuToDisplay> menusToDisplay, Map<Integer, Integer> menuOrder) {
        for (MenuToDisplay menuToDisplay: menusToDisplay) {
            List<MenuToDisplay> subMenuList = menuToDisplay.getSubMenuList();
            if (subMenuList == null || subMenuList.size() == 0) {
                return;
            } else {
                subMenuList.sort(Comparator.comparing(o -> menuOrder.getOrDefault(o.getGnumMenuId(), 0)));
            }
            sortMenus(subMenuList, menuOrder);
        }
    }

    private List<MenuToDisplay> processMenus(List<MenuToDisplay> menus) {
        List<MenuToDisplay> rootMenus = BeanUtils.copyListProperties(
                menuRepository.findAllRootMenus(), MenuToDisplay.class
        );

        boolean isSubMenuProcessRequired;
        int counter = 0;
        Set<Integer> rootMenusToProcess = rootMenus.stream().map(MenuToDisplay::getGnumMenuId).collect(Collectors.toSet());
        do {
            isSubMenuProcessRequired = processSubMenus(rootMenusToProcess, rootMenus, menus, counter);
            menus = filterMenusWithoutSubMenus(rootMenus);
            rootMenusToProcess = rootMenus
                    .stream()
                    .filter(menu -> menu.getSubMenuList() == null || menu.getSubMenuList().size() == 0)
                    .map(MenuToDisplay::getGnumMenuId)
                    .collect(Collectors.toSet());

            counter++;
        } while (isSubMenuProcessRequired);

        rootMenus.removeIf(menu -> menu.getGnumParentId() != 0 || menu.getSubMenuList() == null || menu.getSubMenuList().isEmpty());
        return rootMenus;
    }

    private boolean processSubMenus(Set<Integer> rootMenusToProcess, List<MenuToDisplay> rootMenuList, List<MenuToDisplay> menusList, int counter) {
        if (counter >= 4)
            return false;
        boolean isSubMenuProcessed = false;
        for (MenuToDisplay menu : menusList) {
            for (MenuToDisplay rootMenu: rootMenuList) {
                if (rootMenusToProcess.contains(rootMenu.getGnumMenuId())) {
                    if (menu.getGnumParentId().equals(rootMenu.getGnumMenuId())) {
                        List<MenuToDisplay> subMenuList = rootMenu.getSubMenuList();
                        if (subMenuList == null)
                            subMenuList = new ArrayList<>();
                        subMenuList.add(menu);
                        rootMenu.setSubMenuList(subMenuList);
                        isSubMenuProcessed = true;
                    }
                }
            }
        }
        return isSubMenuProcessed;
    }

    private List<MenuToDisplay> filterMenusWithoutSubMenus(List<MenuToDisplay> menus) {
        return menus.stream()
                .filter(menu -> menu.getSubMenuList() != null)
                .toList();
    }

    public ServiceResponse getMenusUrlsMappedWithUser(Long userId) {
        List<UmmtUserRoleMst> mappedRoles = userRoleRepository.findByGnumUserIdAndGblIsvalid(userId, 1);
        List<UmmtMenuMst> mappedMenus = new ArrayList<>();
        if (!mappedRoles.isEmpty()) {
            List<UmmtRoleMenuMst> mappedMenusWithRole = roleMenuRepository.findByGnumRoleIdInAndGnumIsvalidOrderByGnumDisplayOrder(
                    mappedRoles.stream().map(UmmtUserRoleMst::getGnumRoleId).toList(),
                    1
            );

            mappedMenus = menuRepository.findByGnumMenuIdInAndGnumIsvalid(
                    mappedMenusWithRole.stream().map(UmmtRoleMenuMst::getGnumMenuId).toList(),
                    1
            );
        }
        return ServiceResponse.successObject(BeanUtils.copyListProperties(mappedMenus, MenuToDisplay.class));
    }
}
