package com.catoni.models.dto;

import com.catoni.models.enums.ResourceTypes;
import java.util.List;

public class ResourcesPositionsDto {

    private ResourceTypes resource;

    private List<BuildingDto> positions;

    public ResourceTypes getResource() {
        return resource;
    }

    public void setResource(ResourceTypes resource) {
        this.resource = resource;
    }

    public List<BuildingDto> getPositions() {
        return positions;
    }

    public void setPositions(List<BuildingDto> positions) {
        this.positions = positions;
    }
}
