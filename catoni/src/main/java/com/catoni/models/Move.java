package com.catoni.models;

import com.catoni.models.enums.MoveTypes;
import com.catoni.models.enums.ResourceTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Move {

    public List<MoveTypes> moveList;

    public List<ResourceTypes> objects = new ArrayList<>();

    public List<Building> buildings = new ArrayList<>();

    public List<Road> roads = new ArrayList<>();

    public Trade trade = new Trade();

    public Move() {
        this.moveList = new ArrayList<>();
        moveList.add(MoveTypes.START_TURN);
    }

    public void addBuilding(Building b) {
        this.buildings.add(b);
    }

    public Move(List<ResourceTypes> objects) {
        this.objects = objects;
    }

    public void addMove(MoveTypes move) {
        this.moveList.add(move);
    }

    public List<ResourceTypes> getObjects() {
        return objects;
    }

    public void setObjects(List<ResourceTypes> objects) {
        this.objects = objects;
    }

}
