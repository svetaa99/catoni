package com.catoni.models;

import com.catoni.constants.BotConstants;
import com.catoni.models.enums.CrazyTypes;
import com.catoni.models.enums.ResourceTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class State {
    private int numberOfRoads;
    private int numberOfHouses;
    private int numberOfHotels;
    private int numberOfKnights;
    private List<ResourceTypes> resources;
    private List<CrazyTypes> craziesList;
    private Map<ResourceTypes, Double> resourceChances;

    public State() {
        numberOfRoads = 0;
        numberOfHouses = 0;
        numberOfHotels = 0;
        numberOfKnights = 0;
        resources = new ArrayList<>();
        craziesList = new ArrayList<>();
        resourceChances = new HashMap<>();
        resourceChances.put(ResourceTypes.WOOD, 0.0);
        resourceChances.put(ResourceTypes.CLAY, 0.0);
        resourceChances.put(ResourceTypes.GRAIN, 0.0);
        resourceChances.put(ResourceTypes.SHEEP, 0.0);
        resourceChances.put(ResourceTypes.ROCK, 0.0);
    }

    public State(int numberOfRoads, int numberOfHouses, int numberOfHotels, int numberOfKnights,
                 List<CrazyTypes> craziesList, Map<ResourceTypes, Double> resourceChances) {
        this.numberOfRoads = numberOfRoads;
        this.numberOfHouses = numberOfHouses;
        this.numberOfHotels = numberOfHotels;
        this.numberOfKnights = numberOfKnights;
        this.craziesList = craziesList;
        this.resourceChances = resourceChances;
    }

    public int getTotalGamePoints(String name) {
        return numberOfHouses + numberOfHotels * 2 + calculateVictoryPoints() +
                calculateKnightsBonus(name) + calculateRoadsBonus(name);
    }

    private int calculateVictoryPoints() {
        int victoryPoints = 0;
        for (CrazyTypes item : craziesList) {
            if (item.equals(CrazyTypes.VICTORY_POINT)) {
                victoryPoints++;
            }
        }
        return victoryPoints;
    }

    private int calculateKnightsBonus(String name) {
        int knightsBonus = 0;
        boolean hasMostKnights = GlobalState.getInstance().mostKnights.containsValue(name);
        if (hasMostKnights) {
            System.out.println("HAS MOST KNIGHTS BONUS");
            knightsBonus = 2;
        }
        return knightsBonus;
    }

    private int calculateRoadsBonus(String name) {
        int roadsBonus = 0;
        boolean hasLongestRoad = GlobalState.getInstance().longestRoad.containsValue(name);
        if (hasLongestRoad) {
            System.out.println("HAS MOST ROADS BONUS");
            roadsBonus = 2;
        }
        return roadsBonus;
    }

    public int getNumberOfRoads() {
        return numberOfRoads;
    }

    public void setNumberOfRoads(int numberOfRoads) {
        this.numberOfRoads = numberOfRoads;
    }

    public int getNumberOfHouses() {
        return numberOfHouses;
    }

    public void setNumberOfHouses(int numberOfHouses) {
        this.numberOfHouses = numberOfHouses;
    }

    public int getNumberOfHotels() {
        return numberOfHotels;
    }

    public void setNumberOfHotels(int numberOfHotels) {
        this.numberOfHotels = numberOfHotels;
    }

    public int getNumberOfKnights() {
        return numberOfKnights;
    }

    public void setNumberOfKnights(int numberOfKnights) {
        this.numberOfKnights = numberOfKnights;
    }

    public List<CrazyTypes> getCraziesList() {
        return craziesList;
    }

    public void setCraziesList(List<CrazyTypes> craziesList) {
        this.craziesList = craziesList;
    }

    public Map<ResourceTypes, Double> getResourceChances() {
        return resourceChances;
    }

    public void setResourceChances(Map<ResourceTypes, Double> resourceChances) {
        this.resourceChances = resourceChances;
    }
    public List<ResourceTypes> getResources() {
        return resources;
    }

    public void setResources(List<ResourceTypes> resources) {
        this.resources = resources;
    }

    public void addResources(List<ResourceTypes> resources){
        this.resources.addAll(resources);
    }
}
