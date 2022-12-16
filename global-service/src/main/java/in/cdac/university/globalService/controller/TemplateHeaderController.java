package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.bean.TemplateHeaderBean;
import in.cdac.university.globalService.bean.TemplateHeaderSubHeaderBean;
import in.cdac.university.globalService.service.TemplateHeaderService;
import in.cdac.university.globalService.util.ComboUtility;
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

    @GetMapping("combo")
    public ResponseEntity<?> getTemplateHeaderCombo() throws Exception {
        return ResponseHandler.generateResponse(
                templateHeaderService.getTemplateHeaderCombo()
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

    // Put mapping replaced by POST
    @PostMapping("update")
    public ResponseEntity<?> update(@Valid @RequestBody TemplateHeaderBean templateHeaderBean) throws Exception {
        templateHeaderBean.setUnumIsvalid(1);
        templateHeaderBean.setUdtEffFrom(new Date());
        templateHeaderBean.setUdtEntryDate(new Date());
        templateHeaderBean.setUnumUnivId(RequestUtility.getUniversityId());
        templateHeaderBean.setUnumEntryUid(RequestUtility.getUserId());
        return ResponseHandler.generateResponse(
                templateHeaderService.update(templateHeaderBean)
        );
    }

    // Delete mapping replaced by POST
    @PostMapping("delete")
    public ResponseEntity<?> delete(@RequestBody Long[] idsToDelete) throws Exception {
        TemplateHeaderBean templateHeaderBean = new TemplateHeaderBean();
        templateHeaderBean.setUdtEntryDate(new Date());
        templateHeaderBean.setUnumUnivId(RequestUtility.getUniversityId());
        templateHeaderBean.setUnumEntryUid(RequestUtility.getUserId());
        return ResponseHandler.generateResponse(
          templateHeaderService.delete(templateHeaderBean, idsToDelete)
        );
    }
    
    @GetMapping("subHeaderCombo/{unumTemplHeadId}")
	public ResponseEntity<?> getAllsubHeaderComboByselectedHeader(@PathVariable("unumTemplHeadId") Long unumTemplHeadId) throws Exception {
		 return ResponseHandler.generateOkResponse(
	                ComboUtility.generateComboData(templateHeaderService.getSubHeaderComboID(unumTemplHeadId))
	        );
	}
    
    @PostMapping("ItemsByHeaderSubHeaderComponents")
    public ResponseEntity<?> getItemsByHeaderSubHeaderComponents(@Valid @RequestBody TemplateHeaderSubHeaderBean templateHeaderSubHeaderBean) throws Exception {
       
        return ResponseHandler.generateResponse(
                templateHeaderService.getItemsByHeaderSubHeaderComponents(templateHeaderSubHeaderBean)
        );
    }
    
    
}
