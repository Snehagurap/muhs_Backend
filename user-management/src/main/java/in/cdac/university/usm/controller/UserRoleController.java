package in.cdac.university.usm.controller;

import in.cdac.university.usm.bean.UserRoleBean;
import in.cdac.university.usm.service.UserRoleService;
import in.cdac.university.usm.util.ResponseHandler;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/usm/userRole")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    @GetMapping("combo/{userId}")
    @ApiOperation(value = "Get the list of roles Mapped and Not Mapped to the User Id")
    public ResponseEntity<?> getUserRoles(@PathVariable("userId") Long userId) throws IllegalAccessException {
        return ResponseHandler.generateResponse(
                userRoleService.getRoles(userId)
        );
    }

    @PostMapping("save")
    public ResponseEntity<?> save(@Valid @RequestBody UserRoleBean userRoleBean) {
        return ResponseHandler.generateResponse(
                userRoleService.save(userRoleBean)
        );
    }
}
