package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.service.MasterTemplateService;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
