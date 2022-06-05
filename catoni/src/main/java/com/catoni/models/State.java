package com.catoni.models;

import com.catoni.constants.BotConstants;
import com.catoni.models.enums.CrazyTypes;
import com.catoni.models.enums.ResourceTypes;

import java.util.List;
import java.util.Map;

public class State {
    private int numberOfRoads;
    private int numberOfHouses;
    private int numberOfHotels;
    private int numberOfKnights;
    private List<CrazyTypes> craziesList;
    private Map<ResourceTypes, Double> resourceChances;

    public State() {}

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

}
