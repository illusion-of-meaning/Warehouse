package com.asherbakov.warehouse.services;

import com.asherbakov.warehouse.models.Socks;
import com.asherbakov.warehouse.models.enums.Color;
import com.asherbakov.warehouse.models.enums.OperationType;
import com.asherbakov.warehouse.models.enums.Size;

import java.io.IOException;

public interface SocksService {

    String getAllSocks();

    int getSocksCount(Color color, Size size, int cottonMin, int cottonMax);

    void addSocks(Socks socks) throws IOException;

    boolean takeOutSocks(Socks socks, OperationType type) throws IOException;

}
