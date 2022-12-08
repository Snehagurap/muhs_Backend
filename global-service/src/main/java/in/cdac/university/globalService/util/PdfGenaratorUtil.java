package in.cdac.university.globalService.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class PdfGenaratorUtil {

@Autowired

private TemplateEngine templateEngine;

public String createPdf(String templatename, Map map) throws Exception {
	String fileNameUrl = "";
	Context ctx = new Context();
	if (map != null) {
	Iterator itMap = map.entrySet().iterator();
	while (itMap.hasNext()) {
		
	Map.Entry pair = (Map.Entry) itMap.next();
	log.info("key {} " ,pair.getKey().toString());
	ctx.setVariable(pair.getKey().toString(), pair.getValue());

}

}

String processedHtml = templateEngine.process(templatename, ctx);

FileOutputStream os = null;


try {

	final File outputFile = File.createTempFile("planing_board", ".pdf");
	os = new FileOutputStream(outputFile);
	ITextRenderer itr = new ITextRenderer();
	itr.setDocumentFromString(processedHtml);
	itr.layout();
	itr.createPDF(os, false);
	itr.finishPDF();
	fileNameUrl = outputFile.getName();

}

finally {
	if (os != null) {
	try {
		os.close();
	} catch (IOException e) { }

}

}

return fileNameUrl;

}

}