package com.motionDesign.test;

public class Item {

    private int id;
    private int length;
    private int quantity;
    private double weight;

    public Item(int id, int length, int quantity, double weight) {
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

    public double getWeight() {
        return weight;
    }


    public double getTotalWeight() {
        return weight * quantity;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s", id, length, quantity, weight);
    }
}
