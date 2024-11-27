package com.agency.service;

import com.agency.dto.document.TemplateDocumentDto;

import java.util.List;

public interface DocumentTemplateSearchService {

    List<TemplateDocumentDto> getAll();
}
