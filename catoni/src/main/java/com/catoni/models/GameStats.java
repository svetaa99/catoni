package com.catoni.models;

public class GameStats {

    private int houses;
    private int hotels;
    private int roads;

    public GameStats() {}

    public GameStats(int houses, int hotels, int roads) {
        this.houses = houses;
        this.hotels = hotels;
        this.roads = roads;
    }

    @Override
    public String toString() {
        return "GameStats{" +
                "houses=" + houses +
                ", hotels=" + hotels +
                ", roads=" + roads +
                '}';
    }

    public int getHouses() {
        return houses;
    }

    public void setHouses(int houses) {
        this.houses = houses;
    }

    public int getHotels() {
        return hotels;
    }

    public void setHotels(int hotels) {
        this.hotels = hotels;
    }

    public int getRoads() {
        return roads;
    }

    public void setRoads(int roads) {
        this.roads = roads;
    }
}
