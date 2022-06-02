package com.catoni.models;

import com.catoni.models.enums.Status;

// class Road
    /*
       Building building1;
       Building building2;

       owner;
       status (FREE, BLOCKED, TAKEN)

     */
public class Road {
    private Building building1;

    private int row1;

    private int col1;

    private int row2;

    private int col2;

    private Building building2;

    private String owner;

    private Status status;

    public Road() {}

    public Road(Building building1, Building building2, String owner, Status status) {
        this.building1 = building1;
        this.building2 = building2;
        this.owner = owner;
        this.status = status;
    }

    public Road(int row1, int col1, int row2, int col2, String owner, Status status) {
        this.row1 = row1;
        this.col1 = col1;
        this.row2 = row2;
        this.col2 = col2;
        this.owner = owner;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Road{" +
                ", row1=" + row1 +
                ", col1=" + col1 +
                ", row2=" + row2 +
                ", col2=" + col2 +
                ", owner='" + owner + '\'' +
                ", status=" + status +
                '}' + '\n';
    }

    public Building getBuilding1() {
        return building1;
    }

    public void setBuilding1(Building building1) {
        this.building1 = building1;
    }

    public Building getBuilding2() {
        return building2;
    }

    public void setBuilding2(Building building2) {
        this.building2 = building2;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
