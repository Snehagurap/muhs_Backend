package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.bean.TemplateSubHeaderBean;
import in.cdac.university.globalService.service.TemplateSubHeaderService;
import in.cdac.university.globalService.util.ListPageUtility;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

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
        templateSubHeaderBean.setUnumIsvalid(1);
        templateSubHeaderBean.setUdtEffFrom(new Date());
        templateSubHeaderBean.setUdtEntryDate(new Date());
        templateSubHeaderBean.setUnumUnivId(RequestUtility.getUniversityId());
        templateSubHeaderBean.setUnumEntryUid(RequestUtility.getUserId());
        return ResponseHandler.generateResponse(
                templateSubHeaderService.save(templateSubHeaderBean)
        );
    }

    @GetMapping("{subHeaderId}")
    public ResponseEntity<?> getSubHeaderById(@PathVariable("subHeaderId") Long subHeaderId) throws Exception {
        return ResponseHandler.generateResponse(
                templateSubHeaderService.getSubHeaderById(subHeaderId)
        );
    }

    // Put mapping replaced by POST
@PostMapping("update")
    public ResponseEntity<?> update(@Valid @RequestBody TemplateSubHeaderBean templateSubHeaderBean) throws Exception {
        templateSubHeaderBean.setUnumIsvalid(1);
        templateSubHeaderBean.setUdtEffFrom(new Date());
        templateSubHeaderBean.setUdtEntryDate(new Date());
        templateSubHeaderBean.setUnumUnivId(RequestUtility.getUniversityId());
        templateSubHeaderBean.setUnumEntryUid(RequestUtility.getUserId());
        return ResponseHandler.generateResponse(
                templateSubHeaderService.update(templateSubHeaderBean)
        );
    }

    // Delete mapping replaced by POST
@PostMapping("delete")
    public ResponseEntity<?> delete(@RequestBody Long[] idsToDelete) throws Exception {
        TemplateSubHeaderBean templateSubHeaderBean = new TemplateSubHeaderBean();
        templateSubHeaderBean.setUnumUnivId(RequestUtility.getUniversityId());
        templateSubHeaderBean.setUnumEntryUid(RequestUtility.getUserId());
        templateSubHeaderBean.setUdtEntryDate(new Date());
        return ResponseHandler.generateResponse(
                templateSubHeaderService.delete(templateSubHeaderBean, idsToDelete)
        );
    }
}
