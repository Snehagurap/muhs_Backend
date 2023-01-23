package in.cdac.university.globalService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.cdac.university.globalService.bean.ComboBean;
import in.cdac.university.globalService.bean.MasterTemplateBean;
import in.cdac.university.globalService.bean.TemplateHeaderBean;
import in.cdac.university.globalService.service.MasterTemplateService;
import in.cdac.university.globalService.util.PdfGenaratorUtil;
import in.cdac.university.globalService.util.ResponseHandler;
import in.cdac.university.globalService.util.RestUtility;
import in.cdac.university.globalService.util.ServiceResponse;
import in.cdac.university.globalService.util.html.Style;
import in.cdac.university.globalService.util.html.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/global/pdf/creation")
@Slf4j
public class PdfCreationController {
    @Autowired
    private MasterTemplateService masterTemplateService;
    @Autowired
    private PdfGenaratorUtil pdfGenaratorUtil;

    @Autowired
    private RestUtility restUtility;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("application/{applicationId}")
    public ResponseEntity<?> getTemplate(@PathVariable("applicationId") Long applicationId) throws Exception {

        ServiceResponse serviceResponse = masterTemplateService.getTemplate(applicationId);
        StringBuilder html = new StringBuilder("<html><body>");
        if (serviceResponse.getStatus() == 1) {
            MasterTemplateBean masterTemplateBean = objectMapper.convertValue(serviceResponse.getResponseObject(), MasterTemplateBean.class);

            masterTemplateBean.getTemplateList().forEach(templateBean -> {
                List<TemplateHeaderBean> headers = templateBean.getHeaders();
                headers.forEach((header) -> {
                    processHeader(html, header);
                });
            });
        }
        html.append("</body></html>");

        //Map<String, Object> templateComponentMap = objectMapper.convertValue(serviceResponse.getResponseObject(), Map.class);

        //List<Map<String, Object>> templateList = (List<Map<String, Object>>) templateComponentMap.get("templateList");
//        AtomicInteger index = new AtomicInteger(1);
//        templateList.get(0).forEach((key, value) -> {
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
//                                        processItems(items);
//                                    }
//                                });
//                            });
//                        }
//                    });
//                });
//            }
//        });

        System.out.println("HTML: " + html);
        //byte[] pdfBytes = pdfGenaratorUtil.createPdfBytes("application", templateList.get(0));
        byte[] pdfBytes = pdfGenaratorUtil.createPdfBytesFromHtmlString(html.toString());
        String fileName = "Application.pdf";

        return ResponseHandler.generateFileResponse(pdfBytes, fileName);
    }

    private void processHeader(StringBuilder html, TemplateHeaderBean headerBean) {
        if (headerBean.getUnumTempleHeadId() < 0) {
            html.append(
                    Tag.createTag(headerBean.getUstrTempleHeadCode())
                            .addText(headerBean.getUstrHeadPrintText())
                            .endTag()
            );
        } else {
            if (headerBean.getUnumTempleHeadId() != 27) {
                html.append(
                        Tag.createTag("div")
                                .addStyle(Style.createStyle()
                                        .alignment(headerBean.getUstrHeadAllignment())
                                        .paddingTop("15px")
                                        .fontWeight("bold"))
                                .addTag(
                                        Tag.createTag("span")
                                                .addStyle(Style.createStyle()
                                                        .flex("2"))
                                                .addText(headerBean.getUstrHeadPrintPrefixText())
                                                .endTag()
                                )
                                .addTag(
                                        Tag.createTag("span")
                                                .addStyle(Style.createStyle()
                                                        .flex("30"))
                                                .addText(headerBean.getUstrHeadPrintText())
                                                .endTag()
                                )
                                .endTag()
                );
            }
        }
        html.append("</div>");
    }

    @SuppressWarnings("unchecked")
    private void processItems(List<Map<String, Object>> items) {
        items.forEach((item) -> {
            item.forEach((itemKey, itemValue) -> {
                System.out.println(itemKey + " = " + itemValue);
                if (itemKey.equals("unumUiControlId")) {

                }

                if (itemKey.equals("ustrItemApiUrl") && itemValue != null) {
                    String apiUrl = itemValue.toString();
                    if (!apiUrl.isBlank()) {
                        System.out.println("Calling api " + apiUrl + " to get data");
                        ComboBean[] comboValues = restUtility.get(RestUtility.SERVICE_TYPE.API_GATEWAY, apiUrl, ComboBean[].class);
                    }
                }
                if (itemValue instanceof List<?> childList) {
                    processItems((List<Map<String, Object>>) childList);
                }
            });
        });
    }
}
