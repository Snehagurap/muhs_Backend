package in.cdac.university.usm.controller;

import in.cdac.university.usm.bean.ComboBean;
import in.cdac.university.usm.bean.UserCategoryBean;
import in.cdac.university.usm.service.UserCategoryService;
import in.cdac.university.usm.util.ComboUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usm/userCategory")
public class UserCategoryController {

    @Autowired
    private UserCategoryService userCategoryService;

    @GetMapping("list")
    public @ResponseBody List<ComboBean> getAllUserCategories() throws IllegalAccessException {
        return ComboUtility.generateComboData(userCategoryService.getAllUserCategories());
    }
}
