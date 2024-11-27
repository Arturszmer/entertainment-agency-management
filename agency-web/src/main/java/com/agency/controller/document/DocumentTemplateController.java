package com.agency.controller.document;

import com.agency.documentcontext.templatecontext.TemplateContext;
import com.agency.dto.document.TemplateDocumentDto;
import com.agency.service.DocumentTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
//@PreAuthorize("hasAuthority('DOCUMENT_MANAGEMENT')")
@RequestMapping("api/v1/document/template")
@RequiredArgsConstructor
public class DocumentTemplateController {

    private final DocumentTemplateService templateService;

    @PostMapping
    ResponseEntity<TemplateDocumentDto> addTemplate(@RequestParam("file") MultipartFile file,
                                                    @RequestParam("template-name") String templateName,
                                                    @RequestParam("is-default") boolean isDefault,
                                                    @RequestParam("template-context") TemplateContext templateContext) {

        return ResponseEntity.ok(templateService.saveDocumentTemplate(file, templateName, isDefault, templateContext));
    }

    @PutMapping
    ResponseEntity<String> updateTemplate(@RequestParam("file") MultipartFile file, @RequestParam("reference-id") String referenceId) {
        try {
            templateService.updateDocumentTemplate(file, referenceId);
            return ResponseEntity.status(HttpStatus.OK).body("Template updated successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to update template");
        }
    }
}
