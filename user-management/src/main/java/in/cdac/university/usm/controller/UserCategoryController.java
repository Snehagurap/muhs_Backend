package in.cdac.university.usm.controller;

import in.cdac.university.usm.service.UserCategoryService;
import in.cdac.university.usm.util.ComboUtility;
import in.cdac.university.usm.util.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/usm/userCategory")
public class UserCategoryController {

    @Autowired
    private UserCategoryService userCategoryService;

    @GetMapping("list")
    public @ResponseBody ResponseEntity<?> getList(@RequestHeader String userId) throws IllegalAccessException {
        log.info("user id " + userId);
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(userCategoryService.getAllUserCategories())
        );
    }
}