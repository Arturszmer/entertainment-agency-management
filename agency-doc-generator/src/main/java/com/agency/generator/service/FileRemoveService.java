package com.agency.generator.service;

import com.agency.generator.DirectoryGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Slf4j
public class FileRemoveService {

    private final String DOC_STATIC_FILE_PATH;

    public FileRemoveService(@Value("${doc-static-file-path}") String docStaticFilePath) {
        DOC_STATIC_FILE_PATH = docStaticFilePath;
    }

    public void removeFile(String documentFileName, String docContextType) {
        try {
            Path directoryPath = new DirectoryGenerator(DOC_STATIC_FILE_PATH, docContextType).getDirectory();
            Path existingFile = directoryPath.resolve(documentFileName);
            if(Files.exists(existingFile)) {
                Files.delete(existingFile);
                log.info("Deleted contractor document file: {}", documentFileName);
            } else {
                log.warn("File not found: {}", existingFile);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
