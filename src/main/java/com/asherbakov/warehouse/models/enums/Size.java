package com.asherbakov.warehouse.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Size {
    S(19, 21),
    M(23, 25),
    L(27, 29),
    XL(31, 33);
    private final int min;
    private final int max;
}
