package com.gildedrose;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class UpdateItemSulfurasTest {
    /*
At the end of each day our system lowers both values for every item :
    SellIn--;
    
Special categories
- "Sulfuras"
    - never has to be sold (SellIn > 0 ?)
    - never decreases in Quality
    - Quality is 80 and it never alters
     */
    
    private static final ItemCategory CATEGORY_SULFURAS = ItemCategory.SULFURAS;

    @Test
    void updateUsualOutdatedMinQUality() {
        String name = CATEGORY_SULFURAS.getName() + ", superior";
        GildedRose app = new GildedRose(new Item[]{new Item(name, -5, -7)});
        app.updateQuality();
        Item item = app.items[0];
        assertTrue(item.getSellIn()> 0); // never have to be sold
        assertTrue(item.getQuality() == CATEGORY_SULFURAS.getMaxQuality());
        assertTrue(item.getQuality() >= CATEGORY_SULFURAS.getMinQuality() && item.getQuality() <= CATEGORY_SULFURAS.getMaxQuality());
    }

    @Test
    void updateUsualMinQuality() {
        int initialSellIn = 70;
        int nbUpdate = initialSellIn + 5; // => outdated ?
        GildedRose app = new GildedRose(new Item[]{new Item(CATEGORY_SULFURAS.getName() + ", maximus", -5, 60)});
        for (int i = 0; i < nbUpdate; i++) {
            app.updateQuality();
        }
        Item item = app.items[0];
        assertTrue(item.getSellIn()> 0);
        assertTrue(item.getQuality() == CATEGORY_SULFURAS.getMaxQuality());
        assertTrue(item.getQuality() >= CATEGORY_SULFURAS.getMinQuality() && item.getQuality() <= CATEGORY_SULFURAS.getMaxQuality());
    }
    
}
