package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.bean.TemplateMasterBean;
import in.cdac.university.globalService.bean.TempleHeadCompBean;
import in.cdac.university.globalService.service.TemplateService;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/global/template")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody TemplateMasterBean templateMasterBean) throws Exception {

        return ResponseHandler.generateResponse(templateService.save(templateMasterBean));
    }

    @PostMapping("update")
    public ResponseEntity<?> update(@Valid @RequestBody TemplateMasterBean templateMasterBean) throws Exception {
        return ResponseHandler.generateResponse(templateService.update(templateMasterBean));
    }

    // Delete mapping replaced by POST
    @PostMapping("delete")
    public ResponseEntity<?> delete(@RequestBody List<Long> idsToDelete) throws Exception {
        return ResponseHandler.generateOkResponse(templateService.delete(idsToDelete));

    }


    @GetMapping("/getTemplateById/{templateId}")
    public ResponseEntity<?> getTemplateById(@PathVariable("templateId") Long templateId) throws Exception {
        return ResponseHandler.generateResponse(
                templateService.getTemplateById(templateId)
        );
    }

    @GetMapping("/getAllTemplate")
    public ResponseEntity<?> getAllTemplate() throws Exception {
        return ResponseHandler.generateResponse(
                templateService.getAllTemplate()
        );
    }
    
    @PostMapping("manageTemplateOrder")
    public ResponseEntity<?> updateHeadAndCompDisplayOrder(@Valid @RequestBody TempleHeadCompBean templeHeadCompBean) throws Exception {
    	 return ResponseHandler.generateResponse(
                 templateService.manageTemplateOrder(templeHeadCompBean)
         );
    }
}
