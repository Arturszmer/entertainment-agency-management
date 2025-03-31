package com.agency.documents.model;

import com.agency.documentcontext.templatecontext.TemplateContext;

import java.util.UUID;

public class TemplateDocumentBuilder {

    private String filename;
    private UUID referenceId;
    private String templateName;
    private boolean isDefault;
    private TemplateContext templateContext;
    private String htmlContent;

    public static TemplateDocumentBuilder aTemplateDocument() {
        return new TemplateDocumentBuilder();
    }

    public TemplateDocumentBuilder withFilename(String filename) {
        this.filename = filename;
        return this;
    }
    public TemplateDocumentBuilder withReferenceId(UUID referenceId) {
        this.referenceId = referenceId;
        return this;
    }
    public TemplateDocumentBuilder withTemplateName(String templateName) {
        this.templateName = templateName;
        return this;
    }
    public TemplateDocumentBuilder withDefault(boolean isDefault) {
        this.isDefault = isDefault;
        return this;
    }
    public TemplateDocumentBuilder withTemplateContext(TemplateContext templateContext) {
        this.templateContext = templateContext;
        return this;
    }

    public TemplateDocumentBuilder withHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
        return this;
    }

    public TemplateDocument build() {
        return new TemplateDocument(filename, referenceId, templateName, isDefault, templateContext, htmlContent);
    }

}
