package com.agency.generator.service;

import com.agency.documentcontext.doccontext.DocContextType;
import com.agency.generator.DirectoryGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileWriterService {

    private static final String SUCCESSFUL_LOG_MESSAGE = "Document with name {} has been saved successfully";

    public void write(String docStaticFilePath, String fileName, DocContextType context, XWPFDocument document) {
        try {
            String documentOutputPath = generateDirectoryPath(docStaticFilePath, context);
            OutputStream out = new FileOutputStream(documentOutputPath + fileName);
            document.write(out);
            logSuccessfullyOperation(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<String> write(String outputPath, DocContextType context, MultipartFile file) {
        try {
            Path documentOutputPath = new DirectoryGenerator(outputPath, context.toString()).getDirectory();
            String originalFilename = file.getOriginalFilename();

            assert originalFilename != null;

            Path filePath = documentOutputPath.resolve(originalFilename);
            Path saved = Files.write(filePath, file.getBytes());
            logSuccessfullyOperation(originalFilename);
            log.info("FILES WRITE: {}", saved.getFileName());
            return Optional.of(originalFilename);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    public void writeErrorLog(String docStaticFilePath, String fileName, DocContextType context, List<String> errorLogs) {
        try {
            String outputPath = generateDirectoryPath(docStaticFilePath, context);
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath + fileName));
            writer.write("Error Logs:");
            writer.newLine();

            for (String log : errorLogs) {
                writer.write(log);
                writer.newLine();
            }
            log.info("Document had some issues, document with logs has been saved successfully, filename: {}", fileName);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public Optional<String> update(String outputPath, DocContextType context, MultipartFile file, String existingFileName) {
        try {
            Path directoryPath = new DirectoryGenerator(outputPath, context.toString()).getDirectory();
            Path existingFilePath = directoryPath.resolve(existingFileName);
            if (Files.exists(existingFilePath)) {
                Files.delete(existingFilePath);
            }
            return write(file.getOriginalFilename(), context, file);
        } catch (IOException e){
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    private String generateDirectoryPath(String docStaticFilePath, DocContextType contextType) throws IOException {
        return new DirectoryGenerator(docStaticFilePath, contextType.toString()).getContextDirectory();
    }

    private static void logSuccessfullyOperation(String originalFilename) {
        log.info(SUCCESSFUL_LOG_MESSAGE, originalFilename);
    }
}
