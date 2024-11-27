package com.agency.documents.service;

import com.agency.documentcontext.templatecontext.TemplateContext;
import com.agency.documents.model.TemplateDocument;
import com.agency.documents.repository.TemplateDocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultDocumentTemplateResolver {

    private final TemplateDocumentRepository templateDocumentRepository;

    public void clearDefaultTemplate(TemplateContext templateContext) {
        List<TemplateDocument> allByContextType = templateDocumentRepository.findAllTemplateDocumentsByTemplateContext(templateContext);
        allByContextType.stream()
                .filter(TemplateDocument::isDefault)
                .findFirst().ifPresent(template -> {
                    template.setDefault(false);
                    log.info("Default document template with reference id {} has been changed to false.", template.getReferenceId());
                    templateDocumentRepository.save(template);
                });
    }
}
