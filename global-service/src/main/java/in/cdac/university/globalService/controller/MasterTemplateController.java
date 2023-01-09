package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.bean.CollegeBean;
import in.cdac.university.globalService.bean.ComboBean;
import in.cdac.university.globalService.bean.MasterTemplateBean;
import in.cdac.university.globalService.bean.TemplateToSaveBean;
import in.cdac.university.globalService.service.MasterTemplateService;
import in.cdac.university.globalService.util.ComboUtility;
import in.cdac.university.globalService.util.ListPageUtility;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/global/masterTemplate")
public class MasterTemplateController {

    @Autowired
    private MasterTemplateService masterTemplateService;

    @GetMapping("{masterTemplateId}/{notificationId}/{notificationDetailId}")
    public ResponseEntity<?> getTemplate(@PathVariable("masterTemplateId") Long masterTemplateId,
                                         @PathVariable("notificationId") Long notificationId,
                                         @PathVariable("notificationDetailId") Long notificationDetailId) throws Exception {
        return ResponseHandler.generateResponse(
                masterTemplateService.getTemplate(masterTemplateId, notificationId, notificationDetailId)
        );
    }

    // To preview the added template from template master
    @GetMapping("template/preview/{templateId}/{streamId}")
    public ResponseEntity<?> previewTemplate(@PathVariable("templateId") Long templateId,
                                             @PathVariable("streamId") Integer streamId) throws Exception {
        return ResponseHandler.generateResponse(
                masterTemplateService.getTemplate(templateId, streamId)
        );
    }

    @PostMapping("save")
    public ResponseEntity<?> save(@Valid @RequestBody TemplateToSaveBean templateToSaveBean) throws Exception {
        return ResponseHandler.generateResponse(
                masterTemplateService.save(templateToSaveBean)
        );
    }

    @GetMapping("combo")
    public ResponseEntity<?> getCombo() throws Exception {
        List<ComboBean> comboBeanList = new ArrayList<>();
        comboBeanList.add(new ComboBean("", "Select Template"));
        comboBeanList.addAll(ComboUtility.generateComboData(masterTemplateService.getCombo()));
        return ResponseHandler.generateOkResponse(comboBeanList);
    }

    @GetMapping("scrutiny/listPage/{notificationId}/{applicationStatus}")
    public ResponseEntity<?> scrutinyListPage(@PathVariable("notificationId") Long notificationId,
                                              @PathVariable("applicationStatus") Integer applicationStatus) throws Exception {
        return ResponseHandler.generateOkResponse(
                ListPageUtility.generateListPageData(masterTemplateService.scrutinyListPage(notificationId, applicationStatus))
        );
    }

    @GetMapping("applicationStatusCombo")
    public ResponseEntity<?> getApplicationStatusCombo() throws IllegalAccessException {
        return ResponseHandler.generateOkResponse(
                ComboUtility.generateComboData( masterTemplateService.getApplicationStatusCombo() )
        );
    }

    @GetMapping("application/{applicationId}")
    public ResponseEntity<?> getApplicationById(@PathVariable("applicationId") Long applicationId) throws Exception {
        return ResponseHandler.generateResponse(
                masterTemplateService.getApplicationById(applicationId)
        );
    }
    
    @PostMapping("saveMasterTemplate")
    public ResponseEntity<?> saveMasterTemplate(@Valid @RequestBody MasterTemplateBean masterTemplateBean) throws Exception {
        return ResponseHandler.generateResponse(
                masterTemplateService.saveMasterTemplate(masterTemplateBean)
        );
    }
    
    // Delete mapping replaced by POST
    @PostMapping("delete")
        public ResponseEntity<?> delete(@RequestBody Long[] idsToDelete) throws Exception {
    		MasterTemplateBean masterTemplateBean = new MasterTemplateBean();
            
    		masterTemplateBean.setUnumUnivId(RequestUtility.getUniversityId());
    		masterTemplateBean.setUnumEntryUid(RequestUtility.getUserId());
            return ResponseHandler.generateResponse(
            		masterTemplateService.delete(masterTemplateBean, idsToDelete)
            );
        }
    
    
}
