package in.cdac.university.globalService.controller;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.cdac.university.globalService.service.MasterTemplateService;
import in.cdac.university.globalService.util.PdfGenaratorUtil;
import in.cdac.university.globalService.util.ServiceResponse;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/global/pdf/creation")
@Slf4j
public class PdfCreationController {
    @Autowired
    private MasterTemplateService masterTemplateService;
    @Autowired
    private PdfGenaratorUtil pdfGenaratorUtil;


    @GetMapping("application/{applicationId}")
    public ResponseEntity<?> getTemplate(@PathVariable("applicationId") Long applicationId) throws Exception {

        ServiceResponse serviceResponse = masterTemplateService.getTemplate(applicationId);
        final ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> templateComponentMap = mapper.convertValue(serviceResponse.getResponseObject(), Map.class);

        List<Map<String, Object>> templateList = (List<Map<String, Object>>) templateComponentMap.get("templateList");

        byte[] pdfBytes = pdfGenaratorUtil.createPdfBytes("application", templateList.get(0));
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(pdfBytes));
        String fileName = "Application.pdf";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(pdfBytes.length)
                .body(resource);


    }

}
