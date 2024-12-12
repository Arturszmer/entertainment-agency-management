package com.agency.service;

import com.agency.documentcontext.templatecontext.TemplateContext;
import com.agency.dto.document.TemplateDocumentDto;

import java.util.List;
import java.util.Set;

public interface DocumentTemplateSearchService {

    List<TemplateDocumentDto> getAll();

    Set<String> getTemplatesNamesByContext(TemplateContext templateContext);
}
