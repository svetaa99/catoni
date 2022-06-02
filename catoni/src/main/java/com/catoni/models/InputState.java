package com.catoni.models;

import com.catoni.models.enums.ResourceTypes;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class InputState {

    private List<ResourceTypes> resources;

    private int distanceToHarbor;

    private Map<String, State> playerStates;

    @Autowired
    private Position position = new Position();

    private boolean isMyTurn;

    public InputState() {}

    public InputState(List<ResourceTypes> resources, int distanceToHarbor, Map<String, State> playerStates, boolean isMyTurn) {
        this.resources = resources;
        this.distanceToHarbor = distanceToHarbor;
        this.playerStates = playerStates;
        this.isMyTurn = isMyTurn;
    }

    public InputState(List<ResourceTypes> resources, int distanceToHarbor, Map<String, State> playerStates, Position position, boolean isMyTurn) {
        this.resources = resources;
        this.distanceToHarbor = distanceToHarbor;
        this.playerStates = playerStates;
        this.position = position;
        this.isMyTurn = isMyTurn;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<ResourceTypes> getResources() {
        return resources;
    }

    public void setResources(List<ResourceTypes> resources) {
        this.resources = resources;
    }

    public int getDistanceToHarbor() {
        return distanceToHarbor;
    }

    public void setDistanceToHarbor(int distanceToHarbor) {
        this.distanceToHarbor = distanceToHarbor;
    }

    public Map<String, State> getPlayerStates() {
        return playerStates;
    }

    public void setPlayerStates(Map<String, State> playerStates) {
        this.playerStates = playerStates;
    }

    public boolean isMyTurn() {
        return isMyTurn;
    }

    public void setMyTurn(boolean myTurn) {
        isMyTurn = myTurn;
    }

    @Override
    public String toString() {
        return "InputState{" +
                "resources=" + resources +
                ", distanceToHarbor=" + distanceToHarbor +
                ", playerStates=" + playerStates +
                ", isMyTurn=" + isMyTurn +
                '}';
    }
}
