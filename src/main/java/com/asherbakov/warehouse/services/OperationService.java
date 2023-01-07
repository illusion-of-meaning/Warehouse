package com.asherbakov.warehouse.services;

import com.asherbakov.warehouse.models.Operation;

public interface OperationService {
    void addOperation(Operation operation);

    void cleanOperations();

}
