package com.agency.documents.service;

import com.agency.documentcontext.templatecontext.TemplateContext;
import com.agency.documents.model.TemplateDocument;
import com.agency.documents.repository.TemplateDocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
class DefaultDocumentTemplateResolver {

    private final TemplateDocumentRepository templateDocumentRepository;

    boolean resolveIsDefaultAttribute(boolean isDefault, TemplateContext templateContext) {
        if(isDefault){
            clearDefaultTemplate(templateContext);
            return true;
        } else {
            return setTrueIfNoneExists(templateContext);
        }
    }

    void setLatestModifiedTemplateAsDefault(TemplateContext templateContext) {
        List<TemplateDocument> allByContextType = templateDocumentRepository.findAllTemplateDocumentsByTemplateContext(templateContext);
        boolean defaultTemplateIsNotSet = allByContextType.stream()
                .noneMatch(TemplateDocument::isDefault);
        if(defaultTemplateIsNotSet) {
            allByContextType.stream()
                    .max(Comparator.comparing(TemplateDocument::getModificationTimestamp))
                    .ifPresent(template -> {
                        template.setDefault(true);
                        templateDocumentRepository.save(template);
                    });
        }
    }

    void clearDefaultTemplate(TemplateContext templateContext) {
        List<TemplateDocument> allByContextType = templateDocumentRepository.findAllTemplateDocumentsByTemplateContext(templateContext);
        allByContextType.stream()
                .filter(TemplateDocument::isDefault)
                .findFirst().ifPresent(template -> {
                    template.setDefault(false);
                    log.info("Default document template with reference id {} has been changed to false.", template.getReferenceId());
                    templateDocumentRepository.save(template);
                });
    }

    boolean setTrueIfNoneExists(TemplateContext templateContext) {
        return templateDocumentRepository.findAllTemplateDocumentsByTemplateContext(templateContext)
                .stream().filter(TemplateDocument::isDefault)
                .findFirst().isEmpty();
    }
}
