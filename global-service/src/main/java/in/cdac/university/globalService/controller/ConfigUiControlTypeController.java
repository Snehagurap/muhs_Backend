package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.service.ConfigUiControlTypeService;
import in.cdac.university.globalService.util.ComboUtility;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/global/ui/config/control")
public class ConfigUiControlTypeController {

    @Autowired
    private ConfigUiControlTypeService configUiControlTypeService;

    @GetMapping("type")
    public ResponseEntity<?> getAllControls() throws Exception {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(
                        configUiControlTypeService.getAllControls(RequestUtility.getUniversityId())
                )
        );
    }
}
