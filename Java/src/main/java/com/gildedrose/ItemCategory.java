package com.gildedrose;

/**
 *
 * @author pcjfm
 */
public enum ItemCategory {
    USUAL("usual"),
    SULFURAS("Sulfuras", 0),
    AGED_BRIE("Aged Brie"),
    BACKSTAGE_PASSES("Backstage passes");

    public static final int USUAL_QUALITY_STEP = 1;

    private final String name;
    private final int dailyQualityStep;

    private ItemCategory(String name, int dailyQualityStep) {
        this.name = name;
        this.dailyQualityStep = dailyQualityStep;
    }

    private ItemCategory(String name) {
        this.name = name;
        this.dailyQualityStep = USUAL_QUALITY_STEP;
    }

    public String getName() {
        return name;
    }

    public int getDailyQualityStep() {
        return dailyQualityStep;
    }

    public static ItemCategory getCategory(String name) {
        for (ItemCategory category : ItemCategory.values()) {
            if (category.getName().equalsIgnoreCase(name.trim())) {
                return category;
            }
        }
        return USUAL;
    }

}
