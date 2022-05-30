package com.catoni.models;

import com.catoni.models.enums.MoveTypes;
import com.catoni.models.enums.ResourceTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Move {

    private MoveTypes move = MoveTypes.END_TURN;
    public List<ResourceTypes> objects = new ArrayList<>();

    public Move() {}

    public Move(MoveTypes move, List<ResourceTypes> objects) {
        this.move = move;
        this.objects = objects;
    }

    public MoveTypes getMove() {
        return move;
    }

    public void setMove(MoveTypes move) {
        this.move = move;
    }

    public List<ResourceTypes> getObjects() {
        return objects;
    }

    public void setObjects(List<ResourceTypes> objects) {
        this.objects = objects;
    }
}
