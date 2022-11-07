package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.bean.TemplateHeaderBean;
import in.cdac.university.globalService.service.TemplateHeaderService;
import in.cdac.university.globalService.util.ListPageUtility;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/global/template/header")
public class TemplateHeaderController {

    @Autowired
    private TemplateHeaderService templateHeaderService;

    @GetMapping("listPage")
    public ResponseEntity<?> getListPageData() throws Exception {
        return ResponseHandler.generateOkResponse(
                ListPageUtility.generateListPageData(templateHeaderService.listPageData())
        );
    }

    @GetMapping("{headerId}")
    public ResponseEntity<?> getTemplateHeaderById(@PathVariable("headerId") Long headerId) throws Exception {
        return ResponseHandler.generateResponse(
                templateHeaderService.getTemplateHeaderById(headerId)
        );
    }

    @PostMapping("save")
    public ResponseEntity<?> save(@Valid @RequestBody TemplateHeaderBean templateHeaderBean) throws Exception {
        templateHeaderBean.setUnumIsvalid(1);
        templateHeaderBean.setUdtEffFrom(new Date());
        templateHeaderBean.setUdtEntryDate(new Date());
        templateHeaderBean.setUnumUnivId(RequestUtility.getUniversityId());
        templateHeaderBean.setUnumEntryUid(RequestUtility.getUserId());
        return ResponseHandler.generateResponse(
                templateHeaderService.save(templateHeaderBean)
        );
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@Valid @RequestBody TemplateHeaderBean templateHeaderBean) throws Exception {
        templateHeaderBean.setUdtEntryDate(new Date());
        templateHeaderBean.setUnumUnivId(RequestUtility.getUniversityId());
        templateHeaderBean.setUnumEntryUid(RequestUtility.getUserId());
        return ResponseHandler.generateResponse(
                templateHeaderService.update(templateHeaderBean)
        );
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@RequestBody Long[] idsToDelete) throws Exception {
        TemplateHeaderBean templateHeaderBean = new TemplateHeaderBean();
        templateHeaderBean.setUdtEntryDate(new Date());
        templateHeaderBean.setUnumUnivId(RequestUtility.getUniversityId());
        templateHeaderBean.setUnumEntryUid(RequestUtility.getUserId());
        return ResponseHandler.generateResponse(
          templateHeaderService.delete(templateHeaderBean, idsToDelete)
        );
    }
}
