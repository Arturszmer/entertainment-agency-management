package com.agency.generator;

import com.agency.documentcontext.doccontext.DocContextType;
import com.agency.documentcontext.doccontext.DocumentContext;
import com.agency.documentcontext.doccontext.GenerationResult;
import com.agency.generator.service.FileWriterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
public class HtmlTemplateDocGenerator extends DocGenerator {

    private final String htmlTemplate;

    public HtmlTemplateDocGenerator(String docStaticFilePath,
                                    FileWriterService fileWriterService,
                                    String htmlTemplate) {
        super(docStaticFilePath, fileWriterService);
        this.htmlTemplate = htmlTemplate;
    }

    @Override
    public GenerationResult generate(DocumentContext context) {
        String replacedText = replacePlaceholdersInHTMLTemplate(context);
        XWPFDocument document = new XWPFDocument();

        // Parse HTML
        Document htmlDoc = Jsoup.parse(replacedText);

        // Convert HTML elements to DOCX elements
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run;

        // Process HTML elements and add to document
        // This is a simplified approach - you'll need to handle different HTML elements accordingly
        Elements elements = htmlDoc.body().children();
        for (Element element : elements) {
            if (element.tagName().equals("p")) {
                paragraph = document.createParagraph();
                run = paragraph.createRun();
                run.setText(element.text());
            } else if (element.tagName().equals("h1")) {
                paragraph = document.createParagraph();
                paragraph.setStyle("Heading1");
                run = paragraph.createRun();
                run.setText(element.text());
                run.setBold(true);
            }
            // Handle other HTML elements accordingly
        }
        String outputFile = createOutputFile(context, document);
        generationResult.setFilename(outputFile);
        generationResult.setContent(replacedText);
        return generationResult;
    }

    protected String createOutputFile(DocumentContext context, XWPFDocument document) {
        String fileName;
        final String operationId = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        log.info("Try to save generated document with operationId: {}", operationId);
        final String date = context.getContractWork().startDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        if (generationResult.isSuccess()) {
            fileName = String.format("%s_%s_%s-%s.docx",
                    context.getContractor().name(),
                    context.getContractor().lastName(),
                    date,
                    operationId);
            fileWriterService.write(fileName, DocContextType.CONTRACT_WORK, document);
        } else {
            fileName = String.format("%s_%s_%s-error.txt",
                    context.getContractor().name(),
                    context.getContractor().lastName(),
                    operationId);
            setFinalErrorLog(context);
            fileWriterService.writeErrorLog(fileName, DocContextType.CONTRACT_WORK, generationResult.getErrorLogs());
        }
        return fileName;
    }

    private String replacePlaceholdersInHTMLTemplate(DocumentContext context) {
        return new DocParagraphsReplacer(generationResult)
                .replaceText(htmlTemplate, context.getPlaceholders());
    }
}
