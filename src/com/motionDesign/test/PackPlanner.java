package com.motionDesign.test;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class PackPlanner {

    enum SortOrder {
        NATURAL, SHORT_TO_LONG, LONG_TO_SHORT
    }

    private SortOrder order;
    private int maxPiecesPP;
    private double maxWeightPP;

    public PackPlanner(SortOrder order, int maxPiecesPP, double maxWeightPP) {
        this.order = order;
        this.maxPiecesPP = maxPiecesPP;
        this.maxWeightPP = maxWeightPP;
    }

    private void sort(List<Item> items){
        switch (order){
            case SHORT_TO_LONG: items.sort(Comparator.comparing(Item::getLength)); break;
            case LONG_TO_SHORT: items.sort(Comparator.comparing(Item::getLength).reversed()); break;
        }
    }

    public List<Pack> packUp(List<Item> items) {

        sort(items);
        List<Pack> packs = new ArrayList<>();

        Pack pack = createPack(packs);
        double packWeight = 0;
        int packItemCount = 0;
        boolean splitItemFlag = false;
        Item item = null;
        Iterator<Item> iter = items.listIterator();

        //I chose counting packWeight and packItemCount in this outside-counter manner only for performance reasons
        //so it's not necessary to loop through pack items in every iteration, if that wouldn't be a problem I would use
        //appropriate methods in Pack

        while (iter.hasNext() || splitItemFlag) {
            item = splitItemFlag ? item : iter.next();

            splitItemFlag = false;
            double remainingPackWeight = maxWeightPP - packWeight;
            int remainingPackItemCount = maxPiecesPP - packItemCount;

            int allowedPieceCount = Math.min(remainingPackItemCount, (int)(remainingPackWeight / item.getWeight()));


            if( item.getQuantity() <= allowedPieceCount) {
                packWeight += item.getTotalWeight();
                packItemCount += item.getQuantity();
                pack.getItems().add(item);
                if (item.getQuantity() == allowedPieceCount && iter.hasNext()) {
                    pack = createPack(packs);
                    packWeight = 0;
                    packItemCount = 0;
                }
            } else {
                pack.getItems().add(new Item(item.getId(), item.getLength(), allowedPieceCount, item.getWeight()));
                item.setQuantity(item.getQuantity() - allowedPieceCount);
                pack = createPack(packs);
                packWeight = 0;
                packItemCount = 0;
                splitItemFlag = true;
            }
        }

        return packs;
    }

    private Pack createPack(List<Pack> packs) {
        Pack p = new Pack(packs.size() + 1);
        packs.add(p);
        return p;
    }

    public static void main(String[] args) throws IOException{

        PackPlanner packPlanner = null;
        String input;
        List<Item> items = new ArrayList<>();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        while ( (input = bufferedReader.readLine()) != null && input.trim().length() != 0 ) {

            System.out.println(input);
            //skip comments
            if( !input.startsWith("#")) {

                String[] params = input.split(",");
                //read planner parameters
                if(packPlanner == null) {

                    if(params.length != 3) {
                        System.out.println("Wrong number of Pack planner input parameters.");
                        return;
                    }
                    packPlanner = new PackPlanner(SortOrder.valueOf(params[0].toUpperCase().trim()),
                            Integer.parseInt(params[1]),
                            Double.parseDouble(params[2]));

                } else { //read item parameters

                    if(params.length != 4) {
                        System.out.println("Wrong number of Item input parameters.");
                    }
                    int id = Integer.parseInt(params[0]);
                    int length = Integer.parseInt(params[1]);
                    int quantity = Integer.parseInt(params[2]);
                    double weight = Double.parseDouble(params[3]);

                    items.add(new Item(id, length, quantity, weight));
                }
            }
        }
        if(packPlanner != null) {
            List<Pack> packs = packPlanner.packUp(items);

            packs.forEach((Pack pack) -> System.out.println(pack + "\n"));
        }
    }
}
