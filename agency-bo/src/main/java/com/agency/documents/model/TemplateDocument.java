package com.agency.documents.model;

import com.agency.documentcontext.doccontext.DocContextType;
import com.agency.documentcontext.templatecontext.TemplateContext;
import com.agency.dto.document.TemplateDocumentUpdateRequest;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@DiscriminatorValue("TEMPLATE")
@NoArgsConstructor
@Getter
public class TemplateDocument extends Document {

    @Column(name = "template_name")
    @Setter
    private String templateName;

    @Column(name = "is_default")
    @Setter
    private boolean isDefault;

    @Column(name = "html_content", columnDefinition = "TEXT")
    private String htmlContent;

    @Enumerated(EnumType.STRING)
    @Column(name = "template_context")
    private TemplateContext templateContext;

    public TemplateDocument(String filename,
                            String templateName,
                            boolean isDefault,
                            TemplateContext templateContext) {
        super(filename);
        this.templateName = templateName;
        this.isDefault = isDefault;
        this.templateContext = templateContext;
    }

    public TemplateDocument(String templateName,
                            boolean isDefault,
                            String htmlContent,
                            TemplateContext templateContext) {
        super();
        this.templateName = templateName;
        this.isDefault = isDefault;
        this.htmlContent = htmlContent;
        this.templateContext = templateContext;
    }



    TemplateDocument(String filename,
                     UUID referenceId,
                     String templateName,
                     boolean isDefault,
                     TemplateContext templateContext,
                     String htmlContent) {
        super(filename, referenceId);
        this.templateName = templateName;
        this.isDefault = isDefault;
        this.templateContext = templateContext;
        this.htmlContent = htmlContent;
    }

    public void update(TemplateDocumentUpdateRequest updateRequest) {
        this.templateName = updateRequest.templateName();
        this.isDefault = updateRequest.isDefault();
        this.htmlContent = updateRequest.htmlContent();
    }

    @Override
    public DocContextType getContextType() {
        return DocContextType.TEMPLATE;
    }
}
