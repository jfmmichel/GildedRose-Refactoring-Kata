package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemCreationTest {

    /* 
   - The Quality of an item is never negative
   - The Quality of an item is never more than 50
   - Quality never increase above 50, however "Sulfuras" Quality is 80 and it never alters.
    
   Special categories
   - "Sulfuras"
      - never has to be sold (SellIn > 0 ?)
      - Quality is 80 and it never alters
     */
    @Test
    void createItemUsualOutdated() {
        ItemCategory category = ItemCategory.USUAL;
        String name = "foo";
        Item item = new Item(name, -5, -7);
        assertEquals(name, item.getName());
        assertEquals(-5, item.getSellIn());
        assertTrue(item.getQuality() == 0);
        assertTrue(item.getQuality() >= category.getMinQuality() && item.getQuality() <= category.getMaxQuality());
    }

    @Test
    void createItemAgedBrie() {
        ItemCategory category = ItemCategory.AGED_BRIE;
        Item item = new Item(category.getName() + ", good cheese", 0, 0);
        assertEquals(0, item.getSellIn());
        assertTrue(item.getQuality() >= category.getMinQuality() && item.getQuality() <= category.getMaxQuality());
    }

    @Test
    void createItemBackstagePasses() {
        ItemCategory category = ItemCategory.BACKSTAGE_PASSES;
        Item item = new Item(category.getName() + ", good cheese", 15, 20);
        assertEquals(15, item.getSellIn());
        assertEquals(20, item.getQuality());
        assertTrue(item.getQuality() >= category.getMinQuality() && item.getQuality() <= category.getMaxQuality());
    }

    @Test
    void createItemSulfuras() {
        ItemCategory category = ItemCategory.SULFURAS;
        Item item = new Item(category.getName() + ", super", -3, 30);
        assertTrue(item.getSellIn() > 0); // never has to be sold (SellIn > 0 ?)
        assertEquals(80, item.getQuality());
        assertTrue(item.getQuality() >= category.getMinQuality() && item.getQuality() <= category.getMaxQuality());
    }

    @Test
    void createItemConjured() {
        ItemCategory category = ItemCategory.CONJURED;
        String name = category.getName() + ", xyz";
        Item item = new Item(name, -3, 70);
        assertEquals(name, item.getName());
        assertEquals(-3, item.getSellIn());
        assertTrue(item.getQuality() == category.getMaxQuality());
        assertTrue(item.getQuality() >= category.getMinQuality() && item.getQuality() <= category.getMaxQuality());
    }
}
