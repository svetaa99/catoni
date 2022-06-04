package com.catoni.models;

import com.catoni.models.dto.BuildingDto;
import com.catoni.models.dto.RoadDto;
import com.catoni.models.enums.BuildingTypes;
import com.catoni.models.enums.Status;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            for (int col = 0; col <= maxColForRow(row); col++) {
                if (row < 3) {
                    if (col % 2 == 0) {
                        int colIndex = col == maxColForRow(row) && row == 2 ? col : col + 1;
                        Road roadRight = new Road(row, col, row, col + 1, null, Status.FREE);
                        Road roadDown = new Road(row, col, row + 1, colIndex, null, Status.FREE);

                        if (col != maxColForRow(row)){
                            roads.add(roadRight);
                        }
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
                        if(row != 5)
                            roads.add(roadDown);
                    } else {
                        Road roadRight = new Road(row, col, row, col + 1, null, Status.FREE);
                        if(col != maxColForRow(row))
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

    private int maxColForRow(int row){
        if (row == 0 || row == 5)
            return 6;
        else if (row == 1 || row == 4)
            return 8;
        else if (row == 2 || row == 3)
            return 10;
        return -1;
    }

    public List<Building> getAvailableBuildings(){
        return buildings.stream().filter(b -> b.getStatus() == Status.FREE).collect(Collectors.toList());
    }

    public List<Road> getAvailableRoadsForPlayer(String playerName){ // u pravilima uvek bot pisemo?
        List<Building> playersBuildings = buildings.stream().filter(b -> b.getOwner().equals(playerName)).collect(Collectors.toList());
        List<Road> all = new ArrayList<>();
        for (Building building: playersBuildings) {
            all.addAll(getRoadsForBuilding(building));
        }
        return all;
    }

    public List<Road> getRoadsForBuilding(Building b){
        return roads.stream().filter(r -> (r.getBuilding1().equals(b) || r.getBuilding2().equals(b)) && r.getStatus() == Status.FREE).collect(Collectors.toList());
    }

    public Road addRoad(RoadDto road){
        for (Road r: roads) {
            if(r.getRow1() == road.getRow1() && r.getRow2() == road.getRow2() && r.getCol1() == road.getCol1() && r.getCol2() == road.getCol2() && r.getStatus() == Status.FREE){
                r.setOwner(road.getPlayer());
                r.setStatus(Status.TAKEN);
                return r;
            }
        }
        return null;
    }

    public Building addBuilding(BuildingDto building){
        //SET ONE TO TAKEN AND ALL WITH DISTANCE = 1 TO BLOCKED(2 or 3)
        for (Building b: buildings){
            if(b.getRow() == building.getRow() && b.getColumn() == building.getCol()){
                b.setStatus(Status.TAKEN);
                b.setOwner(building.getPlayerName());
                b.setType(building.getType());
                findNearbyBuildings(b);
            }
        }
        return null;
    }

    private void findNearbyBuildings(Building b){
        List<Road> exitRoads = roads.stream().filter
                (r -> (r.getRow1() == b.getRow() && r.getCol1() == b.getColumn()) || (r.getRow2() == b.getRow() && r.getCol2() == b.getColumn()))
                .collect(Collectors.toList());
        for(Road r: exitRoads){
            int row;
            int col;
            if(r.getRow1() == b.getRow() && r.getCol1() == b.getColumn()){
                row = r.getRow2();
                col = r.getCol2();
            }
            else{
                row = r.getRow1();
                col = r.getCol1();
            }
            findBuildingForCoordinates(row, col);
        }
    }

    private void findBuildingForCoordinates(int row, int col){
        for(Building b: buildings){
            if(b.getRow() == row && b.getColumn() == col)
                b.setStatus(Status.BLOCKED);
        }
    }

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
