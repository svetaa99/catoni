package com.catoni.models;

import com.catoni.models.enums.BuildingTypes;
import com.catoni.models.enums.Status;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Position {
    private List<Building> buildings;

    private List<Road> roads;

    public Position() {
        this.buildings = new ArrayList<>();
        this.roads = new ArrayList<>();

        for(int row = 0; row <= 5; row++) {
            for (int col = 0; col <= 10; col++) {
                if (row == 0 || row == 5) {
                    if (col > 6) {
                        continue;
                    }
                    Building building = new Building(row, col, calculateHarbor(row, col), null, BuildingTypes.NONE, Status.FREE);
                    buildings.add(building);
                }

                else if (row == 1 || row == 4) {
                    if (col > 8) {
                        continue;
                    }
                    Building building = new Building(row, col, calculateHarbor(row, col), null, BuildingTypes.NONE, Status.FREE);
                    buildings.add(building);
                }

                else {
                    Building building = new Building(row, col, calculateHarbor(row, col), null, BuildingTypes.NONE, Status.FREE);
                    buildings.add(building);
                }
            }
        }

        for (int row = 0; row <= 5; row++) {
            for (int col = 0; col <= 10; col++) {
                if (row < 3) {
                    if (col % 2 == 0) {
                        Road roadRight = new Road(row, col, row, col + 1, null, Status.FREE);
                        Road roadDown = new Road(row, col, row + 1, col + 1, null, Status.FREE);
                        roads.add(roadRight);
                        roads.add(roadDown);
                    } else {
                        Road roadRight = new Road(row, col, row, col + 1, null, Status.FREE);
                        roads.add(roadRight);
                    }
                } else {
                    if (col % 2 != 0) {
                        Road roadRight = new Road(row, col, row, col + 1, null, Status.FREE);
                        Road roadDown = new Road(row, col, row + 1, col - 1, null, Status.FREE);
                        roads.add(roadRight);
                        roads.add(roadDown);
                    } else {
                        Road roadRight = new Road(row, col, row, col + 1, null, Status.FREE);
                        roads.add(roadRight);
                    }
                }
            }
        }
    }

    boolean calculateHarbor(int row, int col) {
        if ((row == 0 || row == 5) && (col == 0 || col == 1 || col == 3 || col == 4)) return true;
        else if ((row == 1 || row == 4) && (col == 0 || col == 7 || col == 8)) return true;
        else if ((row == 2 || row == 3) && (col == 1 || col == 10)) return true;
        else return false;
    }

    // Postition methods
    /*
    getPlayerPosition(String playerName) {
        returns all player's roads and buildings
    }

    getAvailableMoves(PlayerPosition position) {
        possibleRoads = List<> roads;
        possibleBuildings = List<> buildings;
    }

     */

    @Override
    public String toString() {
        return "Position{" +
                "buildings=" + buildings +
                ", roads=" + roads +
                '}';
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }

    public List<Road> getRoads() {
        return roads;
    }

    public void setRoads(List<Road> roads) {
        this.roads = roads;
    }



}