package com.agency.documents.service;

import com.agency.documents.assembler.DocumentTemplateAssembler;
import com.agency.documents.model.TemplateDocument;
import com.agency.documents.repository.TemplateDocumentRepository;
import com.agency.dto.document.TemplateDocumentDto;
import com.agency.service.DocumentTemplateSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentTemplateSearchServiceImpl implements DocumentTemplateSearchService {

    private final TemplateDocumentRepository templateDocumentRepository;

    @Override
    public List<TemplateDocumentDto> getAll() {
        List<TemplateDocument> templates = templateDocumentRepository.findAll();

        return templates.stream()
                .map(DocumentTemplateAssembler::toDto)
                .toList();
    }
}
