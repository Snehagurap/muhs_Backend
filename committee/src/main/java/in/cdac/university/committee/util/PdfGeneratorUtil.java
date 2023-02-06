package in.cdac.university.committee.util;

import com.itextpdf.html2pdf.HtmlConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@Component
@Slf4j
public class PdfGeneratorUtil {
    @Autowired
    private TemplateEngine templateEngine;

    public String createPdf(String templateName, Map<?, ?> map) throws Exception {
        String fileNameUrl = "";
        Context ctx = new Context();
        if (map != null) {
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                ctx.setVariable(entry.getKey().toString(), entry.getValue());
            }
        }

        String processedHtml = templateEngine.process(templateName, ctx);

        FileOutputStream os = null;
        try {
            final File outputFile = File.createTempFile("committee", ".pdf");
            os = new FileOutputStream(outputFile);
            ITextRenderer itr = new ITextRenderer();
            itr.setDocumentFromString(processedHtml);
            itr.layout();
            itr.createPDF(os, false);
            itr.finishPDF();
            fileNameUrl = outputFile.getName();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileNameUrl;
    }

    public byte[] createPdfBytes(String templateName, Map<?, ?> map) {
        Context ctx = new Context();
        if (map != null) {
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                ctx.setVariable(entry.getKey().toString(), entry.getValue());
            }
        }

        String processedHtml = templateEngine.process(templateName, ctx);
        log.info("HTML: {}", processedHtml);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            HtmlConverter.convertToPdf(processedHtml, baos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }
}
