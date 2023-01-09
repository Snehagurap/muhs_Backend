package in.cdac.university.usm.controller;

import in.cdac.university.usm.bean.UserBean;
import in.cdac.university.usm.service.UserService;
import in.cdac.university.usm.util.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/usm/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("listPage/{status}/{universityId}")
    public @ResponseBody ResponseEntity<?> getListPage(@PathVariable("status") Integer status, @PathVariable("universityId") Integer universityId)
            throws IllegalAccessException {
        return ResponseHandler.generateOkResponse(
                ListPageUtility.generateListPageData(userService.getAllUsersByUniversityId(universityId, status)));
    }

    @GetMapping("{userId}")
    public @ResponseBody ResponseEntity<?> get(@PathVariable("userId") Long userId) {
        return ResponseHandler.generateResponse(userService.get(userId));
    }

    @GetMapping("category/{categoryId}")
    public @ResponseBody ResponseEntity<?> getUsersForCategory(@PathVariable("categoryId") Integer categoryId) throws IllegalAccessException {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(userService.getUsersForCategory(categoryId))
        );
    }
    @PostMapping("save")
    public @ResponseBody ResponseEntity<?> save(@Valid @RequestBody UserBean userBean) throws Exception {
        userBean.setGnumEntryBy(RequestUtility.getUserId());
        userBean.setGdtEntrydate(new Date());
        userBean.setGnumIslock(0);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userBean.setGstrPassword(encoder.encode(userBean.getGstrPassword()));
        return ResponseHandler.generateResponse(userService.save(userBean));
    }

    @PostMapping("update")
    public @ResponseBody ResponseEntity<?> update(@Valid @RequestBody UserBean userBean) throws Exception {
        userBean.setGnumLstmodBy(RequestUtility.getUserId());
        userBean.setGdtLstmodDate(new Date());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userBean.setGstrPassword(encoder.encode(userBean.getGstrPassword()));
        return ResponseHandler.generateResponse(userService.update(userBean));
    }

    @PostMapping("delete")
    @ApiOperation(value = "Delete the users based on the ids passed.", response = ServiceResponse.class)
    public @ResponseBody ResponseEntity<?> delete(@RequestBody String[] idsToDelete) throws Exception {
        UserBean userBean = new UserBean();
        userBean.setIdsToDelete(idsToDelete);
        userBean.setGdtLstmodDate(new Date());
        userBean.setGnumLstmodBy(RequestUtility.getUserId());
        return ResponseHandler.generateResponse(userService.delete(userBean));
    }

    //user list on the basis of universityId and userCategoryID
    @GetMapping("combo/{userCategoryId}/{universityId}")
    public @ResponseBody ResponseEntity<?> getUserCombo(@PathVariable("userCategoryId") Integer userCategoryId,
    @PathVariable("universityId") Integer universityId) throws IllegalAccessException {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(userService.getUserCombo(userCategoryId,universityId))
        );
    }
}
