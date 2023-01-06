package com.asherbakov.warehouse.services;

import com.asherbakov.warehouse.models.Socks;
import com.asherbakov.warehouse.models.enums.Color;
import com.asherbakov.warehouse.models.enums.Size;

public interface SocksService {

    int socksCountOfColor(Color color);

    int socksCountOfSize(Size size);

    int socksCountOfColorAndQuantity(Color color, int quantity);

    void addSocks(Socks socks);

    boolean takeOutSocks(Socks socks, int count);

    boolean takeOutSocks(Long id, int count);
}
