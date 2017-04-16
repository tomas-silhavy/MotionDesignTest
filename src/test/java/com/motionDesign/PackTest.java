package com.motionDesign;

import com.motionDesign.model.Item;
import com.motionDesign.model.Pack;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class PackTest {

    private PackPlanner pp;
    private List<Item> items;


    @BeforeClass
    public void setUp(){
        pp = new PackPlanner(PackPlanner.SortOrder.NATURAL, 2, new BigDecimal(40));
        items = new ArrayList<>();
        items.add(new Item(10, 15, 5, new BigDecimal(13)));
        items.add(new Item(20, 26, 9, new BigDecimal(5)));
        items.add(new Item(30, 7, 17, new BigDecimal(3)));
    }

    @Test
    public void testAddItemNatural() throws Exception {
        List<Pack> packs = pp.packUp(new ArrayList<>(items));
        packs.forEach(System.out::println);
        assertEquals(packs.size(), 16);
        assertEquals(packs.stream().map(Pack::getItems).flatMap(List::stream).mapToInt(Item::getQuantity).sum(), 31);

    }

    @Test
    public void testAddItemLongToShort() throws Exception {

    }

    @Test
    public void testAddItemShortToLong() throws Exception {

    }

    @Test
    public void testGetTotalItemCount() throws Exception {
    }

}