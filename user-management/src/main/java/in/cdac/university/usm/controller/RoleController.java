package in.cdac.university.usm.controller;

import in.cdac.university.usm.bean.RoleBean;
import in.cdac.university.usm.service.RoleService;
import in.cdac.university.usm.util.ComboUtility;
import in.cdac.university.usm.util.ListPageUtility;
import in.cdac.university.usm.util.RequestUtility;
import in.cdac.university.usm.util.ResponseHandler;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@Slf4j
@RequestMapping("/usm/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("combo")
    public @ResponseBody ResponseEntity<?> getList() throws IllegalAccessException {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(roleService.getAllRoles())
        );
    }

    @GetMapping("listPage/{isValid}")
    public @ResponseBody ResponseEntity<?> getListPage(@PathVariable("isValid") Integer isValid) throws IllegalAccessException {
        return ResponseHandler.generateOkResponse(
                ListPageUtility.generateListPageData(roleService.getRolesListPage(isValid))
        );
    }

    @GetMapping("{roleId}")
    public @ResponseBody ResponseEntity<?> getRole(@PathVariable("roleId") Integer roleId) {
        return ResponseHandler.generateResponse(roleService.getRole(roleId));
    }

    @GetMapping("module/{moduleId}")
    @ApiOperation("Get Role Combo on the basis of Module Id")
    public @ResponseBody ResponseEntity<?> getRoleComboForModule(@PathVariable("moduleId") Integer moduleId) throws IllegalAccessException {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(roleService.getRoleComboForModule(moduleId))
        );
    }

    @PostMapping("save")
    public @ResponseBody ResponseEntity<?> save(@Valid @RequestBody RoleBean roleBean) throws Exception {
        roleBean.setGnumEntryBy(RequestUtility.getUserId());
        log.info("Saving Role: " + roleBean);
        return ResponseHandler.generateResponse(roleService.saveRole(roleBean));
    }

    // Patch mapping replaced by POST
    @PostMapping("update")
    public @ResponseBody ResponseEntity<?> update(@Valid @RequestBody RoleBean roleBean) throws Exception {
        roleBean.setGnumEntryBy(RequestUtility.getUserId());
        roleBean.setGdtEntrydate(new Date());
        log.info("Updating Role: " + roleBean);
        return ResponseHandler.generateResponse(roleService.update(roleBean));
    }

    @PostMapping("delete")
    public @ResponseBody ResponseEntity<?> delete(@RequestBody String[] idsToDelete) throws Exception {
        RoleBean roleBean = new RoleBean();
        roleBean.setIdsToDelete(idsToDelete);
        roleBean.setGnumEntryBy(RequestUtility.getUserId());
        roleBean.setGdtEntrydate(new Date());
        return ResponseHandler.generateResponse(roleService.delete(roleBean));
    }
}
