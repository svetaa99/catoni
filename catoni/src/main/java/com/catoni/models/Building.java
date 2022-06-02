package com.catoni.models;
// int row;
// int column;
// isHarbour;

import com.catoni.models.enums.BuildingTypes;
import com.catoni.models.enums.Status;

public class Building {
    private int row;

    private int column;

    private boolean isHarbor;

    private String owner;

    private BuildingTypes type;

    private Status status;

    public Building() {}

    public Building(int row, int column, boolean isHarbor, String owner, BuildingTypes type, Status status) {
        this.row = row;
        this.column = column;
        this.isHarbor = isHarbor;
        this.owner = owner;
        this.type = type;
        this.status = status;
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
