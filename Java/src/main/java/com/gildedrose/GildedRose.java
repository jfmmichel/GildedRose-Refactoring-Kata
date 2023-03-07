package com.gildedrose;

class GildedRose {

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    private void updateItemQuality(Item item) {
        //
        item.setSellIn(item.getSellIn() - 1);
        //
        ItemCategory category = item.getCategory();
        int speed = (item.getSellIn() < 0 ? 2 : 1);
        int qualityDelta = speed * category.getPositiveSellInQualityStep();

        switch (category) {
            case SULFURAS:
                break;
            case AGED_BRIE:
                item.setQuality(item.getQuality() + qualityDelta);
                break;
            case BACKSTAGE_PASSES:
                if (item.getSellIn() < 0) {
                    item.setQuality(0);
                } else if (item.getSellIn() <= 5) {
                    item.setQuality(item.getQuality() + 3);
                } else if (item.getSellIn() <= 10) {
                    item.setQuality(item.getQuality() + 2);
                } else {
                    item.setQuality(item.getQuality() + qualityDelta);
                }
                break;
            default:
                item.setQuality(item.getQuality() - qualityDelta);

        }
    }

    public void updateQuality() {
        for (Item item : items) {
            updateItemQuality(item);
        }
    }

    public static void main(String[] args) {
        ItemCategory CATEGORY_AGED_BRIE = ItemCategory.AGED_BRIE;
        String name = CATEGORY_AGED_BRIE.getName() + ", foo";
        int initialSellIn = -3;
        int initialQuality = 0;
        GildedRose app = new GildedRose(new Item[]{new Item(name, initialSellIn, initialQuality)});
        app.updateQuality();
        Item item = app.items[0];
        System.err.println(item.toString());
        System.err.println(item.getCategory().getName());
        int expectedSellIn = initialSellIn - 1;
        int expectedQuality = initialQuality + 2 * CATEGORY_AGED_BRIE.getPositiveSellInQualityStep(); // "Aged Brie" : increases in Quality the older it gets, should quality increase twice as fast when sell by date has passed ?
        System.err.println("(" + expectedSellIn + " <=> " + item.getSellIn());
        System.err.println("Expected quality daily update for " + CATEGORY_AGED_BRIE.getName() + " should be updated from [" + initialQuality + "] to [" + expectedQuality + "] instead of [" + item.getQuality() + "]"); // quality is never negative

    }
}
