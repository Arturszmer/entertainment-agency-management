package com.agency.documents.model;

import com.agency.documentcontext.doccontext.DocContextType;
import com.agency.documentcontext.templatecontext.TemplateContext;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@DiscriminatorValue("TEMPLATE")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class TemplateDocument extends Document {

    @Column(name = "template_name")
    private String templateName;

    @Column(name = "is_default")
    @Setter
    private boolean isDefault;

    @Enumerated(EnumType.STRING)
    @Column(name = "template_context")
    private TemplateContext templateContext ;

    public TemplateDocument(String filename, String templateName, boolean isDefault, TemplateContext templateContext) {
        super(filename);
        this.templateName = templateName;
        this.isDefault = isDefault;
        this.templateContext = templateContext;
    }

    TemplateDocument(String filename, UUID referenceId, String templateName, boolean isDefault, TemplateContext templateContext) {
        super(filename, referenceId);
        this.templateName = templateName;
        this.isDefault = isDefault;
        this.templateContext = templateContext;
    }

    @Override
    public DocContextType getContextType() {
        return DocContextType.TEMPLATE;
    }
}
