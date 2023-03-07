package com.gildedrose;

/**
 *
 * @author pcjfm
 */
public enum ItemCategory {
    USUAL("usual"),
    CONJURED("Conjured",2),
    SULFURAS("Sulfuras", 0,  80, 80),
    AGED_BRIE("Aged Brie"),
    BACKSTAGE_PASSES("Backstage passes");

    public static final int USUAL_QUALITY_STEP = 1;
    public static final int MIN_QUALITY = 0;
    public static final int MAX_QUALITY = 50;

    private final String name;
    private final int positiveSellInQualityStep;
    private final int minQuality;
    private final int maxQuality;

    private ItemCategory(String name, int positiveSellInQualityStep, int minQuality, int maxQuality) {
        this.name = name;
        this.positiveSellInQualityStep = positiveSellInQualityStep;
        this.minQuality = minQuality;
        this.maxQuality = maxQuality;
    }

    private ItemCategory(String name, int positiveSellInQualityStep) {
        this.name = name;
        this.positiveSellInQualityStep = positiveSellInQualityStep;
        this.minQuality = MIN_QUALITY;
        this.maxQuality = MAX_QUALITY;
    }

    private ItemCategory(String name) {
        this.name = name;
        this.positiveSellInQualityStep = USUAL_QUALITY_STEP;
        this.minQuality = MIN_QUALITY;
        this.maxQuality = MAX_QUALITY;
    }

    public String getName() {
        return name;
    }

    public int getPositiveSellInQualityStep() {
        return positiveSellInQualityStep;
    }

    public int getMinQuality() {
        return minQuality;
    }

    public int getMaxQuality() {
        return maxQuality;
    }

    public static ItemCategory getCategory(Item item) {
        return getCategory(item.getName());
    }

    /**
     * USUAL category is provided by default For specific categories, item name
     * should start with category name ? Category check is case insensitive ?
     *
     * @todo check specifications for category
     */
    public static ItemCategory getCategory(String name) {
        for (ItemCategory category : ItemCategory.values()) {
            if (name.toUpperCase().startsWith(category.getName().toUpperCase())) {
                return category;
            }
        }
        return USUAL;
    }

}
