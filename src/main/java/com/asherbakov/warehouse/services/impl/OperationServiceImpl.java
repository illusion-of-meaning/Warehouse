package com.asherbakov.warehouse.services.impl;

import com.asherbakov.warehouse.models.Operation;
import com.asherbakov.warehouse.services.FileService;
import com.asherbakov.warehouse.services.OperationService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class OperationServiceImpl implements OperationService {
    @Value("${operations.data.file}")
    private String operationsDataFile;
    Path operationsDataFilePath;

    public static List<Operation> operationList = new ArrayList<>();
    private final FileService fileService;

    public OperationServiceImpl(FileService fileService) {
        this.fileService = fileService;
    }

    @PostConstruct
    private void init() {
        operationsDataFilePath = Path.of(operationsDataFile);
        loadDataFromFile();
    }

    @Override
    public void addOperation(Operation operation) {
        operationList.add(operation);
        saveDataToFile();
    }

    @Override
    public void cleanOperations() {
        operationList.clear();
        saveDataToFile();
    }

    private void saveDataToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(operationList);
            fileService.writeFile(operationsDataFilePath, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadDataFromFile() {
        try {
            String json = fileService.readFile(operationsDataFilePath);
            operationList = new ObjectMapper().readValue(json, new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
