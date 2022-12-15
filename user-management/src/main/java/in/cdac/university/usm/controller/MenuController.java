package in.cdac.university.usm.controller;

import in.cdac.university.usm.bean.ComboBean;
import in.cdac.university.usm.bean.MenuBean;
import in.cdac.university.usm.service.MenuService;
import in.cdac.university.usm.util.ComboUtility;
import in.cdac.university.usm.util.ListPageUtility;
import in.cdac.university.usm.util.RequestUtility;
import in.cdac.university.usm.util.ResponseHandler;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/usm/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/listPage/{moduleId}/{status}")
    public @ResponseBody ResponseEntity<?> listPage(@PathVariable("moduleId") Integer moduleId, @PathVariable("status") Integer status) throws IllegalAccessException {
        return ResponseHandler.generateOkResponse(
                ListPageUtility.generateListPageData(menuService.getList(moduleId, status))
        );
    }

    @GetMapping("{menuId}")
    public @ResponseBody ResponseEntity<?> getMenu(@PathVariable("menuId") Integer menuId) {
        return ResponseHandler.generateResponse(
                menuService.getMenu(menuId)
        );
    }

    @GetMapping("/intermediate/{level}/{moduleId}/{rootMenu}")
    public @ResponseBody ResponseEntity<?> getIntermediateMenu(@PathVariable("level") Integer level, @PathVariable("moduleId") Integer moduleId, @PathVariable("rootMenu") Integer rootMenuId) {
        MenuBean menuBean = new MenuBean();
        menuBean.setGnumMenuLevel(level - 1);
        menuBean.setGnumModuleId(moduleId);
        menuBean.setRootMenuId(rootMenuId);
        return ResponseHandler.generateOkResponse(
                menuService.getIntermediateMenu(menuBean)
        );
    }

    @GetMapping("/mapping/{moduleId}/{rootMenu}/{level}/{roleId}")
    public @ResponseBody ResponseEntity<?> getMenuForMapping(@PathVariable("moduleId") Integer moduleId,
                                                             @PathVariable("rootMenu") Integer rootMenuId,
                                                             @PathVariable("level") Integer level,
                                                             @PathVariable("roleId") Integer roleId) {
        MenuBean menuBean = new MenuBean();
        menuBean.setGnumModuleId(moduleId);
        menuBean.setRootMenuId(rootMenuId);
        menuBean.setGnumMenuLevel(level + 1);
        return ResponseHandler.generateOkResponse(
                menuService.getIntermediateMenu(menuBean, roleId)
        );
    }

    @GetMapping("/mapped/{moduleId}/{rootMenu}/{level}/{roleId}")
    public @ResponseBody ResponseEntity<?> getMappedMenu(@PathVariable("moduleId") Integer moduleId,
                                                             @PathVariable("rootMenu") Integer rootMenuId,
                                                             @PathVariable("level") Integer level,
                                                             @PathVariable("roleId") Integer roleId) {
        MenuBean menuBean = new MenuBean();
        menuBean.setGnumModuleId(moduleId);
        menuBean.setRootMenuId(rootMenuId);
        menuBean.setGnumMenuLevel(level + 1);
        return ResponseHandler.generateOkResponse(
                menuService.getIntermediateMappedMenu(menuBean, roleId)
        );
    }

    @GetMapping("/mapping/{moduleId}/{rootMenu}/{level}/{parentMenuId}/{roleId}")
    public @ResponseBody ResponseEntity<?> getMenuForMapping(@PathVariable("moduleId") Integer moduleId,
                                                             @PathVariable("rootMenu") Integer rootMenuId,
                                                             @PathVariable("level") Integer level,
                                                             @PathVariable("parentMenuId") Integer parentMenuId,
                                                             @PathVariable("roleId") Integer roleId) {
        MenuBean menuBean = new MenuBean();
        menuBean.setGnumModuleId(moduleId);
        menuBean.setRootMenuId(rootMenuId);
        menuBean.setGnumMenuLevel(level + 1);
        menuBean.setGnumParentId(parentMenuId);
        return ResponseHandler.generateOkResponse(
                menuService.getIntermediateMenuWithParent(menuBean, roleId)
        );
    }

    @GetMapping("/mapped/{moduleId}/{rootMenu}/{level}/{parentMenuId}/{roleId}")
    public @ResponseBody ResponseEntity<?> getAlreadyMappedMenu(@PathVariable("moduleId") Integer moduleId,
                                                             @PathVariable("rootMenu") Integer rootMenuId,
                                                             @PathVariable("level") Integer level,
                                                             @PathVariable("parentMenuId") Integer parentMenuId,
                                                             @PathVariable("roleId") Integer roleId) {
        MenuBean menuBean = new MenuBean();
        menuBean.setGnumModuleId(moduleId);
        menuBean.setRootMenuId(rootMenuId);
        menuBean.setGnumMenuLevel(level + 1);
        menuBean.setGnumParentId(parentMenuId);
        return ResponseHandler.generateOkResponse(
                menuService.getMappedMenus(menuBean, roleId)
        );
    }

    @GetMapping("root")
    public @ResponseBody ResponseEntity<?> getRootMenuCombo() throws IllegalAccessException {
        List<ComboBean> comboList = new ArrayList<>();
        comboList.add(new ComboBean("3", "Services"));
        comboList.add(new ComboBean("2", "Reports"));
        comboList.add(new ComboBean("1", "Admin"));
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(comboList)
        );
    }

    @PostMapping("save")
    public @ResponseBody ResponseEntity<?> save(@Valid @RequestBody MenuBean menuBean) throws Exception {
        menuBean.setGnumEntryBy(RequestUtility.getUserId());
        menuBean.setGdtEntryDate(new Date());
        menuBean.setGnumIsvalid(1);
        return ResponseHandler.generateResponse(
                menuService.save(menuBean)
        );
    }

    // Patch mapping replaced by POST
@PostMapping("update")
    @ApiOperation("Update Menu name or Menu URL only")
    public @ResponseBody ResponseEntity<?> update(@RequestBody MenuBean menuBean) throws Exception {
        return ResponseHandler.generateResponse(
                menuService.update(menuBean)
        );
    }

    // Delete mapping replaced by POST
@PostMapping("delete")
    public @ResponseBody ResponseEntity<?> delete(@RequestBody String[] idsToDelete) throws Exception {
        MenuBean menuBean = new MenuBean();
        menuBean.setIdsToDelete(idsToDelete);
        return ResponseHandler.generateResponse(menuService.delete(menuBean));
    }

    @GetMapping("mapped/user")
    public @ResponseBody ResponseEntity<?> getMenusMappedWithUser() throws Exception {
        Long userId = RequestUtility.getUserId();
        return ResponseHandler.generateResponse(
                menuService.getMenusMappedWithUser(userId)
        );
    }

    @GetMapping("mapped/url/user")
    public @ResponseBody ResponseEntity<?> getMenusUrlsMappedWithUser() throws Exception {
        Long userId = RequestUtility.getUserId();
        return ResponseHandler.generateResponse(
                menuService.getMenusUrlsMappedWithUser(userId)
        );
    }
}
