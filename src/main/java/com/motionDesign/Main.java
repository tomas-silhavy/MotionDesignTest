package com.motionDesign;

import com.motionDesign.model.Item;
import com.motionDesign.model.Pack;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        PackPlanner packPlanner = null;
        String input;
        List<Item> items = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        while ( scanner.hasNextLine() ) {

            input = scanner.nextLine();
            if (input.length() == 0) {
                break;
            }
            //skip comments
            if( !input.startsWith("#")) {

                String[] params = input.split(",");
                //read planner parameters
                if(packPlanner == null) {

                    if(params.length != 3) {
                        System.out.println("Wrong number of Pack planner input parameters.");
                        return;
                    }
                    packPlanner = new PackPlanner(
                            PackPlanner.SortOrder.valueOf(params[0].toUpperCase().trim()),
                            Integer.parseInt(params[1]),
                            new BigDecimal(params[2]));

                } else { //read item parameters

                    if(params.length != 4) {
                        System.out.println("Wrong number of Item input parameters, expected 4 but found: " + params.length);
                    }
                    items.add(buildItem(params));
                }
            }
        }
        if(packPlanner != null) {
            try {
                List<Pack> packs = packPlanner.packUp(items);
                packs.forEach((Pack pack) -> System.out.println(pack + "\n"));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static Item buildItem(String[] params){
        int id = Integer.parseInt(params[0]);
        int length = Integer.parseInt(params[1]);
        int quantity = Integer.parseInt(params[2]);
        BigDecimal weight = new BigDecimal(params[3]);

        return new Item(id, length, quantity, weight);
    }
}
