package com.catoni.models;

import com.catoni.models.enums.BuildingTypes;
import com.catoni.models.enums.ResourceTypes;
import com.catoni.models.enums.Status;

import java.util.Map;
import java.util.Objects;

public class Building {
    private int row;

    private int column;

    private boolean isHarbor;

    private String owner;

    private BuildingTypes type;

    private Status status;

    private Map<ResourceTypes, Double> resourceChances;

    public Building() {}

    public Building(int row, int column, boolean isHarbor, String owner, BuildingTypes type, Status status) {
        this.row = row;
        this.column = column;
        this.isHarbor = isHarbor;
        this.owner = owner;
        this.type = type;
        this.status = status;
    }

    public Building(int row, int column, boolean isHarbor, String owner, BuildingTypes type, Status status, Map<ResourceTypes, Double> resourceChances) {
        this.row = row;
        this.column = column;
        this.isHarbor = isHarbor;
        this.owner = owner;
        this.type = type;
        this.status = status;
        this.resourceChances = resourceChances;
    }

    @Override
    public String toString() {
        return "Building{" +
                "row=" + row +
                ", column=" + column +
                ", isHarbor=" + isHarbor +
                ", owner='" + owner + '\'' +
                ", type=" + type +
                ", status=" + status +
                '}' + '\n';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Building building = (Building) o;
        return row == building.row && column == building.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    public Map<ResourceTypes, Double> getResourceChances() {
        return resourceChances;
    }

    public void setResourceChances(Map<ResourceTypes, Double> resourceChances) {
        this.resourceChances = resourceChances;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean isHarbor() {
        return isHarbor;
    }

    public void setHarbor(boolean harbor) {
        isHarbor = harbor;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public BuildingTypes getType() {
        return type;
    }

    public void setType(BuildingTypes type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
