package in.cdac.university.usm.controller;

import in.cdac.university.usm.service.DesignationService;
import in.cdac.university.usm.util.ComboUtility;
import in.cdac.university.usm.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usm/designation")
public class DesignationController {

    @Autowired
    private DesignationService designationService;

    @GetMapping("list")
    public @ResponseBody ResponseEntity<?> getList() throws IllegalAccessException {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(designationService.getAllDesignations())
        );
    }
}
