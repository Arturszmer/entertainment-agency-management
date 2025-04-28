package com.agency.generator;

import com.agency.documentcontext.doccontext.DocContextType;
import com.agency.documentcontext.doccontext.DocumentContext;
import com.agency.documentcontext.doccontext.GenerationResult;
import com.agency.generator.service.FileWriterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
public abstract class DocGenerator {

    protected final String docStaticFilePath;
    protected final FileWriterService fileWriterService;
    protected final GenerationResult generationResult = new GenerationResult();

    public DocGenerator(String docStaticFilePath, FileWriterService fileWriterService) {
        this.docStaticFilePath = docStaticFilePath.endsWith("/") ? docStaticFilePath : docStaticFilePath + "/";
        this.fileWriterService = fileWriterService;
    }

    public abstract GenerationResult generate(DocumentContext context);

    protected String createOutputFile(DocumentContext context, XWPFDocument document, FileInputStream fis) throws IOException {
        String fileName;

        final String operationId = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        log.info("Try to save generated document with operationId: {}", operationId);
        final String date = context.getContractWork().startDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        if (generationResult.isSuccess()) {
            fileName = String.format("%s_%s_%s-%s.docx", context.getContractor().name(),
                    context.getContractor().lastName(), date, operationId);
            fileWriterService.write(fileName, context.getDocContextType(), document);
        } else {
            fileName = String.format("%s_%s_%s-%s-error.txt",
                    context.getContractor().name(),
                    context.getContractor().lastName(),
                    date,
                    operationId);
            setFinalErrorLog(context);
            fileWriterService.writeErrorLog(fileName,
                    DocContextType.CONTRACT_WORK, generationResult.getErrorLogs());
            generationResult.setSuccess(Boolean.FALSE);
        }
        document.close();
        fis.close();
        return fileName;
    }

    protected void setFinalErrorLog(DocumentContext context) {
        StringBuilder availablePlaceholders = new StringBuilder();
        for (String key : context.getPlaceholders().keySet()) {
            availablePlaceholders.append(key).append(System.lineSeparator());
        }
        generationResult.getErrorLogs().add("Available context placeholders: " + availablePlaceholders);
    }
}
