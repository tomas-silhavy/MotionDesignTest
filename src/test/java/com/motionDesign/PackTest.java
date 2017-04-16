package com.motionDesign;

import com.motionDesign.model.Item;
import com.motionDesign.model.Pack;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.motionDesign.PackPlanner.SortOrder.*;
import static org.testng.Assert.assertEquals;

public class PackTest {

    private PackPlanner pp;
    private List<Item> items;


    @BeforeMethod
    public void setUp(){
        pp = new PackPlanner(NATURAL, 2, new BigDecimal(40));
        items = new ArrayList<>();
        items.add(new Item(10, 15, 5, new BigDecimal(13)));
        items.add(new Item(20, 26, 9, new BigDecimal(5)));
        items.add(new Item(30, 7, 17, new BigDecimal(3)));
    }

    @Test
    public void testAddItemNatural() throws Exception {
        List<Pack> packs = pp.packUp(items);
//        packs.forEach(System.out::println);
        assertEquals(packs.size(), 16);
        assertEquals(packs.stream().map(Pack::getItems).flatMap(List::stream).mapToInt(Item::getQuantity).sum(), 31);
        Set<Integer> set = new LinkedHashSet<>();
        packs.stream().map(Pack::getItems).flatMap(List::stream).forEach(item -> set.add(item.getId()));
        assertEquals(set.toArray(), new Integer[]{10, 20, 30});
    }

    @Test
    public void testAddItemLongToShort() throws Exception {
        pp.setOrder(LONG_TO_SHORT);
        pp.setMaxItemCount(4);
        pp.setMaxPackWeight(new BigDecimal(16));
        List<Pack> packs = pp.packUp(items);
//        packs.forEach(System.out::println);
        assertEquals(packs.size(), 12);
        assertEquals(packs.stream().map(Pack::getItems).flatMap(List::stream).mapToInt(Item::getQuantity).sum(), 31);
        Set<Integer> set = new LinkedHashSet<>();
        packs.stream().map(Pack::getItems).flatMap(List::stream).forEach(item -> set.add(item.getId()));
        assertEquals(set.toArray(), new Integer[]{20, 10, 30});
    }

    @Test
    public void testAddItemShortToLong() throws Exception {
        pp.setOrder(SHORT_TO_LONG);
        pp.setMaxItemCount(12);
        pp.setMaxPackWeight(new BigDecimal(34));
        List<Pack> packs = pp.packUp(items);
//        packs.forEach(System.out::println);
        assertEquals(packs.size(), 6);
        assertEquals(packs.stream().map(Pack::getItems).flatMap(List::stream).mapToInt(Item::getQuantity).sum(), 31);
        Set<Integer> set = new LinkedHashSet<>();
        packs.stream().map(Pack::getItems).flatMap(List::stream).forEach(item -> set.add(item.getId()));
        assertEquals(set.toArray(), new Integer[]{30, 10, 20});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooHeavyItem() throws Exception {
        items.add(new Item(40, 8, 40, new BigDecimal(100)));
        pp.packUp(items);
    }

}