package com.agency.controller.contract;

import com.agency.contractmanagement.service.ContractWorkDocumentServiceImpl;
import com.agency.documentcontext.doccontext.GenerationResult;
import com.agency.dto.contractwork.DocumentGenerateRequest;
import com.agency.service.ContractDocumentGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@PreAuthorize("hasAuthority('CONTRACT_MANAGEMENT')")
@RequestMapping("api/v1/contract-work/document")
@RequiredArgsConstructor
public class ContractDocumentController {

    private final ContractDocumentGeneratorService generatorService;
    private final ContractWorkDocumentServiceImpl contractWorkDocumentService;

    @PostMapping("/generate")
    ResponseEntity<GenerationResult> generate(@RequestBody DocumentGenerateRequest documentGenerateRequest){
        return ResponseEntity.ok(generatorService.generate(documentGenerateRequest));
    }

    @DeleteMapping("/{public-id}")
    ResponseEntity<Void> delete(@PathVariable("public-id") String contractorPublicId){
        contractWorkDocumentService.removeDocument(contractorPublicId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/download/{filename}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> downloadFile(
            @PathVariable("filename") String filename) {
        String encodedFileName = URLEncoder.encode(filename, StandardCharsets.UTF_8).replace("+", "%20");
        Resource fileResource = contractWorkDocumentService.downloadDocument(filename);


        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + filename.replaceAll("[^\\x20-\\x7E]", "") + "\"; filename*=UTF-8''" + encodedFileName)
                .body(fileResource);

    }
}
