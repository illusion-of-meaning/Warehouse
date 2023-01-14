package com.asherbakov.warehouse.services.impl;

import com.asherbakov.warehouse.services.FileService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String readFile(Path path) throws IOException {
        if (!Files.exists(path)) {
            cleanJsonFile(path);
        }
        return Files.readString(path);
    }

    @Override
    public boolean writeFile(Path path, String str) throws IOException {
        Files.writeString(path, str);
        return true;
    }

    @Override
    public void cleanJsonFile(Path path) {
        try {
            Files.deleteIfExists(path);
            Files.writeString(path, "{}");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public File getDataFile(Path path) {
        return path.toFile();
    }

    @Override
    public File getDataFile(String path) {
        Path p = Path.of(path);
        return getDataFile(p);
    }
}
