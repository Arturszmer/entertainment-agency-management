package com.agency.documents.service;

import com.agency.documents.repository.TemplateDocumentRepository;
import com.agency.exception.AgencyException;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DocumentTemplateValidatorTest {

    private final TemplateDocumentRepository repository = mock(TemplateDocumentRepository.class);

    @Test
    void shouldThrowAnException_whenFileIsEmpty() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> DocumentTemplateValidator.validateFile(file));
        assertEquals("File must not be empty", illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowAnException_whenFilenameIsNull() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn(null);
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> DocumentTemplateValidator.validateFile(file));
        assertEquals("Filename must not be null", illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowAnException_whenFileHasWrongExtension() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("template.txt");
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> DocumentTemplateValidator.validateFile(file));
        assertEquals("Only DOCX files are supported", illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowAnException_whenFilenameIsNotUnique() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("template.docx");
        when(repository.existsTemplateDocumentByFileName(file.getOriginalFilename())).thenReturn(true);
        String templateName = "template";
        AgencyException agencyException = assertThrows(AgencyException.class,
                () -> new DocumentTemplateValidator(templateName, repository).validateFilenameMustBeUnique(file));
        assertEquals("ADT05", agencyException.getErrorCode());
    }

    @Test
    void shouldThrowAnException_whenTemplateNameIsNotUnique() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("template.docx");
        String templateName = "template";
        when(repository.existsTemplateDocumentByTemplateName(templateName)).thenReturn(true);
        AgencyException agencyException = assertThrows(AgencyException.class,
                () -> new DocumentTemplateValidator(templateName, repository).validateTemplateNameMustBeUnique());
        assertEquals("ADT02", agencyException.getErrorCode());
    }

    @Test
    public void shouldThrowAnException_whenTemplateNameExistsInOtherTemplate() {
        String templateName = "template";
        UUID referenceId = UUID.randomUUID();
        when(repository.existsTemplateDocumentByFileNameAndReferenceIdIsNot(templateName, referenceId)).thenReturn(true);
        AgencyException agencyException = assertThrows(AgencyException.class,
                () -> new DocumentTemplateValidator(templateName, repository).validateIsFilenameExistsInOtherTemplate(templateName, referenceId));
        assertEquals("ADT07", agencyException.getErrorCode());

    }
}
