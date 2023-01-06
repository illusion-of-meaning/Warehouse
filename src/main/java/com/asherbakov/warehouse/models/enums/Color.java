package com.asherbakov.warehouse.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Color {
    BLACK("Чёрный"),
    BROWN("Коричневый"),
    GREEN("Зеленый"),
    RED("Красный"),
    WHITE("Белый");
    private final String color;
}
