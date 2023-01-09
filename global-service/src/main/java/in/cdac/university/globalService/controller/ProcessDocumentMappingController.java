package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.bean.ProcessDocumentMappingBean;
import in.cdac.university.globalService.service.ProcessDocumentMappingService;
import in.cdac.university.globalService.util.ListPageUtility;
import in.cdac.university.globalService.util.RequestUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/global/processDocument")
public class ProcessDocumentMappingController {

    @Autowired
    private ProcessDocumentMappingService processDocumentMappingService;

    @GetMapping("mapping/{processId}")
    public ResponseEntity<?> getMappingDetails(@PathVariable("processId") Long processId) {
        return ResponseHandler.generateResponse(
                processDocumentMappingService.getMappingDetails(processId)
        );
    }

    @PostMapping("mapping/save")
    public ResponseEntity<?> saveMappingDetails(@Valid @RequestBody ProcessDocumentMappingBean processDocumentMappingBean) throws Exception {
        processDocumentMappingBean.setUnumIsvalid(1);
        processDocumentMappingBean.setUnumEntryUid(RequestUtility.getUserId());
        Date d1 = new Date();
        processDocumentMappingBean.setUdtEntryDate(d1);
        processDocumentMappingBean.setUdtEffFrom(d1);
        return ResponseHandler.generateResponse(
                processDocumentMappingService.saveMappingDetails(processDocumentMappingBean)
        );
    }

    @GetMapping("mapping/listPage")
    public ResponseEntity<?> getListPageDtl() throws Exception {
        return ResponseHandler.generateOkResponse(
                ListPageUtility.generateListPageData(
                        processDocumentMappingService.getListPageDtl()
                )
        );
    }
}
