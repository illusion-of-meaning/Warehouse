package com.asherbakov.warehouse.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Стартовый контроллер")
public class StartController {
    @Operation(summary = "Стратовая страница")
    @GetMapping("/")
    public String start() {
        return """
                <h1>Склад 1.0</h1>
                приложение запущено
                """;
    }
}
