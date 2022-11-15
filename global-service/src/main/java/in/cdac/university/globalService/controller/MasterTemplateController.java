package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.bean.TemplateToSaveBean;
import in.cdac.university.globalService.service.MasterTemplateService;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/global/masterTemplate")
public class MasterTemplateController {

    @Autowired
    private MasterTemplateService masterTemplateService;

    @GetMapping("{templateId}")
    public ResponseEntity<?> getTemplate(@PathVariable("templateId") Long templateId) throws Exception {
        return ResponseHandler.generateResponse(
                masterTemplateService.getTemplate(templateId)
        );
    }

    @PostMapping("save")
    public ResponseEntity<?> save(@Valid @RequestBody TemplateToSaveBean templateToSaveBean) throws Exception {
        return ResponseHandler.generateResponse(
                masterTemplateService.save(templateToSaveBean)
        );
    }
}
