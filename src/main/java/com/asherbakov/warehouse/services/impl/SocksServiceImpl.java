package com.asherbakov.warehouse.services.impl;

import com.asherbakov.warehouse.models.Operation;
import com.asherbakov.warehouse.models.Socks;
import com.asherbakov.warehouse.models.enums.Color;
import com.asherbakov.warehouse.models.enums.OperationType;
import com.asherbakov.warehouse.models.enums.Size;
import com.asherbakov.warehouse.services.FileService;
import com.asherbakov.warehouse.services.OperationService;
import com.asherbakov.warehouse.services.SocksService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class SocksServiceImpl implements SocksService {
    @Value("${socks.data.file}")
    private String socksDataFile;
    Path socksDataFilePath;
    private final FileService fileService;
    private final OperationService operationService;
    public SocksServiceImpl(FileService fileService, OperationService operationService) {
        this.fileService = fileService;
        this.operationService = operationService;
    }
    private static Long id = 0L;
    public static Map<Long, Socks> socksMap = new HashMap<>();

    @PostConstruct
    private void init() {
        socksDataFilePath = Path.of(socksDataFile);
        loadDataFromFile();
    }

    @Override
    public String getAllSocks() {
        StringBuilder result = new StringBuilder();
        socksMap.forEach((K, V) -> result.append(V.toString()));
        return result.toString();
    }

    @Override
    public int getSocksCount(Color color, Size size, int cottonMin, int cottonMax) {
        int count = 0;
        for (Map.Entry<Long, Socks> entry : socksMap.entrySet()) {
            if (entry.getValue().getColor().equals(color) &&
                    entry.getValue().getSize().equals(size) &&
                    entry.getValue().getCottonPercent() >= cottonMin &&
                    entry.getValue().getCottonPercent() <= cottonMax) {
                count += entry.getValue().getQuantity();
            }
        }
        return count;
    }

    @Override
    public void addSocks(Socks socks) {
        if (!socksMap.containsValue(socks)) {
            socksMap.put(id++, socks);
        } else {
            Long tempId = getId(socks);
            changeQuantitySocks(tempId, socks, OperationType.ADD);
        }

        operationService.addOperation(new Operation(OperationType.ADD, getDateTimeString(), socks));
        saveDataToFile();
    }

    @Override
    public boolean takeOutSocks(Socks socks, OperationType type) {
        if (socksMap.containsValue(socks)) {
            Long tempId = getId(socks);
            if (changeQuantitySocks(tempId, socks, type)) {
                operationService.addOperation(new Operation(type, getDateTimeString(), socks));
                saveDataToFile();
                return true;
            }
        }
        return false;
    }

    private Long getId(Socks socks) {
        Long tempId;
        for (Map.Entry<Long, Socks> entry : socksMap.entrySet()) {
            if (entry.getValue().equals(socks)) {
                tempId = entry.getKey();
                return tempId;
            }
        }
        return Long.MIN_VALUE;
    }

    private boolean changeQuantitySocks(Long id, Socks socks, OperationType type) {
        Socks tempSocks = socksMap.get(id);
        if (type.equals(OperationType.ADD)) {
            socks.setQuantity(tempSocks.getQuantity() + socks.getQuantity());
        } else {
            if (socks.getQuantity() <= tempSocks.getQuantity()) {
                socks.setQuantity(tempSocks.getQuantity() - socks.getQuantity());
            } else {
                return false;
            }
        }
        socksMap.put(id, socks);
        return true;
    }

    private void loadDataFromFile() {
        try {
            String json = fileService.readFile(socksDataFilePath);
            socksMap = new ObjectMapper().readValue(json, new TypeReference<Map<Long, Socks>>() {
            });
            id = (long) socksMap.size();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void saveDataToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(socksMap);
            fileService.writeFile(socksDataFilePath, json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private String getDateTimeString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy-hh:mm");
        return LocalDateTime.now().format(dtf);
    }
}
