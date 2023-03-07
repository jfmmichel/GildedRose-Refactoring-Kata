package com.gildedrose;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ItemCategoryTest {

    /* Special categories
       - "Sulfuras"
       - "Aged Brie"
       - "Backstage passes"
     */
    @Test
    void checkCategoryName() {
        assertEquals(ItemCategory.USUAL, ItemCategory.getCategory("foo"));
        assertEquals(ItemCategory.USUAL, ItemCategory.getCategory("foo1, item"));
        assertEquals(ItemCategory.USUAL, ItemCategory.getCategory("usual"));
        assertEquals(ItemCategory.USUAL, ItemCategory.getCategory("Sulfura"));
        assertEquals(ItemCategory.USUAL, ItemCategory.getCategory("Aged_Brie"));
        assertEquals(ItemCategory.USUAL, ItemCategory.getCategory("AGED"));
        assertEquals(ItemCategory.USUAL, ItemCategory.getCategory("Backstage pass"));

        assertEquals(ItemCategory.CONJURED, ItemCategory.getCategory("Conjured"));
        assertEquals(ItemCategory.CONJURED, ItemCategory.getCategory("Conjured, item name"));
        assertEquals(ItemCategory.CONJURED, ItemCategory.getCategory("CONJURED"));
        assertEquals(ItemCategory.CONJURED, ItemCategory.getCategory("CONJURED ITEM"));

        assertEquals(ItemCategory.SULFURAS, ItemCategory.getCategory("Sulfuras"));
        assertEquals(ItemCategory.SULFURAS, ItemCategory.getCategory("Sulfuras, item name"));
        assertEquals(ItemCategory.SULFURAS, ItemCategory.getCategory("SULFURAS"));
        assertEquals(ItemCategory.SULFURAS, ItemCategory.getCategory("SULFURAS ITEM"));

        assertEquals(ItemCategory.AGED_BRIE, ItemCategory.getCategory("Aged Brie"));
        assertEquals(ItemCategory.AGED_BRIE, ItemCategory.getCategory("Aged Brie, cheese"));
        assertEquals(ItemCategory.AGED_BRIE, ItemCategory.getCategory("AGED BRIE"));
        assertEquals(ItemCategory.AGED_BRIE, ItemCategory.getCategory("AGED BRIE ITEM"));

        assertEquals(ItemCategory.BACKSTAGE_PASSES, ItemCategory.getCategory("Backstage passes"));
        assertEquals(ItemCategory.BACKSTAGE_PASSES, ItemCategory.getCategory("Backstage passes, item"));
        assertEquals(ItemCategory.BACKSTAGE_PASSES, ItemCategory.getCategory("Backstage passes item"));
        assertEquals(ItemCategory.BACKSTAGE_PASSES, ItemCategory.getCategory("BACKSTAGE PASSES"));
        assertEquals(ItemCategory.BACKSTAGE_PASSES, ItemCategory.getCategory("BACKSTAGE PASSES ITEM"));
    }

}
