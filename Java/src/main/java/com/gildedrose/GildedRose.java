package com.gildedrose;

/*
You can make the UpdateQuality method static if you like
Feel free to make any changes to the UpdateQuality method and add any new code as long as everything still works correctly. 

New feature : new category of items
	- "Conjured" items degrade in Quality twice as fast as normal items
*/
class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            if (!items[i].getName().equals("Aged Brie")
                    && !items[i].getName().equals("Backstage passes to a TAFKAL80ETC concert")) {
                if (items[i].getQuality() > 0) {
                    if (!items[i].getName().equals("Sulfuras, Hand of Ragnaros")) {
                        items[i].setQuality(items[i].getQuality() - 1);
                    }
                }
            } else {
                if (items[i].getQuality() < 50) {
                    items[i].setQuality(items[i].getQuality() + 1);

                    if (items[i].getName().equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (items[i].getSellIn() < 11) {
                            if (items[i].getQuality() < 50) {
                                items[i].setQuality(items[i].getQuality() + 1);
                            }
                        }

                        if (items[i].getSellIn() < 6) {
                            if (items[i].getQuality() < 50) {
                                items[i].setQuality(items[i].getQuality() + 1);
                            }
                        }
                    }
                }
            }

            if (!items[i].getName().equals("Sulfuras, Hand of Ragnaros")) {
                items[i].setSellIn(items[i].getSellIn() - 1);
            }

            if (items[i].getSellIn() < 0) {
                if (!items[i].getName().equals("Aged Brie")) {
                    if (!items[i].getName().equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (items[i].getQuality() > 0) {
                            if (!items[i].getName().equals("Sulfuras, Hand of Ragnaros")) {
                                items[i].setQuality(items[i].getQuality() - 1);
                            }
                        }
                    } else {
                        items[i].setQuality(items[i].getQuality() - items[i].getQuality());
                    }
                } else {
                    if (items[i].getQuality() < 50) {
                        items[i].setQuality(items[i].getQuality() + 1);
                    }
                }
            }
        }
    }
}