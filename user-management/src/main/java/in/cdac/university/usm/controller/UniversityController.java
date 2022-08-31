package in.cdac.university.usm.controller;

import in.cdac.university.usm.service.UniversityService;
import in.cdac.university.usm.util.ComboUtility;
import in.cdac.university.usm.util.ListPageUtility;
import in.cdac.university.usm.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usm/university")
public class UniversityController {

    @Autowired
    private UniversityService universityService;

    @GetMapping("listPage/{status}")
    public @ResponseBody ResponseEntity<?> getListPage(@PathVariable("status") Integer status)
            throws IllegalAccessException {
        return ResponseHandler.generateOkResponse(
                ListPageUtility.generateListPageData(universityService.getAllUniversities(status)));
    }

    @GetMapping("combo")
    public @ResponseBody ResponseEntity<?> getCombo()
            throws IllegalAccessException {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(universityService.getAllUniversities(1)));
    }
}
