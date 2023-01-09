package in.cdac.university.globalService.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.cdac.university.globalService.bean.CheckListBean;
import in.cdac.university.globalService.bean.TemplateMasterBean;
import in.cdac.university.globalService.bean.TemplateMasterDtlsBean;
import in.cdac.university.globalService.service.CheckListService;
import in.cdac.university.globalService.service.TemplateService;
import in.cdac.university.globalService.util.ResponseHandler;

@RestController
@RequestMapping("/global/template/checklist")
public class CheckListController {

	@Autowired
    private CheckListService checkListService;

	@PostMapping("save")
    public ResponseEntity<?> saveChecklist(@Valid @RequestBody CheckListBean checkListBean) throws Exception {
        return ResponseHandler.generateResponse(checkListService.saveChecklist(checkListBean));
    }

}
