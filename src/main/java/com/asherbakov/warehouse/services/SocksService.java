package com.asherbakov.warehouse.services;

import com.asherbakov.warehouse.models.Socks;
import com.asherbakov.warehouse.models.enums.Color;
import com.asherbakov.warehouse.models.enums.OperationType;
import com.asherbakov.warehouse.models.enums.Size;

public interface SocksService {

    String getAllSocks();

    int getSocksCount(Color color, Size size, int cottonMin, int cottonMax);

    void addSocks(Socks socks);

    boolean takeOutSocks(Socks socks, OperationType type);

}
