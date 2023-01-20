package in.cdac.university.globalService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.cdac.university.globalService.service.MasterTemplateService;
import in.cdac.university.globalService.util.PdfGenaratorUtil;
import in.cdac.university.globalService.util.ResponseHandler;
import in.cdac.university.globalService.util.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/global/pdf/creation")
@Slf4j
public class PdfCreationController {
    @Autowired
    private MasterTemplateService masterTemplateService;
    @Autowired
    private PdfGenaratorUtil pdfGenaratorUtil;

    @GetMapping("application/{applicationId}")
    @SuppressWarnings("unchecked")
    public ResponseEntity<?> getTemplate(@PathVariable("applicationId") Long applicationId) throws Exception {

        ServiceResponse serviceResponse = masterTemplateService.getTemplate(applicationId);
        final ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> templateComponentMap = mapper.convertValue(serviceResponse.getResponseObject(), Map.class);

        List<Map<String, Object>> templateList = (List<Map<String, Object>>) templateComponentMap.get("templateList");
        AtomicInteger index = new AtomicInteger(1);
        templateList.get(0).forEach((key, value) -> {
            if (key != null && key.equals("headers")) {
                List<Map<String, Object>> headers = (List<Map<String, Object>>) value;
                headers.forEach((header) -> {
                    header.forEach((headerKey, headerValue) -> {
                        if (headerKey.equals("components")) {
                            List<Map<String, Object>> components = (List<Map<String, Object>>) headerValue;
                            components.forEach((component) -> {
                                component.forEach((componentKey, componentValue) -> {
                                    if (componentKey.equals("items")) {
                                        List<Map<String, Object>> items = (List<Map<String, Object>>) componentValue;
                                        processItems(items);
                                    }
                                });
                            });
                            System.out.println(index.getAndAdd(1) + ": " + headerKey + " = " + headerValue);
                        }
                    });
                });
            }
        });

        byte[] pdfBytes = pdfGenaratorUtil.createPdfBytes("application", templateList.get(0));
        String fileName = "Application.pdf";

        return ResponseHandler.generateFileResponse(pdfBytes, fileName);
    }

    private void processItems(List<Map<String, Object>> items) {
        items.forEach((item) -> {

        });
    }
}
