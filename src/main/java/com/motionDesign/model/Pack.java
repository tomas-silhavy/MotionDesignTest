package com.motionDesign.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pack {

    private final int id;
    private final int maxItemCount;
    private final BigDecimal maxWeight;
    private BigDecimal currentWeight;
    private int currentItems;
    private List<Item> items;

    public Pack(int id, int maxItemCount, BigDecimal maxWeight) {
        this.id = id;
        this.maxItemCount = maxItemCount;
        this.maxWeight = maxWeight;
        this.currentWeight = BigDecimal.ZERO;

        this.items = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public BigDecimal getWeight() {
        return currentWeight;
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public Item addItem(Item item) {

        BigDecimal remainingPackWeight = maxWeight.subtract(currentWeight);
        int remainingPackItemCount = maxItemCount - currentItems;

        int allowedPieceCount = Math.min(remainingPackItemCount, remainingPackWeight.divide(item.getWeight(), RoundingMode.DOWN).intValue());

        if(allowedPieceCount == 0) {
            return item;
        }

        Item partialItem = null;
        if( item.getQuantity() > allowedPieceCount) {

            partialItem = new Item(item.getId(), item.getLength(), item.getQuantity() - allowedPieceCount, item.getWeight());
            item.setQuantity(allowedPieceCount);
        }

        items.add(item);
        currentWeight = currentWeight.add(item.getTotalWeight());
        currentItems += item.getQuantity();

        return partialItem;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Pack Number: ");
        sb.append(id).append("\n");

        int packLength = 0;
        for (Item item : items) {
            sb.append(item).append("\n");
            packLength = Math.max(packLength, item.getLength());
        }
        sb.append("Pack Length: ").append(packLength);
        sb.append(", Pack Weight: ").append(currentWeight.stripTrailingZeros()).append("\n");
        return sb.toString();
    }
}
