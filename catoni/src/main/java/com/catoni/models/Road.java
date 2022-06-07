package com.catoni.models;
import com.catoni.models.dto.RoadDto;
import com.catoni.models.enums.Status;

import java.util.Objects;

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
                "row1=" + row1 +
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

    public int getRow1() {
        return row1;
    }

    public void setRow1(int row1) {
        this.row1 = row1;
    }

    public int getCol1() {
        return col1;
    }

    public void setCol1(int col1) {
        this.col1 = col1;
    }

    public int getRow2() {
        return row2;
    }

    public void setRow2(int row2) {
        this.row2 = row2;
    }

    public int getCol2() {
        return col2;
    }

    public void setCol2(int col2) {
        this.col2 = col2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Road road = (Road) o;
        return row1 == road.row1 && col1 == road.col1 && row2 == road.row2 && col2 == road.col2;
    }

    public boolean equalsDto(RoadDto r){
        return row1 == r.getRow1() && row2 == r.getRow2() && col1 == r.getCol1() && col2 == r.getCol2();
    }

    public boolean continues(Road r){
        if(r.getBuilding1().equals(building1) && !r.getBuilding2().equals(building2) && status == Status.FREE){
            return true;
        }
        else if(r.getBuilding2().equals(building1) && !r.getBuilding1().equals(building2) && status == Status.FREE){
            return true;
        }
        else if(r.getBuilding1().equals(building2) && !r.getBuilding2().equals(building1) && status == Status.FREE){
            return true;
        }
        else if(r.getBuilding2().equals(building2) && !r.getBuilding1().equals(building1) && status == Status.FREE){
            return true;
        }
        return false;
    }
    @Override
    public int hashCode() {
        return Objects.hash(row1, col1, row2, col2);
    }
}
