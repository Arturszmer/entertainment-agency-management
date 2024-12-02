package com.agency.generator;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class DirectoryGenerator {

    private final String outputPath;
    private final String contextDirectory;
    @Getter
    private Path directory;

    public DirectoryGenerator(final String outputPath, final String contextDirectory) throws IOException {
        this.outputPath = outputPath;
        this.contextDirectory = contextDirectory.endsWith("/") ? contextDirectory : contextDirectory + "/";
        directory = generate();
    }

    private Path generate() throws IOException {
        directory = Paths.get(outputPath + contextDirectory);
        log.info("Generate directory: {}", directory);
        if (!Files.exists(directory)) {
            Path created = Files.createDirectories(directory);
            log.info("Created directory: {}", created);
        }

        return directory;
    }

    public String getContextDirectory() {
        return directory + "/";
    }
}
