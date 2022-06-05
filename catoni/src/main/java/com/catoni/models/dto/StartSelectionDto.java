package com.catoni.models.dto;

public class StartSelectionDto {
    private BuildingDto building;
    private RoadDto road;

    public BuildingDto getBuilding() {
        return building;
    }

    public void setBuilding(BuildingDto building) {
        this.building = building;
    }

    public RoadDto getRoad() {
        return road;
    }

    public void setRoad(RoadDto road) {
        this.road = road;
    }
}
