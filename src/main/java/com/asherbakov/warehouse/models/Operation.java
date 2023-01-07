package com.asherbakov.warehouse.models;

import com.asherbakov.warehouse.models.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Operation {
    private OperationType operationType;
    private String DateTime;
    private Socks socks;
}
