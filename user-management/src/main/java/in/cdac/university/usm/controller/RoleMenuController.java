package in.cdac.university.usm.controller;

import in.cdac.university.usm.bean.RoleMenuBean;
import in.cdac.university.usm.service.RoleMenuService;
import in.cdac.university.usm.util.RequestUtility;
import in.cdac.university.usm.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/usm/roleMenu")
public class RoleMenuController {

    @Autowired
    private RoleMenuService roleMenuService;

    @PostMapping("save")
    public @ResponseBody ResponseEntity<?> save(@Valid @RequestBody RoleMenuBean roleMenuBean) throws Exception {
        roleMenuBean.setGdtEntryDate(new Date());
        roleMenuBean.setGnumEntryBy(RequestUtility.getUserId());
        return ResponseHandler.generateResponse(
                roleMenuService.save(roleMenuBean)
        );
    }
}
