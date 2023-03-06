package com.gildedrose;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class UpdateItemUsualTest {

    /*
       At the end of each day our system lowers both values for every item :
         SellIn--;
         Quality -= qualityDecrease;

	- Once the sell by date has passed (SellIn < 0), Quality degrades twice as fast
	- The Quality of an item is never negative
	- The Quality of an item is never more than 50
     */
    private static final ItemCategory CATEGORY_USUAL = ItemCategory.USUAL;

    @Test
    void dailyUpdateUsualOutdatedMinQUality() {
        GildedRose app = new GildedRose(new Item[]{new Item("foo", -5, -7)});
        app.updateQuality();
        Item item = app.items[0];
        assertEquals(-6, item.getSellIn());
        assertTrue(item.getQuality() == 0);
        assertTrue(item.getQuality() == CATEGORY_USUAL.getMinQuality());
        assertTrue(item.getQuality() >= CATEGORY_USUAL.getMinQuality() && item.getQuality() <= CATEGORY_USUAL.getMaxQuality());
    }

    @Test
    void dailyUpdateUsualMinQuality() {
        int initialSellIn = 70;
        int nbUpdate = initialSellIn + 5; // => outdated
        GildedRose app = new GildedRose(new Item[]{new Item("foo3", initialSellIn, 60)});
        for (int i = 0; i < nbUpdate; i++) {
            app.updateQuality();
        }
        Item item = app.items[0];
        assertEquals(-5, item.getSellIn());
        assertTrue(item.getQuality() == 0);
        assertTrue(item.getQuality() == CATEGORY_USUAL.getMinQuality());
        assertTrue(item.getQuality() >= CATEGORY_USUAL.getMinQuality() && item.getQuality() <= CATEGORY_USUAL.getMaxQuality());
    }

    @Test
    void dailyUpdateUsualNormalCase() {
        GildedRose app = new GildedRose(new Item[]{new Item("foo2", 20, 40)});
        app.updateQuality();
        Item item = app.items[0];
        assertEquals(19, item.getSellIn());
        assertTrue(item.getQuality() == 39);
        assertTrue(item.getQuality() == 40 - CATEGORY_USUAL.getPositiveSellInQualityStep());
        assertTrue(item.getQuality() >= CATEGORY_USUAL.getMinQuality() && item.getQuality() <= CATEGORY_USUAL.getMaxQuality());
    }

    @Test
    void dailyUpdateUsualOutdatedWithPositiveQuality() {
        int initialSellIn = 0;
        int initialQuality = 40;
        int nbSteps = 3;
        int expectedSellIn = initialSellIn - nbSteps;
        int expectedQuality = initialQuality - nbSteps * 2 * CATEGORY_USUAL.getPositiveSellInQualityStep(); // Once the sell by date has passed (SellIn < 0), Quality degrades twice as fast
        GildedRose app = new GildedRose(new Item[]{new Item("foo4", initialSellIn, initialQuality)});
        for (int i = 0; i < nbSteps; i++) {
            app.updateQuality();
        }
        Item item = app.items[0];
        assertEquals(expectedSellIn, item.getSellIn());
        assertEquals(expectedQuality, item.getQuality());
        assertTrue(item.getQuality() >= CATEGORY_USUAL.getMinQuality() && item.getQuality() <= CATEGORY_USUAL.getMaxQuality());
    }

}
