package com.asherbakov.warehouse.services;

import java.io.File;
import java.nio.file.Path;

public interface FileService {

    String readFile(Path path);

    boolean writeFile(Path path, String str);

    void cleanJsonFile(Path path);

    File getDataFile(Path path);

    File getDataFile(String path);
}
