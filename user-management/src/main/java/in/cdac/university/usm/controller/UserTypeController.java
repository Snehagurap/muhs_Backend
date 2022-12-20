package in.cdac.university.usm.controller;

import in.cdac.university.usm.bean.UserTypeBean;
import in.cdac.university.usm.service.UserTypeService;
import in.cdac.university.usm.util.ComboUtility;
import in.cdac.university.usm.util.ListPageUtility;
import in.cdac.university.usm.util.RequestUtility;
import in.cdac.university.usm.util.ResponseHandler;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/usm/userType")
public class UserTypeController {

    @Autowired
    private UserTypeService userTypeService;

    @GetMapping("/listPage/{isValid}/{userCategory}")
    public @ResponseBody ResponseEntity<?> getListPage(@PathVariable("isValid") Integer isValid, @PathVariable("userCategory") Integer userCategoryId) throws IllegalAccessException {
        return ResponseHandler.generateOkResponse(
                ListPageUtility.generateListPageData(userTypeService.getAllUserTypeList(isValid, userCategoryId))
        );
    }

    @GetMapping("/combo/{userCategory}")
    @ApiOperation("Get the combo for User Type on the basis of User Category")
    @ApiResponses(
            @ApiResponse(code = 200, message = "ID: UserTypeID^RoleID^RoleName^DefaultDatasetID^DatasetName")
    )
    public @ResponseBody ResponseEntity<?> combo(@PathVariable("userCategory") Integer userCategoryId) throws IllegalAccessException {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(userTypeService.getAllUserTypeList(1, userCategoryId))
        );
    }

    @GetMapping("/{userTypeId}")
    public @ResponseBody ResponseEntity<?> getUserType(@PathVariable("userTypeId") Integer userTypeId) {
        return ResponseHandler.generateResponse(userTypeService.getUserType(userTypeId));
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody UserTypeBean userTypeBean) throws Exception {
        log.info("Saving User Type: " + userTypeBean.toString());
        userTypeBean.setGnumEntryBy(RequestUtility.getUserId());
        userTypeBean.setGdtEntryDate(new Date());
        return ResponseHandler.generateResponse(userTypeService.save(userTypeBean));
    }

    // Patch mapping replaced by POST
    @PostMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody UserTypeBean userTypeBean) throws Exception {
        log.info("Updating User Type: " + userTypeBean.toString());
        userTypeBean.setGnumEntryBy(RequestUtility.getUserId());
        userTypeBean.setGdtEntryDate(new Date());
        return ResponseHandler.generateResponse(userTypeService.update(userTypeBean));
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody String[] idsToDelete) throws Exception {
        log.info("Deleting User Type: " + Arrays.toString(idsToDelete));
        UserTypeBean userTypeBean = new UserTypeBean();
        userTypeBean.setIdsToDelete(idsToDelete);
        userTypeBean.setGnumEntryBy(RequestUtility.getUserId());
        userTypeBean.setGdtEntryDate(new Date());
        return ResponseHandler.generateResponse(userTypeService.delete(userTypeBean));
    }
}
