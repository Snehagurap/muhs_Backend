package in.cdac.university.globalService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.cdac.university.globalService.bean.*;
import in.cdac.university.globalService.service.MasterTemplateService;
import in.cdac.university.globalService.util.*;
import in.cdac.university.globalService.util.html.Style;
import in.cdac.university.globalService.util.html.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

    @Autowired
    private FtpUtility ftpUtility;

    private static final String HTML_START = """
    <html>
    <head>
        <style>
            @media print {
                @page {
                    @top-left {
                        content: "";
                    }
                    @top-center {
                        content: "";
                    }
                    @top-right {
                        content: "";
                    }
                    @bottom-left {
                        content: counter(page) "/" counter(pages);
                    }
                    @bottom-center {
                        content: "";
                    }
                    @bottom-right {
                        content: "Sign of Chairman/Secretary";
                    }
                }
            }
            
            table, th, tr, td {
                border: 1px solid black;
                border-collapse: collapse;
            }
        </style>
    </head>
    <body>
    """;


    @GetMapping("application/{applicationId}/{shouldZip}")
    public ResponseEntity<?> getTemplate(@PathVariable("applicationId") Long applicationId,
                                         @PathVariable("shouldZip") Integer shouldZip) throws Exception {

        ByteArrayOutputStream baos = null;
        ZipOutputStream zout;
        if (shouldZip == 1) {
            baos = new ByteArrayOutputStream();
            zout = new ZipOutputStream(baos);
        } else {
            zout = null;
        }

        ServiceResponse serviceResponse = masterTemplateService.getTemplate(applicationId);
        StringBuilder html = new StringBuilder(HTML_START);
        if (serviceResponse.getStatus() == 1) {
            MasterTemplateBean masterTemplateBean = objectMapper.convertValue(serviceResponse.getResponseObject(), MasterTemplateBean.class);

            html.append("<div>");
            html.append("Proposal for academic year: ").append(masterTemplateBean.getUstrAcademicYear());
            html.append("</div>");

            html.append("<div style='display: flex; align-items: center; justify-content: center;'>");
            html.append("<div style='margin-right: 20px'>");
            html.append("<img height='100px' width='100px' src='" + Constants.LOGO + "'>");
            html.append("</div>");
            html.append("<div>");

            AtomicInteger headerCount = new AtomicInteger(0);
            masterTemplateBean.getTemplateList().forEach(templateBean -> {
                Map<Long, TemplateItemBean> mapItem = new HashMap<>();
                List<TemplateHeaderBean> headers = templateBean.getHeaders();
                headers.forEach((header) -> {
                    int count = headerCount.addAndGet(1);
                    if (count == 4) {
                        html.append("</div>");
                        html.append("</div>");
                    }
                    processHeader(html, header, mapItem, zout);
                });

                if (templateBean.getCheckList() != null) {
                    processChecklist(html, templateBean.getCheckList());
                }
            });
        }
        html.append("</body></html>");
        log.debug("HTML: {}", html);

        byte[] pdfBytes = pdfGenaratorUtil.createPdfBytesFromHtmlString(html.toString());
        String fileName = "Application.pdf";

        if (shouldZip == 1) {
            ZipEntry entry = new ZipEntry(fileName);
            zout.putNextEntry(entry);
            zout.write(pdfBytes);
            zout.closeEntry();
            zout.close();
            fileName = "Application.zip";
        }
        if (shouldZip == 1) {
            return ResponseHandler.generateFileResponse(baos.toByteArray(), fileName);
        }
        return ResponseHandler.generateFileResponse(pdfBytes, fileName);
    }

    private final String FLEX_SEQUENCE = "2";
    private final String FLEX_TEXT = "35";

    private void processHeader(StringBuilder html, TemplateHeaderBean headerBean, Map<Long, TemplateItemBean> mapItem, ZipOutputStream zout) {
        if (headerBean.getUnumTempleHeadId() < 0) {
            html.append(
                    Tag.createTag(headerBean.getUstrTempleHeadCode())
                            .addStyle(Style.createStyle().alignment(headerBean.getUstrHeadAllignment()))
                            .addText(headerBean.getUstrHeadPrintText())
                            .endTag()
            );
        } else {
            if (headerBean.getUnumTempleHeadId() != 27) {
                Style headStyle = Style.createStyle()
                        .alignment(headerBean.getUstrHeadAllignment())
                        .paddingTop("25px")
                        .display("flex");

                html.append("<div").append(headStyle).append(">");

                boolean isHeaderTextAdded = false;
                if (headerBean.getUnumIsHidden() == null || headerBean.getUnumIsHidden() == 0) {
                    if (headerBean.getUstrHeadPrintPrefixText() != null && !headerBean.getUstrHeadPrintPrefixText().isBlank()) {
                        Style flexPrefix = Style.createStyle().flex(FLEX_SEQUENCE).fontWeight("bold");
                        html.append("<div").append(flexPrefix).append(">")
                            .append(headerBean.getUstrHeadPrintPrefixText())
                            .append("</div>");
                    }
                    Style flexPrintText = Style.createStyle().flex(FLEX_TEXT);
                    html.append("<div")
                            .append(flexPrintText)
                            .append("<span").append(Style.createStyle().fontWeight("bold")).append(">")
                            .append(headerBean.getUstrHeadPrintText())
                            .append("</span>");

                    isHeaderTextAdded = true;
                }

                if (headerBean.getComponents() != null && !headerBean.getComponents().isEmpty()) {
                    processComponents(html, headerBean.getComponents(), mapItem, zout);
                }

                if (isHeaderTextAdded) {
                    html.append("</div>");
                }
                html.append("</div>");
            }
        }
    }

    private void processComponents(StringBuilder html, List<TemplateComponentBean> components, Map<Long, TemplateItemBean> mapItem, ZipOutputStream zout) {
        components.forEach(component -> {
            if (component.getItems() != null && !component.getItems().isEmpty())
                processItems(html, component.getItems(), mapItem, false, zout);
        });
    }

    private void processItems(StringBuilder html, List<TemplateItemBean> items, Map<Long, TemplateItemBean> mapItem, boolean isMerged, ZipOutputStream zout) {
        items.forEach(item -> {
            mapItem.put(item.getUnumTempleItemId(), item);

            // Item with hidden children
            String parentValueCheckFlag = item.getUnumParentValueCheckFlag();
            if (parentValueCheckFlag != null && !parentValueCheckFlag.isBlank()) {
                Long parentId = item.getUnumTempleParentItemId();
                if (parentId != null) {
                    TemplateItemBean parentItem = mapItem.get(parentId);
                    if (parentItem != null) {
                        String parentValue = parentItem.getUstrItemValue();
                        Optional<String> itemFound = Arrays.stream(parentValueCheckFlag.split(","))
                                .filter(value -> value.equals(parentValue))
                                .findAny();

                        if (itemFound.isEmpty()) {
                            return;
                        }
                    }
                }
            }

            boolean isItemHidden = (item.getUnumIsHidden() != null && item.getUnumIsHidden() == 1);
            if (!isItemHidden) {
                if (isMerged) {
                    Style style = Style.createStyle();
                    html.append("<span").append(style).append(">");
                } else {
                    Style style = Style.createStyle()
                            .paddingTop("10px")
                            .paddingBottom("5px")
                            .display("flex");
                    html.append("<div").append(style).append(">");
                }

                if (item.getUstrItemPrintPrefixText() != null && !item.getUstrItemPrintPrefixText().isBlank()) {
                    Style prefixStyle;
                    if (isMerged)
                        prefixStyle = Style.createStyle();
                    else
                        prefixStyle = Style.createStyle().flex(FLEX_SEQUENCE);

                    html.append("<span").append(prefixStyle).append(">")
                            .append(item.getUstrItemPrintPrefixText())
                            .append("</span>");
                }

                Style textStyle;
                if (isMerged) {
                    textStyle = Style.createStyle();
                } else {
                    textStyle = Style.createStyle().flex(FLEX_TEXT);
                }
                html.append("<span").append(textStyle).append(">");

                if (item.getUstrItemPrintPreText() != null && !item.getUstrItemPrintPreText().isBlank()) {
                    html.append("<span>")
                            .append(item.getUstrItemPrintPreText())
                            .append("</span>");
                }

                if (item.getUstrItemValue() != null) {
                    Style valueStyle = Style.createStyle()
                            .fontWeight("bold")
                            .textDecoration("underline")
                            .paddingLeft("10px")
                            .paddingRight("10px");

                    html.append("<span").append(valueStyle).append(">")
                            .append(processItemValue(item, mapItem, zout))
                            .append("</span>");
                }

                if (item.getUstrItemPrintPostText() != null && !item.getUstrItemPrintPostText().isBlank()) {
                    html.append("<span>")
                            .append(item.getUstrItemPrintPostText())
                            .append("</span>");
                }
            }

            if (item.getChildren() != null && !item.getChildren().isEmpty()) {
                boolean isChildMerged = item.getChildren().get(0).getUnumIsMergeWithParent() != null && item.getChildren().get(0).getUnumIsMergeWithParent() == 1;

                processItems(html, item.getChildren(), mapItem, isChildMerged, zout);
            }

            if (!isItemHidden) {
                html.append("</span>");
                if (isMerged)
                    html.append("</span>");
                else
                    html.append("</div>");
            }
        });
    }

    private String processItemValue(TemplateItemBean item, Map<Long, TemplateItemBean> mapItem, ZipOutputStream zout) {
        String itemValue = item.getUstrItemValue();
        if (itemValue == null)
            return "";

        Integer controlId = item.getUnumUiControlId();

        // Radio Button
        if (controlId == 6 || controlId == 12 || controlId == 13
                || controlId == 14 || controlId == 15 || controlId == 18) {
            return "1".equals(itemValue) ? "Yes" : "No";
        }

        // Drop down
        if (controlId == 3 || controlId == 4) {
            if (itemValue.isBlank() || itemValue.equals("0"))
                return "";
            String apiUrl = item.getUstrItemApiUrl();
            String parentItemId = "0";
            if (item.getUnumTempleParentItemId() != null && item.getUnumTempleParentItemId() > 0) {
                if (mapItem.containsKey(item.getUnumTempleParentItemId())) {
                    parentItemId = mapItem.get(item.getUnumTempleParentItemId())
                            .getUstrItemValue();
                }
            }
            if (apiUrl != null && !apiUrl.isBlank()) {
                String itemValueByApi = getItemValueByApi(apiUrl, itemValue);
                if (itemValueByApi == null && !parentItemId.equals("0")) {
                    apiUrl += "/" + parentItemId;
                    itemValueByApi = getItemValueByApi(apiUrl, itemValue);
                }
                return (itemValueByApi == null) ? "" : itemValueByApi;
            }
        }

        // Date
        if (controlId == 7) {
            return DateUtility.dateAsStringToPrintableFormat(itemValue);
        }

        // File upload
        if (controlId == 9) {
            if (!itemValue.isEmpty()) {
                // Add entry in a ZIP file
                if (zout != null) {
                    byte[] fileBytes = ftpUtility.downloadFile(itemValue);
                    if (fileBytes != null) {
                        ZipEntry entry = new ZipEntry(itemValue);
                        try {
                            zout.putNextEntry(entry);
                            zout.write(fileBytes);
                            zout.closeEntry();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Style style = Style.createStyle().paddingLeft("10px").paddingRight("10px");
                    return "<a " + style + " href='" + itemValue + "'>" + itemValue + "</a>";
                }
                return itemValue;
            }
        }

        return itemValue;
    }

    private String getItemValueByApi(String apiUrl, String itemValue) {
        RestUtility.SERVICE_TYPE apiServiceType = null;
        if (apiUrl.startsWith("/usm"))
            apiServiceType = RestUtility.SERVICE_TYPE.USM;
        else if (apiUrl.startsWith("/global"))
            apiServiceType = RestUtility.SERVICE_TYPE.GLOBAL;

        if (apiServiceType != null) {
            ComboBean[] comboBeans = restUtility.get(apiServiceType, apiUrl, ComboBean[].class);
            if (comboBeans == null) {
                return null;
            }

            return Arrays.stream(comboBeans)
                    .filter(comboBean -> itemValue.equals(comboBean.getKey()))
                    .findFirst()
                    .map(ComboBean::getValue)
                    .orElse("");
        }

        return "";
    }

    private void processChecklist(StringBuilder html, CheckListBean checkList) {
        Style style = Style.createStyle().alignment("center");
        html.append("<div style='page-break-before: always'></div><div").append(style).append(">");
        html.append("<span")
                .append(style.fontWeight("bold").fontSize("18px")).append(">")
                .append(checkList.getUstrChecklistName())
                .append("</span>");
        html.append("</div>");
        html.append("<table cellpadding='3'>");
        html.append("<tr>");
        html.append("<th rowspan='2' width='5%'>").append("Sr. No.").append("</th>");
        html.append("<th rowspan='2' width='65%'>").append("Documents description").append("</th>");
        html.append("<th colspan='3'>").append("Write page numbers in the bracket of Page No.").append("</th>");
        html.append("</tr>");
        html.append("<tr style='border: 1px solid black; border-collapse: collapse'>");
        html.append("<th width='5%'>").append("Yes/No.").append("</th>");
        html.append("<th width='10%'>").append("Page No.").append("</th>");
        html.append("<th width='10%'>").append("For office use").append("</th>");
        html.append("</tr>");
        int index = 1;
        for (CheckListItems checkListItem: checkList.getCheckListItems()) {
            html.append("<tr>");
            html.append("<td>").append(index).append("</td>");
            html.append("<td>").append(checkListItem.getUstrChecklistItemName()).append("</td>");
            html.append("<td>").append(checkListItem.getUstrItemValue() == null || checkListItem.getUstrItemValue().isBlank() ? "No" : "Yes").append("</td>");
            html.append("<td>").append("</td>");
            html.append("<td>").append("</td>");
            html.append("</tr>");
            index++;
        }
        html.append("</table>");

        Style certificateStyle = Style.createStyle().alignment("center").paddingTop("20px")
                .paddingBottom("20px").fontWeight("bold").fontSize("20px");
        html.append("<div").append(certificateStyle).append(">").append("CERTIFICATE").append("</div>");

        Style line = Style.createStyle().background("black").height("5px");
        html.append("<div").append(line).append(">").append("</div>");

        Style textStyle = Style.createStyle().display("flex");
        html.append("<div").append(textStyle).append(">");
        html.append("<div").append(Style.createStyle().flex("8").padding("10px")).append(">");
        html.append("I hereby certify that papers are attached as per the check list." +
                " (N.B. Please note that all documents are mandatory. The application will be " +
                "rejected if one or more documents in the check list are not attached).");
        html.append("</div>");
        html.append("<div").append(Style.createStyle().width("5px").background("black")).append("></div>");
        html.append("<div").append(Style.createStyle().flex("2").alignment("center").padding("10px")).append(">");
        html.append("Signature of Scrutiny\nOfficer of MUHS");
        html.append("</div>");
        html.append("</div>");

        html.append("<div").append(textStyle.marginTop("50px")).append(">");
        html.append("<div").append(Style.createStyle().flex("5")).append(">");
        html.append("Place: \nDate:");
        html.append("</div>");
        html.append("<div").append(Style.createStyle().flex("5").alignment("right")).append(">");
        html.append("Chairman/Secretary");
        html.append("</div>");
        html.append("</div>");
    }
}
