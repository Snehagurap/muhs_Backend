package in.cdac.university.committee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.cdac.university.committee.service.LicCommitteeMstService;
import in.cdac.university.committee.util.PdfGeneratorUtil;
import in.cdac.university.committee.util.ResponseHandler;
import in.cdac.university.committee.util.ServiceResponse;
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
@RequestMapping("committee/pdf/creation")
@Slf4j
public class PdfCreationController {

    @Autowired
    PdfGeneratorUtil pdfGenaratorUtil;

    @Autowired
    LicCommitteeMstService licCommitteeMstService;

    @GetMapping("committee/{comId}")
    public ResponseEntity<?> getCommittee(@PathVariable("comId") Long comId) {
        ServiceResponse serviceResponse = licCommitteeMstService.getLicCommitteeByid(comId);
        final ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> committeMap = mapper.convertValue(serviceResponse.getResponseObject(), Map.class);

        List<Map<String, Object>> committeeList = (List<Map<String, Object>>) committeMap.get("committeeList");
        AtomicInteger index = new AtomicInteger(1);
//        committeeList.get(0).forEach((key, value) -> {
//            if (key != null && key.equals("headers")) {
//                List<Map<String, Object>> headers = (List<Map<String, Object>>) value;
//                headers.forEach((header) -> {
//                    header.forEach((headerKey, headerValue) -> {
//                        if (headerKey.equals("components")) {
//                            List<Map<String, Object>> components = (List<Map<String, Object>>) headerValue;
//                            components.forEach((component) -> {
//                                component.forEach((componentKey, componentValue) -> {
//                                    if (componentKey.equals("items")) {
//                                        List<Map<String, Object>> items = (List<Map<String, Object>>) componentValue;
////                                        processItems(items);
//                                    }
//                                });
//                            });
//                            System.out.println(index.getAndAdd(1) + ": " + headerKey + " = " + headerValue);
//                        }
//                    });
//                });
//            }
//        });

        byte[] pdfBytes = pdfGenaratorUtil.createPdfBytes("application", committeMap);
        String fileName = "Committee.pdf";

        return ResponseHandler.generateFileResponse(pdfBytes, fileName);
    }
}
