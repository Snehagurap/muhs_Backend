package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.service.DepartmentService;
import in.cdac.university.globalService.util.ComboUtility;
import in.cdac.university.globalService.util.ListPageUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/global/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/listPage/{status}")
    public ResponseEntity<?> getListPage(@PathVariable("status") Integer isValid) throws IllegalAccessException {
        return ResponseHandler.generateOkResponse(
                ListPageUtility.generateListPageData(departmentService.departmentList(isValid))
        );
    }

    @GetMapping("/combo")
    public ResponseEntity<?> getDepartmentCombo() throws IllegalAccessException {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(departmentService.departmentList(1))
        );
    }
}
