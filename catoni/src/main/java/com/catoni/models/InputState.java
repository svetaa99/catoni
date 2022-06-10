package com.catoni.models;

import com.catoni.models.enums.BuildingTypes;
import com.catoni.models.enums.ResourceTypes;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InputState {

    private static InputState instance = null;

    private List<ResourceTypes> resources;

    private int distanceToHarbor;

    private Map<String, State> playerStates;

    public static InputState getInstance(){
        if(instance == null)
            instance = new InputState();
        return instance;
    }

    @Autowired
    private Position position = Position.getInstance();

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

    public InputState updateState(InputState is) {
        this.resources = is.getResources();
        this.playerStates = is.getPlayerStates();
        this.distanceToHarbor = is.getDistanceToHarbor();
        this.position = is.getPosition();
        return this;
    }

    public List<ResourceTypes> findExcess(int wood, int clay, int grain, int sheep, int rock, BuildingTypes goal, ResourceTypes cardToGet){
        List<ResourceTypes> retVal = new ArrayList<>();
        if(goal == BuildingTypes.HOUSE){
            for(int i = 0; i < rock; i++){
                retVal.add(ResourceTypes.ROCK);
            }

            for(int i = 1; i < wood; i++){
                retVal.add(ResourceTypes.WOOD);
            }
            for(int i = 1; i < clay; i++){
                retVal.add(ResourceTypes.CLAY);
            }
            for(int i = 1; i < grain; i++){
                retVal.add(ResourceTypes.GRAIN);
            }
            for(int i = 1; i < sheep; i++){
                retVal.add(ResourceTypes.SHEEP);
            }
        }
        else if(goal == BuildingTypes.HOTEL){
            for(int i = 0; i < wood; i++){
                retVal.add(ResourceTypes.WOOD);
            }
            for(int i = 0; i < clay; i++){
                retVal.add(ResourceTypes.CLAY);
            }
            for(int i = 0; i < sheep; i++){
                retVal.add(ResourceTypes.SHEEP);
            }
            for(int i = 3; i < grain; i++){
                retVal.add(ResourceTypes.GRAIN);
            }
            for(int i = 4; i < rock; i++){
                retVal.add(ResourceTypes.ROCK);
            }
        }
        return retVal;
    }

    public void updateChances(String player, Building newBuilding){
        Map<ResourceTypes, Double> oldChances = playerStates.get(player).getResourceChances();
        List<ResourceChance> newChances = newBuilding.getChanceList();
        for (ResourceChance rc: newChances) {
            double oldChance = oldChances.get(rc.getType());
            oldChances.replace(rc.getType(), oldChance + rc.getChance());
        }
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

    public void addResources(List<ResourceTypes> resources){
        this.resources.addAll(resources);
    }

    public void addResource(ResourceTypes resource) { this.resources.add(resource) ;}

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
