package com.catoni.models.dto;

import com.catoni.models.enums.ResourceTypes;

public class ResourceChanceDto {
    private ResourceTypes type;
    private double chance;

    public ResourceTypes getType() {
        return type;
    }

    public void setType(ResourceTypes type) {
        this.type = type;
    }

    public double getChance() {
        return chance;
    }

    public void setChance(double chance) {
        this.chance = chance;
    }
}
