package com.agency.generator;

import lombok.Getter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }

        return directory;
    }

    public String getContextDirectory() {
        return directory + "/";
    }
}
