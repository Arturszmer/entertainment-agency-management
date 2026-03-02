package com.agency.generator;

import com.agency.documentcontext.doccontext.DocContextType;
import com.agency.documentcontext.doccontext.DocumentContext;
import com.agency.documentcontext.doccontext.GenerationResult;
import com.agency.generator.service.FileWriterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

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
        XWPFDocument document = generateDocument(replacedText);

        String outputFile = createOutputFile(context, document);
        GenerationResult generationResult = new GenerationResult();
        generationResult.setFilename(outputFile);
        generationResult.setContent(replacedText);
        return generationResult;
    }

    XWPFDocument generateDocument(String html) {
        XWPFDocument document = new XWPFDocument();
        // Parse the HTML
        Document htmlDoc = Jsoup.parse(html);

        // Process block-level elements in the body
        processHtmlElements(htmlDoc.body().children(), document);
        return document;
    }

    // Process block-level (paragraphs, headings, lists) elements.
    private void processHtmlElements(Elements elements, XWPFDocument document) {
        for (Element element : elements) {
            switch (element.tagName()) {
                case "p": {
                    XWPFParagraph paragraph = document.createParagraph();
                    applyAlignment(element, paragraph);
                    // Process inline sub-elements with default formatting (none applied)
                    processInlineElements(element, paragraph, false, false, false);
                    break;
                }
                case "ol": {
                    int counter = 1;
                    // For ordered lists, process each <li> and prefix with a number.
                    for (Element li : element.children()) {
                        if (li.tagName().equalsIgnoreCase("li")) {
                            XWPFParagraph liParagraph = document.createParagraph();
                            // Apply default indentation for list items (360 twips left, 360 hanging for the number)
                            liParagraph.setIndentationLeft(720);
                            liParagraph.setIndentationHanging(360);

                            XWPFRun numRun = liParagraph.createRun();
                            numRun.setText(counter++ + ". ");
                            processInlineElements(li, liParagraph, false, false, false);
                        }
                    }
                    break;
                }
                case "ul": {
                    // For unordered lists, prefix with a bullet (or dash)
                    for (Element li : element.children()) {
                        if (li.tagName().equalsIgnoreCase("li")) {
                            XWPFParagraph liParagraph = document.createParagraph();
                            // Apply default indentation for list items
                            liParagraph.setIndentationLeft(720);
                            liParagraph.setIndentationHanging(360);

                            XWPFRun bulletRun = liParagraph.createRun();
                            bulletRun.setText("- ");
                            processInlineElements(li, liParagraph, false, false, false);
                        }
                    }
                    break;
                }
                case "h1": {
                    XWPFParagraph h1Para = document.createParagraph();
                    h1Para.setStyle("Heading1");
                    applyAlignment(element, h1Para);
                    // Assume headings should be bold – set bold flag to true.
                    processInlineElements(element, h1Para, true, false, false);
                    break;
                }
                // You can handle other headings or block-level tags similarly.
                default: {
                    // If the tag is not explicitly handled as a block element, treat it as a paragraph.
                    XWPFParagraph defaultPara = document.createParagraph();
                    applyAlignment(element, defaultPara);
                    processInlineElements(element, defaultPara, false, false, false);
                    break;
                }
            }
        }
    }

    private void applyAlignment(Element element, XWPFParagraph paragraph) {
        if (element.hasClass("ql-align-center")) {
            paragraph.setAlignment(ParagraphAlignment.CENTER);
        } else if (element.hasClass("ql-align-right")) {
            paragraph.setAlignment(ParagraphAlignment.RIGHT);
        } else if (element.hasClass("ql-align-justify")) {
            paragraph.setAlignment(ParagraphAlignment.BOTH);
        }

        // Apply indentation (Quill uses ql-indent-1, ql-indent-2, etc.)
        for (String className : element.classNames()) {
            if (className.startsWith("ql-indent-")) {
                try {
                    int level = Integer.parseInt(className.substring("ql-indent-".length()));
                    // Standard indentation in Word is 720 twips (0.5 inch) per level
                    paragraph.setIndentationLeft(level * 720);
                } catch (NumberFormatException ignored) {}
            }
        }
    }

    /**
     * Recursively process inline HTML nodes.
     *
     * @param element      The current element whose children are processed.
     * @param paragraph    The XWPFParagraph in which runs will be created.
     * @param isBold       True if the current formatting includes bold.
     * @param isItalic     True if the current formatting includes italic.
     * @param isUnderline  True if the current formatting includes underline.
     */
    private void processInlineElements(Element element, XWPFParagraph paragraph,
                                       boolean isBold, boolean isItalic, boolean isUnderline) {
        for (Node node : element.childNodes()) {
            if (node instanceof TextNode textNode) {
                // getWholeText() preserves literal tabs and newlines, unlike text() which normalizes spaces.
                    String text = textNode.getWholeText();
                    if (!text.isEmpty()) {
                        // Word and browser collapse multiple spaces. 
                        // Replacing some spaces with non-breaking spaces (\u00A0) ensures gaps are preserved.
                        // We replace double spaces with one non-breaking space and one regular space repeatedly.
                        String preservedText = text;
                        // Replace multiple regular spaces with alternating non-breaking and regular spaces
                        // so they don't get collapsed by Word. 
                        // Note: TemplateSanitizer already converts &nbsp; to \u00A0.
                        while (preservedText.contains("  ")) {
                            preservedText = preservedText.replace("  ", "\u00A0 ");
                        }
                        
                        // Handle tabs and regular text.
                        // We use a single run for consecutive tabs if possible, or just add them to the current run.
                        String[] parts = preservedText.split("\t", -1);
                        XWPFRun currentRun = null;
                        
                        for (int i = 0; i < parts.length; i++) {
                            if (i > 0) {
                                if (currentRun == null) {
                                    currentRun = paragraph.createRun();
                                    applyFormatting(currentRun, isBold, isItalic, isUnderline);
                                }
                                currentRun.addTab();
                            }
                            
                            if (!parts[i].isEmpty()) {
                                currentRun = paragraph.createRun();
                                applyFormatting(currentRun, isBold, isItalic, isUnderline);
                                currentRun.getCTR().addNewT().setStringValue(parts[i]);
                                currentRun.getCTR().getTList().get(currentRun.getCTR().getTList().size()-1)
                                        .setSpace(org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute.Space.PRESERVE);
                            }
                        }
                    }
            } else if (node instanceof Element childElement) {
                String tagName = childElement.tagName();
                if (tagName.equalsIgnoreCase("br")) {
                    XWPFRun run = paragraph.createRun();
                    run.addBreak();
                } else if (tagName.equalsIgnoreCase("strong") || tagName.equalsIgnoreCase("b")) {
                    // New effective bold value is true regardless of current.
                    processInlineElements(childElement, paragraph, true, isItalic, isUnderline);
                } else if (tagName.equalsIgnoreCase("i") || tagName.equalsIgnoreCase("em")) {
                    processInlineElements(childElement, paragraph, isBold, true, isUnderline);
                } else if (tagName.equalsIgnoreCase("u")) {
                    processInlineElements(childElement, paragraph, isBold, isItalic, true);
                } else {
                    // For other inline elements (or nested spans) without specific formatting,
                    // continue with the parent's formatting.
                    processInlineElements(childElement, paragraph, isBold, isItalic, isUnderline);
                }
            }
        }
    }

    private void applyFormatting(XWPFRun run, boolean isBold, boolean isItalic, boolean isUnderline) {
        run.setBold(isBold);
        run.setItalic(isItalic);
        if (isUnderline) {
            run.setUnderline(UnderlinePatterns.SINGLE);
        }
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
