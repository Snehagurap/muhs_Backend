package in.cdac.university.globalService.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
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
import in.cdac.university.globalService.util.ResponseHandler;
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

        Resource resource = null;
        try {
            String property = "java.io.tmpdir";
            String tempDir = System.getProperty(property);
            String fileNameUrl = pdfGenaratorUtil.createPdf("planingBoard", templateList.get(0));
            Path path = Paths.get(tempDir + "/" + fileNameUrl);
            resource = new UrlResource(path.toUri());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String fileName = resource == null ? "" : resource.getFilename();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_PDF_VALUE))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(resource);


    }

}
