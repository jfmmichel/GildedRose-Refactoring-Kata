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

- "Aged Brie" :
    - increases in Quality the older it gets
    - What about negative sellIn ? @TODO

- "Backstage passes", like aged brie, increases in Quality as its SellIn value approaches;
	- Quality increases by 2 when there are 10 days or less 
                        and by 3 when there are 5 days or less
	- Quality drops to 0 after the concert (Sellin < 0)

- "Conjured" (new feature) :
    - items degrade in Quality twice as fast as normal items
     */
    @Test
    void dailyUpdateAgedBrie2() {
        int initialSellIn = 30;
        int initialQuality = 25;
        int nbSteps = 20;
        GildedRose app = new GildedRose(new Item[]{
            new Item(ItemCategory.AGED_BRIE.getName(), initialSellIn, initialQuality),
            new Item(ItemCategory.BACKSTAGE_PASSES.getName(), initialSellIn, initialQuality),
            new Item(ItemCategory.USUAL.getName(), initialSellIn, initialQuality),
            new Item(ItemCategory.SULFURAS.getName(), initialSellIn, initialQuality)
        });
        for (int i = 0; i < nbSteps; i++) {
            app.updateQuality();
        }
        assertEquals(app.items[0].getSellIn(), 5);
        assertEquals(app.items[1].getSellIn(), 5);
        assertEquals(app.items[2].getSellIn(), 5);
        assertTrue(app.items[3].getSellIn() > 0);

        assertEquals(app.items[0].getQuality(), 45);
        assertEquals(app.items[1].getQuality(), 50);
        assertEquals(app.items[2].getQuality(), 5);
        assertTrue(app.items[3].getQuality() == 80);

        app.updateQuality();

        assertEquals(app.items[0].getSellIn(), 4);
        assertEquals(app.items[1].getSellIn(), 4);
        assertEquals(app.items[2].getSellIn(), 4);
        assertTrue(app.items[3].getSellIn() > 0);

        assertEquals(app.items[0].getQuality(), 46);
        assertEquals(app.items[1].getQuality(), 50);
        assertEquals(app.items[2].getQuality(), 4);
        assertTrue(app.items[3].getQuality() == 80);
    }
}
