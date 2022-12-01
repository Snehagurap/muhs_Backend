package in.cdac.university.globalService.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.cdac.university.globalService.bean.TemplateComponentBean;
import in.cdac.university.globalService.service.TemplateComponentService;
import in.cdac.university.globalService.util.ListPageUtility;
import in.cdac.university.globalService.util.ResponseHandler;

@RestController
@RequestMapping("/global/template/component")
public class TemplateComponentController {

	@Autowired
	private TemplateComponentService templateComponentService;

	@GetMapping("listPage")
	public ResponseEntity<?> listPage() throws Exception {
		return ResponseHandler
				.generateOkResponse(ListPageUtility.generateListPageData(templateComponentService.listPage()));
	}

	@PostMapping("save")
	public ResponseEntity<?> save(@Valid @RequestBody TemplateComponentBean templateBean) throws Exception {

		return ResponseHandler.generateResponse(templateComponentService.save(templateBean));
	}

	@PostMapping("update")
	public ResponseEntity<?> update(@Valid @RequestBody TemplateComponentBean templateBean) throws Exception {
		return ResponseHandler.generateResponse(templateComponentService.update(templateBean));
	}

//	@GetMapping("delete")
//	public ResponseEntity<?> delete(@RequestBody List<Long> idsToDelete) throws Exception {
//		return ResponseHandler.generateOkResponse(templateComponentService.delete(idsToDelete));
//
//	}
}
