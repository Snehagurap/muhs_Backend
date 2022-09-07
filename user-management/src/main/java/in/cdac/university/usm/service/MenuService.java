package in.cdac.university.usm.service;

import in.cdac.university.usm.bean.IntermediateMenuBean;
import in.cdac.university.usm.bean.MenuBean;
import in.cdac.university.usm.entity.UmmtMenuMst;
import in.cdac.university.usm.repository.MenuRepository;
import in.cdac.university.usm.util.BeanUtils;
import in.cdac.university.usm.util.Language;
import in.cdac.university.usm.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private Language language;

    public List<MenuBean> getList(Integer moduleId, Integer status) {
        return BeanUtils.copyListProperties(
                menuRepository.findByGnumModuleIdAndGnumIsvalid(moduleId, status),
                MenuBean.class
        );
    }

    public ServiceResponse getMenu(Integer menuId) {
        if (menuId == null) {
            return ServiceResponse.errorResponse(language.mandatory("Menu Id is mandatory"));
        }

        Optional<UmmtMenuMst> menuMstOptional = menuRepository.findByGnumMenuIdAndGnumIsvalidIn(
                menuId, List.of(1, 2)
        );

        if (menuMstOptional.isEmpty()) {
            return ServiceResponse.errorResponse(language.notFoundForId("Menu", menuId));
        }

        MenuBean menuBean = BeanUtils.copyProperties(menuMstOptional.get(), MenuBean.class);

        return ServiceResponse.builder()
                .status(1)
                .responeObject(menuBean)
                .build();
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
            menuBean.setGnumParentId(menuBean.getRootMenuId());
        }

        // Duplicate check
        Optional<UmmtMenuMst> menuMstOptional = menuRepository.findByGstrMenuNameIgnoreCaseAndGnumIsvalidIn(menuBean.getGstrMenuName(), List.of(1, 2));
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

        return ServiceResponse.builder()
                .status(1)
                .message(language.deleteSuccess("Menu(s)"))
                .build();
    }

    public List<IntermediateMenuBean> getIntermediateMenu(MenuBean menuBean) {
        List<UmmtMenuMst> menuMsts = menuRepository.findByGnumMenuLevelAndGnumModuleIdAndGnumIsvalid(menuBean.getGnumMenuLevel(), menuBean.getGnumModuleId(), 1);

        return menuMsts.stream()
                .map(menu -> new IntermediateMenuBean(menu.getGnumMenuId().toString(), menu.getGstrMenuName(), menu.getGstrUrl()))
                .collect(Collectors.toList());
    }
}
