package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.bean.TemplateComponentBean;
import in.cdac.university.globalService.bean.TemplateMasterBean;
import in.cdac.university.globalService.service.TemplateService;
import in.cdac.university.globalService.util.ResponseHandler;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    

    @PostMapping("/save")
	public ResponseEntity<?> save(@Valid @RequestBody TemplateMasterBean templateMasterBean) throws Exception {

		return ResponseHandler.generateResponse(templateService.save(templateMasterBean));
	}

	@PutMapping("update")
	public ResponseEntity<?> update(@Valid @RequestBody TemplateMasterBean templateMasterBean) throws Exception {
		return ResponseHandler.generateResponse(templateService.update(templateMasterBean));
		
	}

	@DeleteMapping("delete")
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

}
