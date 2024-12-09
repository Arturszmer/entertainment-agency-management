package com.agency.controller.document;

import com.agency.documentcontext.templatecontext.TemplateContext;
import com.agency.dto.document.TemplateDocumentDto;
import com.agency.service.DocumentTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@PreAuthorize("hasAuthority('DOCUMENT_MANAGEMENT')")
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
    ResponseEntity<Void> updateTemplate(@RequestParam("file") MultipartFile file,
                                        @RequestParam("reference-id") String referenceId,
                                        @RequestParam("template-name") String templateName) {
        try {
            templateService.updateDocumentTemplate(file, referenceId, templateName);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/update/template-name")
    ResponseEntity<Void> updateTemplateName(@RequestParam("reference-id") String referenceId,
                                            @RequestParam("template-name") String templateName){
        templateService.updateDocumentTemplateName(referenceId, templateName);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/set-default/{reference-id}")
    ResponseEntity<Void> setDefault(@PathVariable("reference-id") String referenceId) {
        templateService.setAsDefault(referenceId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @DeleteMapping("/{reference-id}")
    ResponseEntity<Void> delete(@PathVariable("reference-id") String referenceId) {
        templateService.removeTemplateDocument(referenceId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/download/{filename}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> downloadFile(
            @PathVariable("filename") String filename) {
        String encodedFileName = URLEncoder.encode(filename, StandardCharsets.UTF_8).replace("+", "%20");
        Resource fileResource = templateService.downloadDocumentTemplate(filename);


        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + filename.replaceAll("[^\\x20-\\x7E]", "") + "\"; filename*=UTF-8''" + encodedFileName)
                .body(fileResource);

    }

}
