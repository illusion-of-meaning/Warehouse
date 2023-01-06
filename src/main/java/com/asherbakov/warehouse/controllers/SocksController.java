package com.asherbakov.warehouse.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Носки", description = "Операции с носками")
@RequestMapping("/api/socks")
public class SocksController {

}
