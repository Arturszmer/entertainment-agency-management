package com.agency.controller.contract;

import com.agency.documentcontext.doccontext.DocContextType;
import com.agency.documentcontext.doccontext.GenerationResult;
import com.agency.contractmanagement.service.ContractWorkDocumentRemove;
import com.agency.service.ContractFileGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasAuthority('CONTRACT_MANAGEMENT')")
@RequestMapping("api/v1/contract-work/document")
@RequiredArgsConstructor
public class ContractDocumentController {

    private final ContractFileGeneratorService generatorService;
    private final ContractWorkDocumentRemove contractWorkDocumentRemove;

    @PostMapping("/generate/{public-id}")
    ResponseEntity<GenerationResult> generate(@PathVariable("public-id") String contractPublicId, @RequestParam("template-name") String templateName){
        return ResponseEntity.ok(generatorService.generate(contractPublicId, templateName, DocContextType.CONTRACT_WORK));
    }

    @DeleteMapping("/{public-id}")
    ResponseEntity<Void> delete(@PathVariable("public-id") String contractorPublicId){
        contractWorkDocumentRemove.removeDocument(contractorPublicId);
        return ResponseEntity.ok().build();
    }
}
