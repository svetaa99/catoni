package com.catoni.models;

import com.catoni.models.enums.ResourceTypes;

public class ResourceChance {

    private ResourceTypes type;

    private double chance;

    @Override
    public String toString() {
        return "{" +
                "type=" + type +
                ", chance=" + chance +
                '}';
    }

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
