package com.catoni.models.dto;

import com.catoni.models.ResourceChance;

import java.util.List;

public class ChanceInitDto {

    private List<BuildingDto> building;

    private List<List<ResourceChance>> chances;

    public List<BuildingDto> getBuilding() {
        return building;
    }

    public void setBuilding(List<BuildingDto> building) {
        this.building = building;
    }

    public List<List<ResourceChance>> getChances() {
        return chances;
    }

    public void setChances(List<List<ResourceChance>> chances) {
        this.chances = chances;
    }
}
