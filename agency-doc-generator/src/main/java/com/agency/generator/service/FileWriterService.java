package com.agency.generator.service;

import com.agency.documentcontext.doccontext.DocContextType;
import com.agency.exception.AgencyException;
import com.agency.exception.DocumentErrorResult;
import com.agency.generator.DirectoryGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
@Slf4j
public class FileWriterService {

    private static final String SUCCESSFUL_LOG_MESSAGE = "Document with name {} has been saved successfully";
    private final String DOC_STATIC_FILE_PATH;

    public FileWriterService(@Value("${doc-static-file-path}") String outputDirectory) {
        DOC_STATIC_FILE_PATH = outputDirectory;
    }

    public void write(String fileName, DocContextType context, XWPFDocument document) {
        try {
            String documentOutputPath = generateDirectoryPath(DOC_STATIC_FILE_PATH, context);
            OutputStream out = new FileOutputStream(documentOutputPath + fileName);
            document.write(out);
            logSuccessfullyOperation(fileName);
        } catch (IOException e) {
            throw new AgencyException(DocumentErrorResult.DOCUMENT_FILE_WRITE_ERROR);
        }
    }

    public String write(DocContextType context, MultipartFile file) {
        try {
            Path documentOutputPath = new DirectoryGenerator(DOC_STATIC_FILE_PATH, context.toString()).getDirectory();
            String originalFilename = file.getOriginalFilename();

            assert originalFilename != null;

            Path filePath = documentOutputPath.resolve(originalFilename);
            Files.write(filePath, file.getBytes());
            logSuccessfullyOperation(originalFilename);
            return originalFilename;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new AgencyException(DocumentErrorResult.DOCUMENT_FILE_WRITE_ERROR);
        }
    }

    public void writeErrorLog(String fileName, DocContextType context, List<String> errorLogs) {
        try {
            String outputPath = generateDirectoryPath(DOC_STATIC_FILE_PATH, context);
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

    public String update(DocContextType context, MultipartFile file, String existingFileName) {
        try {
            Path directoryPath = new DirectoryGenerator(DOC_STATIC_FILE_PATH, context.toString()).getDirectory();
            Path existingFilePath = directoryPath.resolve(existingFileName);
            if (Files.exists(existingFilePath)) {
                Files.delete(existingFilePath);
            }
            return write(context, file);
        } catch (IOException e){
            log.error(e.getMessage(), e);
            throw new AgencyException(DocumentErrorResult.DOCUMENT_FILE_WRITE_ERROR);
        }
    }

    private String generateDirectoryPath(String docStaticFilePath, DocContextType contextType) throws IOException {
        return new DirectoryGenerator(docStaticFilePath, contextType.toString()).getContextDirectory();
    }

    private static void logSuccessfullyOperation(String originalFilename) {
        log.info(SUCCESSFUL_LOG_MESSAGE, originalFilename);
    }
}
