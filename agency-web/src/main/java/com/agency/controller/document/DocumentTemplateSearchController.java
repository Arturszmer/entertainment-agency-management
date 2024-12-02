package com.agency.controller.document;

import com.agency.dto.document.TemplateDocumentDto;
import com.agency.service.DocumentTemplateSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@PreAuthorize("hasAuthority('DOCUMENT_MANAGEMENT')")
@RequestMapping("api/v1/document/template")
@RequiredArgsConstructor
public class DocumentTemplateSearchController {

    private final DocumentTemplateSearchService searchService;

    @GetMapping
    ResponseEntity<List<TemplateDocumentDto>> getAll(){
        return ResponseEntity.ok(searchService.getAll());
    }
}