package com.agency.generator;

import com.agency.documentcontext.doccontext.DocContextType;
import com.agency.documentcontext.doccontext.DocumentContext;
import com.agency.documentcontext.doccontext.GenerationResult;
import com.agency.generator.service.FileWriterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
public class DocGenerator {

    private final String docStaticFilePath;
    private final String templateFilename;
    private final FileWriterService fileWriterService;
    private final GenerationResult generationResult = new GenerationResult();

    public DocGenerator(String docStaticFilePath, String templateFilename, FileWriterService fileWriterService) {
        this.docStaticFilePath = docStaticFilePath.endsWith("/") ? docStaticFilePath : docStaticFilePath + "/";
        this.templateFilename = templateFilename;
        this.fileWriterService = fileWriterService;
    }

    public GenerationResult generate(DocumentContext context) {
        try {
            String templatePath = docStaticFilePath + DocContextType.TEMPLATE.name() + "/";
            FileInputStream fis = new FileInputStream(templatePath + templateFilename);
            XWPFDocument document = new XWPFDocument(fis);
            for (XWPFParagraph p : document.getParagraphs()) {
                processParagraph(context, p);
            }

            String fileName = createOutputFile(context, document, fis);
            generationResult.setFilename(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return generationResult;
    }

    private String createOutputFile(DocumentContext context, XWPFDocument document, FileInputStream fis) throws IOException {
        String fileName;

        final String operationId = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        log.info("Try to save generated document with operationId: {}", operationId);
        final String date = context.getContractWork().startDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        if (generationResult.isSuccess()) {
            fileName = String.format("%s_%s_%s-%s.docx", context.getContractor().name(),
                    context.getContractor().lastName(), date, operationId);
            fileWriterService.write(docStaticFilePath, fileName, context.getDocContextType(), document); // TODO: MOŻE ZWRACAĆ BOOLEAN I OBSŁUŻYĆ? COFNĄĆ TRANSAKCJE?
        } else {
            fileName = String.format("%s_%s_%s-error.txt", context.getContractor().name(), context.getContractor().lastName(),
                    operationId);
            setFinalErrorLog(context);
            fileWriterService.writeErrorLog(docStaticFilePath, fileName,
                    DocContextType.CONTRACT_WORK, generationResult.getErrorLogs());
            generationResult.setSuccess(Boolean.FALSE);
        }
        document.close();
        fis.close();
        return fileName;
    }

    private void processParagraph(DocumentContext context, XWPFParagraph p) {
        String replacedText = new DocParagraphsReplacer(generationResult).replaceText(p.getText(), context.getPlaceholders());
        if (!replacedText.equals(p.getText())) {
            XWPFRun original = p.getRuns().get(0);
            String fontFamily = original.getFontFamily();
            Double fontSizeAsDouble = original.getFontSizeAsDouble();
            boolean italic = original.isItalic();
            boolean bold = original.isBold();

            removeExistingRuns(p);

            XWPFRun newRun = p.createRun();
            newRun.setText(replacedText);

            if (!p.getRuns().isEmpty()) {
                newRun.setBold(bold);
                newRun.setItalic(italic);
                newRun.setFontSize(fontSizeAsDouble);
                newRun.setFontFamily(fontFamily);
            }
        }
    }

    private void removeExistingRuns(XWPFParagraph p) {
        for (int i = p.getRuns().size() - 1; i >= 0; i--) {
            p.removeRun(i);
        }
    }

    private void setFinalErrorLog(DocumentContext context) {
        StringBuilder availablePlaceholders = new StringBuilder();
        for (String key : context.getPlaceholders().keySet()) {
            availablePlaceholders.append(key).append(System.lineSeparator());
        }
        generationResult.getErrorLogs().add("Available context placeholders: " + availablePlaceholders);
    }
}
