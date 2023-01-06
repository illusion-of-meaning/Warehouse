package com.asherbakov.warehouse.services.impl;

import com.asherbakov.warehouse.models.Socks;
import com.asherbakov.warehouse.models.enums.Color;
import com.asherbakov.warehouse.models.enums.Size;
import com.asherbakov.warehouse.services.SocksService;

import java.util.HashMap;
import java.util.Map;

public class SocksServiceImpl implements SocksService {
    private static Long id = 0L;
    public static Map<Long, Socks> socksMap = new HashMap<>();

    @Override
    public int socksCountOfColor(Color color) {
        int count = 0;
        for (Map.Entry<Long, Socks> entry : socksMap.entrySet()) {
            if (entry.getValue().getColor().equals(color)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int socksCountOfSize(Size size) {
        int count = 0;
        for (Map.Entry<Long, Socks> entry : socksMap.entrySet()) {
            if (entry.getValue().getSize().equals(size)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int socksCountOfColorAndQuantity(Color color, int quantity) {
        int count = 0;
        for (Map.Entry<Long, Socks> entry : socksMap.entrySet()) {
            if (entry.getValue().getColor().equals(color) &&
                    entry.getValue().getQuantity() == quantity) {
                count++;
            }
        }
        return count;
    }

    // TODO: add find

    @Override
    public void addSocks(Socks socks) {
        if (!socksMap.containsValue(socks)) {
            socksMap.put(id++, socks);
        } else {
            throw new IllegalArgumentException("Ошибка. Данный товар уже существует.");
        }
    }

    @Override
    public boolean takeOutSocks(Socks socks, int count) {
        if (count > 0 && socksMap.containsValue(socks)) {
            Long tempId;
            for (Map.Entry<Long, Socks> entry : socksMap.entrySet()) {
                if (entry.getValue().equals(socks)) {
                    tempId = entry.getKey();
                    return takeOutSocks(tempId, count);
                }
            }
        }
        return false;
    }
    @Override
    public boolean takeOutSocks(Long id, int count) {
        if (count > 0 && socksMap.get(id).getQuantity() > count) {
            Socks socks = socksMap.get(id);
            socks.setQuantity(socks.getQuantity() - count);
            socksMap.put(id, socks);
            return true;
        } else {
            return false;
        }
    }

}
