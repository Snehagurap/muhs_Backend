package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.service.TemplateService;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/global/template")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @GetMapping("{templateId}")
    public ResponseEntity<?> getTemplate(@PathVariable("templateId") Long templateId) {
        return ResponseHandler.generateResponse(
                templateService.getTemplate(templateId)
        );
    }
}
