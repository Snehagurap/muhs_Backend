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

    @GetMapping("/intermediate/{level}/{moduleId}")
    public @ResponseBody ResponseEntity<?> getIntermediateMenu(@PathVariable("level") Integer level, @PathVariable("moduleId") Integer moduleId) {
        MenuBean menuBean = new MenuBean();
        menuBean.setGnumMenuLevel(level);
        menuBean.setGnumModuleId(moduleId);
        return ResponseHandler.generateOkResponse(
                menuService.getIntermediateMenu(menuBean)
        );
    }

    @GetMapping("root")
    public @ResponseBody ResponseEntity<?> getRootMenuCombo() throws IllegalAccessException {
        List<ComboBean> comboList = new ArrayList<>();
        comboList.add(new ComboBean("1", "Admin"));
        comboList.add(new ComboBean("2", "Reports"));
        comboList.add(new ComboBean("3", "Services"));
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

    @PatchMapping("update")
    @ApiOperation("Update Menu name or Menu URL only")
    public @ResponseBody ResponseEntity<?> update(@Valid @RequestBody MenuBean menuBean) throws Exception {
        return ResponseHandler.generateResponse(
                menuService.update(menuBean)
        );
    }

    @DeleteMapping("delete")
    public @ResponseBody ResponseEntity<?> delete(@RequestBody String[] idsToDelete) throws Exception {
        MenuBean menuBean = new MenuBean();
        menuBean.setIdsToDelete(idsToDelete);
        return ResponseHandler.generateResponse(menuService.delete(menuBean));
    }
}
