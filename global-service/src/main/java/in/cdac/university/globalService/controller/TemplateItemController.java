package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.bean.TemplateItemBean;
import in.cdac.university.globalService.service.TemplateItemService;
import in.cdac.university.globalService.util.ComboUtility;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/global/template/item")
public class TemplateItemController {

    @Autowired
    private TemplateItemService templateItemService;

    @PostMapping("save")
    public ResponseEntity<?> save(@Valid @RequestBody TemplateItemBean templateItemBean) throws Exception {
        templateItemBean.setUnumIsvalid(1);
        templateItemBean.setUnumEntryUid(RequestUtility.getUserId());
        templateItemBean.setUnumUnivId(RequestUtility.getUniversityId());
        return ResponseHandler.generateResponse(
                templateItemService.save(templateItemBean)
        );
    }

    @GetMapping("combo")
    public ResponseEntity<?> getItemCombo() throws Exception {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData(
                        templateItemService.getAllItems(RequestUtility.getUniversityId())
                )
        );
    }
}
