package com.asherbakov.warehouse.controllers;

import com.asherbakov.warehouse.models.Socks;
import com.asherbakov.warehouse.models.enums.Color;
import com.asherbakov.warehouse.models.enums.OperationType;
import com.asherbakov.warehouse.models.enums.Size;
import com.asherbakov.warehouse.services.SocksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Носки", description = "Операции с носками")
@RequestMapping("/api/socks")
public class SocksController {
    private final SocksService socksService;

    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }

    @Operation(summary = "Приход товара на склад", description = "Регистрирует приход товара на склад")
    @Parameters(value = {
            @Parameter(name = "socks", description = "Добавляемый товар в виде JSON-объекта",
                    example = "{\"color\":\"BLACK\",\"size\":\"S\",\"cottonPercent\":30,\"quantity\":40}")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Товар добавлен"),
            @ApiResponse(responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат"),
            @ApiResponse(responseCode = "500",
                    description = "Ошибка сервера")
    })
    @PostMapping
    private ResponseEntity<Void> addSocks(@RequestBody Socks socks) {
        try {
            if (socks != null) {
                socksService.addSocks(socks);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(400).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @Operation(summary = "Количество носков", description = "Получение информации о количестве носков определенного типа на складе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Запрос выполнен"),
            @ApiResponse(responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат"),
            @ApiResponse(responseCode = "500",
                    description = "Ошибка сервера")
    })
    @GetMapping
    private ResponseEntity<String> getSocksQuantity(@RequestParam Color color,
                                                    @RequestParam Size size,
                                                    @RequestParam int cottonMin,
                                                    @RequestParam int cottonMax) {
        try {
            if (color != null && size != null && cottonMin >= 0 && cottonMax >= 0 && cottonMax >= cottonMin) {
                String count = String.valueOf(socksService.getSocksCount(color, size, cottonMin, cottonMax));
                return ResponseEntity.ok(count);
            } else {
                return ResponseEntity.status(HttpStatus.valueOf(400)).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.valueOf(500)).build();
        }
    }

    @Operation(summary = "Вывести все носки на складе", description = "Получение информации о количестве носков на складе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Запрос выполнен"),
            @ApiResponse(responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат"),
            @ApiResponse(responseCode = "500",
                    description = "Ошибка сервера")
    })
    @GetMapping("/all")
    private ResponseEntity<String> getAllSocks() {
        return ResponseEntity.ok(socksService.getAllSocks());
    }

    @Operation(summary = "Отпуск товара со склада", description = "Регистрирует отпуск товара со склада")
    @Parameters(value = {
            @Parameter(name = "socks", description = "Отпускаемый товар в виде JSON-объекта",
                    example = "{\"color\":\"BLACK\",\"size\":\"S\",\"cottonPercent\":30,\"quantity\":40}")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Товар отпущен"),
            @ApiResponse(responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат"),
            @ApiResponse(responseCode = "500",
                    description = "Ошибка сервера")
    })
    @PutMapping
    private ResponseEntity<Void> takeOutSocks(@RequestBody Socks socks) {
        try {
            if (socks != null) {
                if (socksService.takeOutSocks(socks, OperationType.TAKE)) {
                    return ResponseEntity.ok().build();
                }
            }
            return ResponseEntity.status(400).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @Operation(summary = "Списание товара", description = "Регистрирует списание испорченных (бракованных) носков.")
    @Parameters(value = {
            @Parameter(name = "socks", description = "Списываемый товар в виде JSON-объекта",
                    example = "{\"color\":\"BLACK\",\"size\":\"S\",\"cottonPercent\":30,\"quantity\":40}")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Товар списан"),
            @ApiResponse(responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат"),
            @ApiResponse(responseCode = "500",
                    description = "Ошибка сервера")
    })
    @DeleteMapping
    private ResponseEntity<Void> writeOffSocks(@RequestBody Socks socks) {
        try {
            if (socks != null) {
                if (socksService.takeOutSocks(socks, OperationType.WRITE_OFF)) {
                    return ResponseEntity.ok().build();
                }
            }
            return ResponseEntity.status(400).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

}
