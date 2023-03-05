package com.gildedrose;

public class Item {

    private final String name;
    private int sellIn; // number of days we have to sell the item
    private int quality; // how valuable the item is

    private final ItemCategory category; // identifies item category

    /**
     * Item to sell.
     * Values are checked and corrected at creation according to category.
     * 
     * @param name item name (category)
     * @param sellIn number of days we have to sell the item
     * @param quality how valuable the item is 
     */
    public Item(String name, int sellIn, int quality) {
        this.name = name; // should we add some formatting check ?
        category = ItemCategory.getCategory(name);
        this.setSellIn(sellIn);
        setQuality(quality);
    }

    @Override
    public String toString() {
        return this.getName() + ", " + this.getSellIn() + ", " + this.getQuality();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the sellIn
     */
    public int getSellIn() {
        return sellIn;
    }

    /**
     * @param sellIn the sellIn to set
     */
    public void setSellIn(int sellIn) {
        switch (category) {
            case SULFURAS:
                this.sellIn = 1000;
                break;
            default:
                this.sellIn = sellIn;
        }
    }

    /**
     * @return the quality
     */
    public int getQuality() {
        return quality;
    }

    /**
     * @param quality the quality to set
     */
    public void setQuality(int quality) {
        switch (category) {
            case SULFURAS:
                this.quality = 80;
                break;
            default:
                this.quality = (quality < 0 ? 0 : (quality > 50 ? 50 : quality));
        }
    }

}
