package in.cdac.university.usm.controller;

import in.cdac.university.usm.bean.RoleBean;
import in.cdac.university.usm.service.RoleService;
import in.cdac.university.usm.util.ComboUtility;
import in.cdac.university.usm.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usm/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("list")
    public @ResponseBody ResponseEntity<?> getList() throws IllegalAccessException {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(roleService.getAllRoles())
        );
    }

    @PostMapping("save")
    public @ResponseBody ResponseEntity<?> saveRole(RoleBean roleBean) {
        int roleId = roleService.saveRole(roleBean);
        if (roleId == 0) {
            return ResponseHandler.generateErrorResponse("Unable to save Role");
        } else {
            return ResponseHandler.generateOkResponse("Role saved successfully");
        }
    }
}
