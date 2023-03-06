package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GildedRoseTest {

    /*
At the end of each day our system lowers both values for every item :
    SellIn--;
    Quality -= qualityDecrease;

	- Once the sell by date has passed (SellIn < 0), Quality degrades twice as fast
	- The Quality of an item is never negative
	- The Quality of an item is never more than 50
        - Quality never increase above 50, however "Sulfuras" Quality is 80 and it never alters.
    
Special categories
- "Sulfuras"
    - never has to be sold (SellIn > 0 ?)
    - never decreases in Quality
    - Quality is 80 and it never alters

- "Aged Brie" 
    - increases in Quality the older it gets

- "Backstage passes", like aged brie, increases in Quality as its SellIn value approaches;
	- Quality increases by 2 when there are 10 days or less 
                        and by 3 when there are 5 days or less
	- Quality drops to 0 after the concert (Sellin < 0)

- "Conjured" (new feature) :
    - items degrade in Quality twice as fast as normal items
     */

 /*
    Check Item creation for each category
     */
    @Test
    void createItemUsual() {
        String name = "foo";
        int initialSellIn = -5;
        int initialQuality = -7;
        assertEquals(ItemCategory.USUAL, ItemCategory.getCategory(name));
        Item item = new Item(name, initialSellIn, initialQuality);
        assertEquals(name, item.getName());
        assertEquals(initialSellIn, item.getSellIn());
        assertEquals(0, item.getQuality());
    }

    @Test
    void createItemAgedBrie() {
        String name = ItemCategory.AGED_BRIE.getName();
        int initialSellIn = 0;
        int initialQuality = 0;
        assertEquals(ItemCategory.AGED_BRIE, ItemCategory.getCategory(name));
        Item item = new Item(name, initialSellIn, initialQuality);
        assertEquals(name, item.getName());
        assertEquals(initialSellIn, item.getSellIn());
        assertEquals(initialQuality, item.getQuality());
    }

    @Test
    void createItemBackstagePasses() {
        String name = ItemCategory.BACKSTAGE_PASSES.getName();
        int initialSellIn = 15;
        int initialQuality = 20;
        assertEquals(ItemCategory.BACKSTAGE_PASSES, ItemCategory.getCategory(name));
        Item item = new Item(name, initialSellIn, initialQuality);
        assertEquals(name, item.getName());
        assertEquals(initialSellIn, item.getSellIn());
        assertEquals(initialQuality, item.getQuality());
    }

    @Test
    void createItemSulfuras() {
        String name = ItemCategory.SULFURAS.getName();
        int initialSellIn = -3;
        int initialQuality = 30;
        assertEquals(ItemCategory.SULFURAS, ItemCategory.getCategory(name));
        Item item = new Item(name, initialSellIn, initialQuality);
        assertEquals(name, item.getName());
        assertTrue(item.getSellIn() > 0); // never has to be sold (SellIn > 0 ?)
        assertEquals(80, item.getQuality());
    }

    @Test
    void dailyUpdateUsual1() {
        String name = "foo1";
        int initialSellIn = -5;
        int initialQuality = -7;
        int expectedSellIn = initialSellIn - 1;
        int expectedQuality = 0;
        Item[] items = new Item[]{new Item(name, initialSellIn, initialQuality)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(name, app.items[0].getName()); // name is not impacted by daily update
        assertEquals(expectedSellIn, app.items[0].getSellIn());
        assertEquals(expectedQuality, app.items[0].getQuality()); // quality is never negative
    }

    @Test
    void dailyUpdateUsual2() {
        String name = "foo2";
        int initialSellIn = 20;
        int initialQuality = 40;
        int expectedSellIn = initialSellIn - 1;
        int expectedQuality = initialQuality - ItemCategory.USUAL_QUALITY_STEP;
        Item[] items = new Item[]{new Item(name, initialSellIn, initialQuality)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(name, app.items[0].getName()); // name is not impacted by daily update
        assertEquals(expectedSellIn, app.items[0].getSellIn());
        assertEquals(expectedQuality, app.items[0].getQuality());
    }
}
