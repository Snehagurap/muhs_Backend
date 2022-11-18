package in.cdac.university.usm.controller;

import in.cdac.university.usm.bean.CustomUser;
import in.cdac.university.usm.service.UserLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usm/log")
@Slf4j
public class UserLogController {

    @Autowired
    private UserLogService logoutService;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody CustomUser customUser) {
        return ResponseEntity.ok(logoutService.logUserLoginDetails(customUser));
    }

    @PostMapping("logout")
    public ResponseEntity<?> logout(@RequestBody CustomUser customUser) {
        log.debug("Logging out user: {}", customUser.getUserId());
        return ResponseEntity.ok(logoutService.logout(customUser));
    }
}
