package com.gildedrose;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author LD65SE
 */
public class UpdateItemBackstagePassesTest {

    /*
At the end of each day our system lowers both values for every item :
    SellIn--;
    Quality -= qualityDecrease;

	- Once the sell by date has passed (SellIn < 0), Quality degrades twice as fast
	- The Quality of an item is never negative
	- The Quality of an item is never more than 50
    
- "Backstage passes", like aged brie, increases in Quality as its SellIn value approaches;
	- Quality increases by 2 when there are 10 days or less 
                        and by 3 when there are 5 days or less
	- Quality drops to 0 after the concert (Sellin < 0)
     */
    private static final ItemCategory CATEGORY_BACKSTAGE_PASSES = ItemCategory.BACKSTAGE_PASSES;

    @Test
    void updateOutdated() {
        String name = CATEGORY_BACKSTAGE_PASSES.getName() + ", Pfff";
        int initialSellIn = -3;
        int initialQuality = 0;
        GildedRose app = new GildedRose(new Item[]{new Item(name, initialSellIn, initialQuality)});
        app.updateQuality();
        Item item = app.items[0];
        int expectedSellIn = initialSellIn - 1;
        int expectedQuality = CATEGORY_BACKSTAGE_PASSES.getMinQuality();
        assertEquals(expectedSellIn, item.getSellIn());
        assertEquals(expectedQuality, item.getQuality(), "Expected quality daily update for " + CATEGORY_BACKSTAGE_PASSES.getName() + " should be updated from [" + initialQuality + "] to [" + expectedQuality + "] instead of [" + item.getQuality() + "]"); // quality is never negative
        assertTrue(item.getQuality() >= CATEGORY_BACKSTAGE_PASSES.getMinQuality() && item.getQuality() <= CATEGORY_BACKSTAGE_PASSES.getMaxQuality());
    }

    @Test
    void updateOutdated2() {
        String name = CATEGORY_BACKSTAGE_PASSES.getName() + ", Comic";
        int initialSellIn = 5;
        int initialQuality = -3; // so it means 0 after creation ...
        GildedRose app = new GildedRose(new Item[]{new Item(name, initialSellIn, initialQuality)});
        int nbSteps = 6;
        for (int i = 0; i < nbSteps; i++) {
            app.updateQuality();
        }
        Item item = app.items[0];
        int expectedSellIn = initialSellIn - nbSteps;
        int expectedQuality = 0;
        assertEquals(expectedSellIn, item.getSellIn());
        assertEquals(expectedQuality, item.getQuality(), "Expected quality daily update for " + item.getName() + "/" + CATEGORY_BACKSTAGE_PASSES.getName() + " should be updated from [" + initialQuality + "] to [" + expectedQuality + "] instead of [" + item.getQuality() + "]"); // quality is never negative
        assertTrue(item.getQuality() >= CATEGORY_BACKSTAGE_PASSES.getMinQuality() && item.getQuality() <= CATEGORY_BACKSTAGE_PASSES.getMaxQuality());
    }

    @Test
    void updateLessThan5Days() {
        String name = CATEGORY_BACKSTAGE_PASSES.getName() + ", Comic";
        int initialSellIn = 5;
        int initialQuality = -3; // so it means 0 after creation ...
        GildedRose app = new GildedRose(new Item[]{new Item(name, initialSellIn, initialQuality)});
        int nbSteps = 3;
        for (int i = 0; i < nbSteps; i++) {
            app.updateQuality();
        }
        Item item = app.items[0];
        int expectedSellIn = initialSellIn - nbSteps;
        int expectedQuality = nbSteps * 3; // Quality increases by 3 when there are 5 days or less
        assertEquals(expectedSellIn, item.getSellIn());
        assertEquals(expectedQuality, item.getQuality(), "Expected quality daily update for " + item.getName() + "/" + CATEGORY_BACKSTAGE_PASSES.getName() + " should be updated from [" + initialQuality + "] to [" + expectedQuality + "] instead of [" + item.getQuality() + "]"); // quality is never negative
        assertTrue(item.getQuality() >= CATEGORY_BACKSTAGE_PASSES.getMinQuality() && item.getQuality() <= CATEGORY_BACKSTAGE_PASSES.getMaxQuality());
    }

    @Test
    void updateLessThan10Days() {
        String name = CATEGORY_BACKSTAGE_PASSES.getName() + ", Singer";
        int initialSellIn = 10;
        int initialQuality = 10;
        int nbSteps = 5;
        int expectedSellIn = initialSellIn - nbSteps;
        int expectedQuality = nbSteps * 2; // Quality increases by 2 when there are 10 days or less
        GildedRose app = new GildedRose(new Item[]{new Item(name, initialSellIn, initialQuality)});
        for (int i = 0; i < nbSteps; i++) {
            app.updateQuality();
        }
        Item item = app.items[0];
        assertEquals(expectedSellIn, item.getSellIn());
        assertEquals(expectedQuality, item.getQuality(), "Expected quality daily update for " + item.getName() + "/" + CATEGORY_BACKSTAGE_PASSES.getName() + " should be updated from [" + initialQuality + "] to [" + expectedQuality + "] instead of [" + item.getQuality() + "]");
        assertTrue(item.getQuality() >= CATEGORY_BACKSTAGE_PASSES.getMinQuality() && item.getQuality() <= CATEGORY_BACKSTAGE_PASSES.getMaxQuality());
    }

    @Test
    void update20Days() {
        String name = CATEGORY_BACKSTAGE_PASSES.getName() + ", Singer";
        int initialSellIn = 20;
        int initialQuality = 10;
        int nbSteps = 20;
        int expectedSellIn = initialSellIn - nbSteps;
        int expectedQuality = 5 * 2 + 5 * 3 + 10 * CATEGORY_BACKSTAGE_PASSES.getPositiveSellInQualityStep();
        GildedRose app = new GildedRose(new Item[]{new Item(name, initialSellIn, initialQuality)});
        for (int i = 0; i < nbSteps; i++) {
            app.updateQuality();
        }
        Item item = app.items[0];
        assertEquals(expectedSellIn, item.getSellIn());
        assertEquals(expectedQuality, item.getQuality(), "Expected quality daily update for " + item.getName() + "/" + CATEGORY_BACKSTAGE_PASSES.getName() + " should be updated from [" + initialQuality + "] to [" + expectedQuality + "] instead of [" + item.getQuality() + "]");
        assertTrue(item.getQuality() >= CATEGORY_BACKSTAGE_PASSES.getMinQuality() && item.getQuality() <= CATEGORY_BACKSTAGE_PASSES.getMaxQuality());
        // -------------------------------------
        app.updateQuality();
        expectedSellIn = -1;
        expectedQuality = 0;
        assertEquals(expectedSellIn, item.getSellIn());
        assertEquals(expectedQuality, item.getQuality(), "Expected quality daily update for " + item.getName() + "/" + CATEGORY_BACKSTAGE_PASSES.getName() + " should be updated from [" + initialQuality + "] to [" + expectedQuality + "] instead of [" + item.getQuality() + "]");
        assertTrue(item.getQuality() >= CATEGORY_BACKSTAGE_PASSES.getMinQuality() && item.getQuality() <= CATEGORY_BACKSTAGE_PASSES.getMaxQuality());
    }

    void update20DaysMaxQuality() {
        String name = CATEGORY_BACKSTAGE_PASSES.getName() + ", Singer";
        int initialSellIn = 20;
        int initialQuality = 30;
        int nbSteps = 20;
        int expectedSellIn = initialSellIn - nbSteps;
        int expectedQuality = CATEGORY_BACKSTAGE_PASSES.getMaxQuality();
        GildedRose app = new GildedRose(new Item[]{new Item(name, initialSellIn, initialQuality)});
        for (int i = 0; i < nbSteps; i++) {
            app.updateQuality();
        }
        Item item = app.items[0];
        assertEquals(expectedSellIn, item.getSellIn());
        assertEquals(expectedQuality, item.getQuality(), "Expected quality daily update for " + item.getName() + "/" + CATEGORY_BACKSTAGE_PASSES.getName() + " should be updated from [" + initialQuality + "] to [" + expectedQuality + "] instead of [" + item.getQuality() + "]");
        assertTrue(item.getQuality() >= CATEGORY_BACKSTAGE_PASSES.getMinQuality() && item.getQuality() <= CATEGORY_BACKSTAGE_PASSES.getMaxQuality());
        // -------------------------------------
        app.updateQuality();
        expectedSellIn = -1;
        expectedQuality = 0;
        assertEquals(expectedSellIn, item.getSellIn());
        assertEquals(expectedQuality, item.getQuality(), "Expected quality daily update for " + item.getName() + "/" + CATEGORY_BACKSTAGE_PASSES.getName() + " should be updated from [" + initialQuality + "] to [" + expectedQuality + "] instead of [" + item.getQuality() + "]");
        assertTrue(item.getQuality() >= CATEGORY_BACKSTAGE_PASSES.getMinQuality() && item.getQuality() <= CATEGORY_BACKSTAGE_PASSES.getMaxQuality());
    }
}
