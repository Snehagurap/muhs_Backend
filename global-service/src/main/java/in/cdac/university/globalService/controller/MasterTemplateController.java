package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.bean.ComboBean;
import in.cdac.university.globalService.bean.TemplateToSaveBean;
import in.cdac.university.globalService.service.MasterTemplateService;
import in.cdac.university.globalService.util.ComboUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/global/masterTemplate")
public class MasterTemplateController {

    @Autowired
    private MasterTemplateService masterTemplateService;

    @GetMapping("{templateId}/{notificationId}/{notificationDetailId}")
    public ResponseEntity<?> getTemplate(@PathVariable("templateId") Long templateId,
                                         @PathVariable("notificationId") Long notificationId,
                                         @PathVariable("notificationDetailId") Long notificationDetailId) throws Exception {
        return ResponseHandler.generateResponse(
                masterTemplateService.getTemplate(templateId, notificationId, notificationDetailId)
        );
    }

    @PostMapping("save")
    public ResponseEntity<?> save(@Valid @RequestBody TemplateToSaveBean templateToSaveBean) throws Exception {
        return ResponseHandler.generateResponse(
                masterTemplateService.save(templateToSaveBean)
        );
    }

    @GetMapping("combo")
    public ResponseEntity<?> getCombo() throws Exception {
        List<ComboBean> comboBeanList = new ArrayList<>();
        comboBeanList.add(new ComboBean("", "Select Template"));
        comboBeanList.addAll(ComboUtility.generateComboData(masterTemplateService.getCombo()));
        return ResponseHandler.generateOkResponse(comboBeanList);
    }
}
