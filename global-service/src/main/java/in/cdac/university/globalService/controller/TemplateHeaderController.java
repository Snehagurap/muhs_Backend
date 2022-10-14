package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.service.TemplateHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/global/template/header")
public class TemplateHeaderController {

    @Autowired
    private TemplateHeaderService templateHeaderService;
}
