package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.service.TemplateComponentService;
import in.cdac.university.globalService.util.ListPageUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import in.cdac.university.globalService.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/global/template/component")
public class TemplateComponentController {

    @Autowired
    private TemplateComponentService templateComponentService;

    @GetMapping("listPage")
    public ResponseEntity<?> listPage() throws IllegalAccessException {
        return ResponseHandler.generateOkResponse(
                ListPageUtility.generateListPageData(templateComponentService.listPage())
        );
    }
}
