package com.gildedrose;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class UpdateItemConjuredTest {

    /*
       At the end of each day our system lowers both values for every item :
         SellIn--;
         Quality -= qualityDecrease;

	- Once the sell by date has passed (SellIn < 0), Quality degrades twice as fast
	- The Quality of an item is never negative
	- The Quality of an item is never more than 50

    New feature : new category of items
	- "Conjured" items degrade in Quality twice as fast as normal items
     */
    private static final ItemCategory CATEGORY_CONJURED = ItemCategory.CONJURED;

    @Test
    void dailyUpdateUsualOutdatedMinQUality() {
        GildedRose app = new GildedRose(new Item[]{new Item("Conjured, foo", -5, -7)});
        app.updateQuality();
        Item item = app.items[0];
        assertEquals(-6, item.getSellIn());
        assertTrue(item.getQuality() == 0);
        assertTrue(item.getQuality() == CATEGORY_CONJURED.getMinQuality());
        assertTrue(item.getQuality() >= CATEGORY_CONJURED.getMinQuality() && item.getQuality() <= CATEGORY_CONJURED.getMaxQuality());
    }

    @Test
    void dailyUpdateUsualMinQuality() {
        int initialSellIn = 70;
        int nbUpdate = initialSellIn + 5; // => outdated
        GildedRose app = new GildedRose(new Item[]{new Item("CONJURED foo3", initialSellIn, 60)});
        for (int i = 0; i < nbUpdate; i++) {
            app.updateQuality();
        }
        Item item = app.items[0];
        assertEquals(-5, item.getSellIn());
        assertTrue(item.getQuality() == 0);
        assertTrue(item.getQuality() == CATEGORY_CONJURED.getMinQuality());
        assertTrue(item.getQuality() >= CATEGORY_CONJURED.getMinQuality() && item.getQuality() <= CATEGORY_CONJURED.getMaxQuality());
    }

    @Test
    void dailyUpdateUsualNormalCase() {
        GildedRose app = new GildedRose(new Item[]{new Item("conjured foo2", 20, 40)});
        app.updateQuality();
        Item item = app.items[0];
        assertEquals(19, item.getSellIn());
        assertTrue(item.getQuality() == 38);
        assertTrue(item.getQuality() == 40 - CATEGORY_CONJURED.getPositiveSellInQualityStep());
        assertTrue(item.getQuality() >= CATEGORY_CONJURED.getMinQuality() && item.getQuality() <= CATEGORY_CONJURED.getMaxQuality());
    }

    @Test
    void dailyUpdateUsualOutdatedWithPositiveQuality() {
        int initialSellIn = 0;
        int initialQuality = 40;
        int nbSteps = 3;
        int expectedSellIn = initialSellIn - nbSteps;
        int expectedQuality = initialQuality - nbSteps * 2 * CATEGORY_CONJURED.getPositiveSellInQualityStep(); // Once the sell by date has passed (SellIn < 0), Quality degrades twice as fast
        GildedRose app = new GildedRose(new Item[]{new Item("Conjured foo4", initialSellIn, initialQuality)});
        for (int i = 0; i < nbSteps; i++) {
            app.updateQuality();
        }
        Item item = app.items[0];
        assertEquals(expectedSellIn, item.getSellIn());
        assertEquals(expectedQuality, item.getQuality());
        assertTrue(item.getQuality() >= CATEGORY_CONJURED.getMinQuality() && item.getQuality() <= CATEGORY_CONJURED.getMaxQuality());
    }

}
