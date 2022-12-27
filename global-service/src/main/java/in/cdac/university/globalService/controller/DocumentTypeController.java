package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.bean.DocumentTypeBean;
import in.cdac.university.globalService.service.DocumentTypeService;
import in.cdac.university.globalService.util.ListPageUtility;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/global/documentType")
public class DocumentTypeController {

    @Autowired
    private DocumentTypeService documentTypeService;

    @GetMapping("notification")
    public ResponseEntity<?> getDocumentTypeListForNotification() {
        return ResponseHandler.generateResponse(
            documentTypeService.getDocumentTypeListForNotification()
        );
    }

    @GetMapping("listPage/{status}")
    public ResponseEntity<?> listPageData(@PathVariable("status") Integer status) throws Exception {
        return ResponseHandler.generateOkResponse(
                ListPageUtility.generateListPageData(
                        documentTypeService.listPageData(status)
                )
        );
    }

    @GetMapping("{docId}")
    public ResponseEntity<?> getDocumentType(@PathVariable("docId") Long docId) {
        return ResponseHandler.generateResponse(
                documentTypeService.getDocumentType(docId)
        );
    }

    @PostMapping("save")
    public ResponseEntity<?> saveDocumentTypeDtl(@Valid @RequestBody DocumentTypeBean documentTypeBean) {
        System.out.print(documentTypeBean.getUnumIsvalid());
        documentTypeBean.setUdtEntryDate(new Date());
        documentTypeBean.setUdtEffFrom(new Date());
        return ResponseHandler.generateResponse(documentTypeService.save(documentTypeBean));
    }

    @PostMapping("update")
    public ResponseEntity<?> update(@Valid @RequestBody DocumentTypeBean documentTypeBean) throws Exception {
        documentTypeBean.setUdtEntryDate(new Date());
        documentTypeBean.setUdtEffFrom(new Date());
        return ResponseHandler.generateResponse(
                documentTypeService.update(documentTypeBean)
        );
    }

    @PostMapping("delete")
    public ResponseEntity<?> delete(@RequestBody Long[] idsToDelete) throws Exception {
        DocumentTypeBean documentTypeBean = new DocumentTypeBean();
        documentTypeBean.setUdtEntryDate(new Date());
        return ResponseHandler.generateResponse(
                documentTypeService.delete(documentTypeBean, idsToDelete)
        );
    }
}
