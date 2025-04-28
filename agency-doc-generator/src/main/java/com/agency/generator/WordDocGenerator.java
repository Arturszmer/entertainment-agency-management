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

@Slf4j
public class WordDocGenerator extends DocGenerator {

    private final String templateFilename;

    public WordDocGenerator(String docStaticFilePath,
                            FileWriterService fileWriterService,
                            String templateFilename) {
        super(docStaticFilePath, fileWriterService);
        this.templateFilename = templateFilename;
    }

    @Override
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
}
