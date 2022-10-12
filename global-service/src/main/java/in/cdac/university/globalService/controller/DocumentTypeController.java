package in.cdac.university.globalService.controller;

import in.cdac.university.globalService.service.DocumentTypeService;
import in.cdac.university.globalService.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
