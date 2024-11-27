package com.agency.documents.repository;

import com.agency.documentcontext.templatecontext.TemplateContext;
import com.agency.documents.model.TemplateDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TemplateDocumentRepository extends JpaRepository<TemplateDocument, Long> {

    Optional<TemplateDocument> findByTemplateName(String templateName);
    Optional<TemplateDocument> findByReferenceId(UUID referenceId);
    List<TemplateDocument> findAllTemplateDocumentsByTemplateContext(TemplateContext templateContext);
}
