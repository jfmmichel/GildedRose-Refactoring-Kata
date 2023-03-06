package com.gildedrose;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class UpdateItemAgedBrie {

    /*
At the end of each day our system lowers both values for every item :
    SellIn--;
    Quality -= qualityDecrease;

	- Once the sell by date has passed (SellIn < 0), Quality degrades twice as fast
	- The Quality of an item is never negative
	- The Quality of an item is never more than 50
    
- "Aged Brie" :
    - increases in Quality the older it gets
    - What about negative sellIn ? @TODO
     */
    private static final ItemCategory CATEGORY_AGED_BRIE = ItemCategory.AGED_BRIE;

    @Test
    void dailyUpdateOutdatedAgedBrie1() {
        String name = CATEGORY_AGED_BRIE.getName() + ", foo";
        int initialSellIn = -3;
        int initialQuality = 0;
        GildedRose app = new GildedRose(new Item[]{new Item(name, initialSellIn, initialQuality)});
        app.updateQuality();
        Item item = app.items[0];
        int expectedSellIn = initialSellIn - 1;
        int expectedQuality = initialQuality + 2 * CATEGORY_AGED_BRIE.getPositiveSellInQualityStep(); // "Aged Brie" : increases in Quality the older it gets, should quality increase twice as fast when sell by date has passed ?
        assertEquals(expectedSellIn, item.getSellIn());
        assertEquals(expectedQuality, item.getQuality(), "Expected quality daily update for " + CATEGORY_AGED_BRIE.getName() + " should be updated from [" + initialQuality + "] to [" + expectedQuality + "] instead of [" + item.getQuality() + "]"); // quality is never negative
    }

    @Test
    void dailyUpdateAgedBrie() {
        String name = CATEGORY_AGED_BRIE.getName() + ", foo2";
        int initialSellIn = 5;
        int initialQuality = -3;
        GildedRose app = new GildedRose(new Item[]{new Item(name, initialSellIn, initialQuality)});
        int nbSteps = 3;
        for (int i = 0; i < nbSteps; i++) {
            app.updateQuality();
        }
        Item item = app.items[0];
        int expectedSellIn = initialSellIn - nbSteps;
        int expectedQuality = nbSteps * CATEGORY_AGED_BRIE.getPositiveSellInQualityStep(); // "Aged Brie" : increases in Quality the older it gets, should quality increase twice as fast when sell by date has passed ?
        assertEquals(expectedSellIn, item.getSellIn());
        assertEquals(expectedQuality, item.getQuality(), "Expected quality daily update for " + CATEGORY_AGED_BRIE.getName() + " should be updated from [" + initialQuality + "] to [" + expectedQuality + "] instead of [" + item.getQuality() + "]"); // quality is never negative
        assertTrue(item.getQuality() >= CATEGORY_AGED_BRIE.getMinQuality() && item.getQuality() <= CATEGORY_AGED_BRIE.getMaxQuality());
    }

    @Test
    void dailyUpdateAgedBrie2() {
        String name = CATEGORY_AGED_BRIE.getName() + ", good cheese";
        int initialSellIn = 60;
        int initialQuality = 0;
        int nbSteps = 75;
        int expectedSellIn = initialSellIn - nbSteps;
        int expectedQuality = CATEGORY_AGED_BRIE.getMaxQuality(); // "Aged Brie" : increases in Quality the older it gets / The Quality of an item is never more than 50
        GildedRose app = new GildedRose(new Item[]{new Item(name, initialSellIn, initialQuality)});
        for (int i = 0; i < nbSteps; i++) {
            app.updateQuality();
        }
        Item item = app.items[0];
        assertEquals(expectedSellIn, item.getSellIn());
        assertEquals(expectedQuality, item.getQuality(), "Expected quality daily update for " + item.getName() + "/" + CATEGORY_AGED_BRIE.getName() + " should be updated from [" + initialQuality + "] to [" + expectedQuality + "] instead of [" + item.getQuality() + "]");
        assertTrue(item.getQuality() >= CATEGORY_AGED_BRIE.getMinQuality() && item.getQuality() <= CATEGORY_AGED_BRIE.getMaxQuality());
    }
}
