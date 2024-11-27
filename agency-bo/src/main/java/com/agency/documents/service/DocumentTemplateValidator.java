package com.agency.documents.service;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public class DocumentTemplateValidator {

    public static void validate(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        if (!Objects.requireNonNull(file.getOriginalFilename()).endsWith(".docx")) {
            throw new IllegalArgumentException("Only DOCX files are supported");
        }

        String originalFilename = file.getOriginalFilename();
        if(!StringUtils.hasText(originalFilename)){
            throw new IllegalArgumentException("Filename is empty");
        }
    }
}
