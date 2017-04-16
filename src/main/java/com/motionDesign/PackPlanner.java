package com.motionDesign;


import com.motionDesign.model.Item;
import com.motionDesign.model.Pack;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PackPlanner {

    enum SortOrder {
        NATURAL, SHORT_TO_LONG, LONG_TO_SHORT
    }

    private SortOrder order;
    private int maxItemCount;
    private BigDecimal maxPackWeight;
    private int packIdCounter;

    public PackPlanner(SortOrder order, int maxItemCount, BigDecimal maxPackWeight) {
        this.packIdCounter = 1;
        this.order = order;
        this.maxItemCount = maxItemCount;
        this.maxPackWeight = maxPackWeight;
    }

    private void sort(List<Item> items){
        switch (order){
            case SHORT_TO_LONG: items.sort(Comparator.comparing(Item::getLength)); break;
            case LONG_TO_SHORT: items.sort(Comparator.comparing(Item::getLength).reversed()); break;
        }
    }

    public SortOrder getOrder() {
        return order;
    }

    public void setOrder(SortOrder order) {
        this.order = order;
    }

    public int getMaxItemCount() {
        return maxItemCount;
    }

    public void setMaxItemCount(int maxItemCount) {
        this.maxItemCount = maxItemCount;
    }

    public BigDecimal getMaxPackWeight() {
        return maxPackWeight;
    }

    public void setMaxPackWeight(BigDecimal maxPackWeight) {
        this.maxPackWeight = maxPackWeight;
    }

    public void resetPackIdCounter(){
        this.packIdCounter = 1;
    }

    public List<Pack> packUp(List<Item> items) throws IllegalArgumentException {

        sort(items);
        List<Pack> packs = new ArrayList<>();

        Pack pack = createPack();

        for (Item item : items){

            if(item.getWeight().compareTo(maxPackWeight) > 0){
                throw new IllegalArgumentException("Too heavy item: [" + item + "]");
            }
            while ( (item = pack.addItem(item)) != null) {
                packs.add(pack);
                pack = createPack();
            }
        }

        if (!pack.isEmpty()) {
            packs.add(pack);
        }

        return packs;
    }

    private Pack createPack() {
        return new Pack(packIdCounter++, maxItemCount, maxPackWeight);
    }
}
