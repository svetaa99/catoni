package com.catoni.models;

import com.catoni.models.enums.ResourceTypes;

import java.util.List;
import java.util.Map;

public class InputState {

    private List<ResourceTypes> resources;
    private int distanceToHarbor;
    // Map<playerName, State>
    private Map<String, State> playerStates;
    // FALI POZICIJA
    private boolean isMyTurn;

    public InputState() {}

    public InputState(List<ResourceTypes> resources, int distanceToHarbor, Map<String, State> playerStates, boolean isMyTurn) {
        this.resources = resources;
        this.distanceToHarbor = distanceToHarbor;
        this.playerStates = playerStates;
        this.isMyTurn = isMyTurn;
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
