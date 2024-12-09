package com.agency.generator.service;

import com.agency.documentcontext.doccontext.DocContextType;
import com.agency.exception.AgencyException;
import com.agency.exception.DocumentErrorResult;
import com.agency.generator.DirectoryGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Slf4j
public class FileDownloadService {

    private final String DOC_STATIC_FILE_PATH;

    public FileDownloadService(@Value("${doc-static-file-path}") String DOC_STATIC_FILE_PATH) {
        this.DOC_STATIC_FILE_PATH = DOC_STATIC_FILE_PATH;
    }

    public Resource downloadFile(String fileName, DocContextType context, String username) {
        try {
            log.info("User {} Downloading file: {}", username, fileName);
            Path directoryPath = new DirectoryGenerator(DOC_STATIC_FILE_PATH, context.toString()).getDirectory();
            Path existingFilePath = directoryPath.resolve(fileName);
            if (!Files.exists(existingFilePath) || !Files.isRegularFile(existingFilePath)) {
                throw new AgencyException(DocumentErrorResult.DOCUMENT_NOT_FOUND);
            }
            return new UrlResource(existingFilePath.toUri());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
