package com.motionDesign.model;

import java.math.BigDecimal;

public class Item {

    private int id;
    private int length;
    private int quantity;
    private BigDecimal weight;

    public Item(int id, int length, int quantity, BigDecimal weight) {
        this.id = id;
        this.length = length;
        this.quantity = quantity;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public int getLength() {
        return length;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getWeight() {
        return weight;
    }


    public BigDecimal getTotalWeight() {
        return weight.multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public String toString() {
        return id + "," +length + "," +quantity + "," + weight;
    }
}
