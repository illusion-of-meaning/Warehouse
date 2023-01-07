package com.asherbakov.warehouse.models;

import com.asherbakov.warehouse.models.enums.Color;
import com.asherbakov.warehouse.models.enums.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
public class Socks {
    private Color color;
    private Size size;
    private int cottonPercent;
    private int quantity;

    public Socks(Color color, Size size, int cottonPercent, int quantity) {
        if (color != null) {
            this.color = color;
        } else {
            throw new IllegalArgumentException("Ошибка. Цвет не задан.");
        }
        if (size != null) {
            this.size = size;
        } else {
            throw new IllegalArgumentException("Ошибка. Размер не задан.");
        }
        setCottonPercent(cottonPercent);
        setQuantity(quantity);
    }

    public void setCottonPercent(int cottonPercent) {
        if (cottonPercent >= 0 && cottonPercent <= 100) {
            this.cottonPercent = cottonPercent;
        } else {
            throw new IllegalArgumentException("Ошибка. Значение должно находиться в диапозоне от 0 до 100.");
        }
    }

    public void setQuantity(int quantity) {
        if (quantity > 0) {
            this.quantity = quantity;
        } else {
            throw new IllegalArgumentException("Ошибка. Количество должно быть положительным.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Socks socks = (Socks) o;
        return cottonPercent == socks.cottonPercent && color == socks.color && size == socks.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, size, cottonPercent);
    }

    @Override
    public String toString() {
        return "----------------" +
                "\n\tцвет: " + color +
                "\n\tразмер: " + size +
                "\n\tпроцент хлопка: " + cottonPercent +
                "\n\tколичество на складе: " + quantity +
                "\n----------------";
    }
}
