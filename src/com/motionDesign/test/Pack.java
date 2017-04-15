package com.motionDesign.test;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Pack {

    private int id;
    private ArrayList<Item> items = new ArrayList<>();

    public Pack(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public int getTotalItemCount() {
        return items.stream().mapToInt(Item::getQuantity).sum();
    }

    public double getWeight() {
        return items.stream().mapToDouble(Item::getTotalWeight).sum();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Pack Number: ");
        sb.append(id).append("\n");

        int packLength = 0;
        double packWeight = 0;
        for (Item item : items) {
            sb.append(item).append("\n");
            packLength = Math.max(packLength, item.getLength());
            packWeight += item.getTotalWeight();
        }

        sb.append("Pack Length: ").append(packLength);
        sb.append(", Pack Weight: ").append(new DecimalFormat("0.###").format(packWeight));
        return sb.toString();
    }
}
