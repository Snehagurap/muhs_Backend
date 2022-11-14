package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.bean.TemplateSubHeaderBean;
import in.cdac.university.globalService.service.TemplateSubHeaderService;
import in.cdac.university.globalService.util.ListPageUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/global/template/subHeader")
public class TemplateSubHeaderController {

    @Autowired
    private TemplateSubHeaderService templateSubHeaderService;

    @GetMapping("listPage/{headerId}")
    public ResponseEntity<?> getListPageData(@PathVariable("headerId") Long headerId) throws Exception {
        return ResponseHandler.generateOkResponse(
                ListPageUtility.generateListPageData(templateSubHeaderService.listPageData(headerId))
        );
    }

    @PostMapping("save")
    public ResponseEntity<?> save(@Valid @RequestBody TemplateSubHeaderBean templateSubHeaderBean) throws Exception {
        return null;
    }
}
