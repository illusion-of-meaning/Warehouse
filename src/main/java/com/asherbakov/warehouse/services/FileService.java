package com.asherbakov.warehouse.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public interface FileService {

    String readFile(Path path) throws IOException;

    boolean writeFile(Path path, String str) throws IOException;

    void cleanJsonFile(Path path);

    File getDataFile(Path path);

    File getDataFile(String path);
}
