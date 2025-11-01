package com.alkra.estimates.model;

public enum ProductType {
    UPVC_WINDOW("UPVC Window"),
    UPVC_DOOR("UPVC Door"),
    MODULAR_KITCHEN("Modular Kitchen"),
    GLASS_PARTITION("Glass Partition"),
    MOSQUITO_NET("Mosquito Net");

    private final String displayName;

    ProductType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
