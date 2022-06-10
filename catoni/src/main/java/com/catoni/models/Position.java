package com.catoni.models;

import com.catoni.exceptions.PositionNotAvailableException;
import com.catoni.models.dto.BuildingDto;
import com.catoni.models.dto.ChanceInitDto;
import com.catoni.models.dto.RoadDto;
import com.catoni.models.enums.BuildingTypes;
import com.catoni.models.enums.Status;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Position {

    private static Position instance = null;

    private List<Building> buildings;

    private List<Road> roads;

    public static Position getInstance()
    {
        if (instance == null)
            instance = new Position();

        return instance;
    }

    public Position() {
        this.buildings = new ArrayList<>();
        this.roads = new ArrayList<>();

        for(int row = 0; row <= 5; row++) {
            for (int col = 0; col <= 10; col++) {
                if (row == 0 || row == 5) {
                    if (col > 6) {
                        continue;
                    }
                    Building building = new Building(row, col, calculateHarbor(row, col), "null", BuildingTypes.NONE, Status.FREE);
                    buildings.add(building);
                }

                else if (row == 1 || row == 4) {
                    if (col > 8) {
                        continue;
                    }
                    Building building = new Building(row, col, calculateHarbor(row, col), "null", BuildingTypes.NONE, Status.FREE);
                    buildings.add(building);
                }

                else {
                    Building building = new Building(row, col, calculateHarbor(row, col), "null", BuildingTypes.NONE, Status.FREE);
                    buildings.add(building);
                }
            }
        }

        for (int row = 0; row <= 5; row++) {
            for (int col = 0; col <= maxColForRow(row); col++) {
                if (row < 3) {
                    if (col % 2 == 0) {
                        int colIndex = col == maxColForRow(row) && row == 2 ? col : col + 1;
                        Road roadRight = new Road(row, col, row, col + 1, "null", Status.FREE);
                        roadRight.setBuilding1(new Building(row, col, calculateHarbor(row, col), "null", BuildingTypes.NONE, Status.FREE));
                        roadRight.setBuilding2(new Building(row, col + 1, calculateHarbor(row, col + 1), "null", BuildingTypes.NONE, Status.FREE));

                        if(row == 2)
                            colIndex = col;
                        Road roadDown = new Road(row, col, row + 1, colIndex, "null", Status.FREE);
                        roadDown.setBuilding1(new Building(row, col, calculateHarbor(row, col), "null", BuildingTypes.NONE, Status.FREE));
                        roadDown.setBuilding2(new Building(row, colIndex, calculateHarbor(row + 1, colIndex), "null", BuildingTypes.NONE, Status.FREE));

                        if (col != maxColForRow(row)){
                            roads.add(roadRight);
                        }
                        roads.add(roadDown);
                    } else {
                        Road roadRight = new Road(row, col, row, col + 1, "null", Status.FREE);
                        roadRight.setBuilding1(new Building(row, col, calculateHarbor(row, col), "null", BuildingTypes.NONE, Status.FREE));
                        roadRight.setBuilding2(new Building(row, col + 1, calculateHarbor(row, col + 1), "null", BuildingTypes.NONE, Status.FREE));

                        roads.add(roadRight);
                    }
                } else {
                    if (col % 2 != 0) {
                        Road roadRight = new Road(row, col, row, col + 1, "null", Status.FREE);
                        roadRight.setBuilding1(new Building(row, col, calculateHarbor(row, col), "null", BuildingTypes.NONE, Status.FREE));
                        roadRight.setBuilding2(new Building(row, col + 1, calculateHarbor(row, col + 1), "null", BuildingTypes.NONE, Status.FREE));

                        Road roadDown = new Road(row, col, row + 1, col - 1, "null", Status.FREE);
                        roadDown.setBuilding1(new Building(row, col, calculateHarbor(row, col), "null", BuildingTypes.NONE, Status.FREE));
                        roadDown.setBuilding2(new Building(row, col + 1, calculateHarbor(row + 1, col + 1), "null", BuildingTypes.NONE, Status.FREE));

                        roads.add(roadRight);
                        if(row != 5)
                            roads.add(roadDown);
                    } else {
                        Road roadRight = new Road(row, col, row, col + 1, "null", Status.FREE);
                        roadRight.setBuilding1(new Building(row, col, calculateHarbor(row, col), "null", BuildingTypes.NONE, Status.FREE));
                        roadRight.setBuilding2(new Building(row, col + 1, calculateHarbor(row, col + 1), "null", BuildingTypes.NONE, Status.FREE));

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

    public List<Road> getAvailableRoadsForPlayer(String playerName){
        List<Building> playersBuildings = buildings.stream().filter(b -> b.getOwner().equals(playerName)).collect(Collectors.toList());
        List<Road> all = new ArrayList<>();
        for (Building building: playersBuildings) {
            all.addAll(getRoadsForBuilding(building));
        }
        return all;
    }

    public List<Building> getAvailableBuildingSpotsForPlayer(String playerName){
        List<Building> retVal = new ArrayList<>();
        List<Road> roadsForPlayer = roads.stream().filter(r -> r.getOwner().equals(playerName)).collect(Collectors.toList());
        for(Road r: roadsForPlayer){
            if(r.getBuilding1().getStatus() == Status.FREE)
                retVal.add(r.getBuilding1());
            else if(r.getBuilding2().getStatus() == Status.FREE)
                retVal.add(r.getBuilding2());
        }
        return retVal;
    }

    public List<Building> getAvailableHotelSpotsForPlayer(String playerName){
        return buildings
                .stream()
                .filter(b -> b.getOwner().equals(playerName) && b.getType() == BuildingTypes.HOUSE)
                .collect(Collectors.toList());
    }

    public List<Road> getAvailableRoadSpotsForPlayer(String playerName){
        List<Road> retVal = new ArrayList<>();
        //VRATI SVE PUTEVE KOJI SU STATUSA FREE I KOJI SE NADOVEZUJU NA NEKI OD PUTEVA KOJE SADRZI KORISNIK
        List<Road> roadsForPlayer = roads.stream().filter(r -> r.getOwner().equals(playerName)).collect(Collectors.toList());
        for(Road rp: roadsForPlayer){
            for(Road r: roads){
                if(r.continues(rp)){
                    retVal.add(r);
                }
            }
        }
        return retVal;
    }

    public List<Building> getTwoPlaceApart(BuildingDto b){
        List<Building> retVal = new ArrayList<>();
        for(Building building: buildings){
            if(building.getRow() == b.getRow()){
                if(building.getColumn() == b.getCol() - 2 || building.getColumn() == b.getCol() + 2){
                    retVal.add(building);
                }
            }
        }
        return retVal;
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
        System.out.println("ZAUZIMAMO");
        for (Building b: buildings){
            if(b.equalsDto(building)){
                for(Road r: roads){
                    if(r.getBuilding1().equals(b)) {
                        Building bld = r.getBuilding1();
                        bld.setStatus(Status.TAKEN);
                        r.setBuilding1(bld);
                    }
                    else if(r.getBuilding2().equals(b)){
                        Building bld = r.getBuilding2();
                        bld.setStatus(Status.TAKEN);
                        r.setBuilding2(bld);
                    }
                }
                if(b.getStatus() != Status.FREE){
                    throw new PositionNotAvailableException();
                }
                b.setStatus(Status.TAKEN);
                b.setOwner(building.getPlayerName());

                InputState.getInstance().updateChances(building.getPlayerName(), b);

                return b;
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
            if(b.getRow() == row && b.getColumn() == col){
                b.setStatus(Status.BLOCKED);
                for(Road r: roads){
                    if(r.getBuilding1().equals(b)){
                        Building bld = r.getBuilding1();
                        bld.setStatus(Status.BLOCKED);
                        r.setBuilding1(bld);
                    }
                    else if(r.getBuilding2().equals(b)){
                        Building bld = r.getBuilding2();
                        bld.setStatus(Status.BLOCKED);
                        r.setBuilding2(bld);
                    }
                }
            }
        }
    }

    public void initChances(ChanceInitDto chances){
        for (Building b: buildings) {
            for(int i = 0; i < chances.getBuilding().size(); i++){
                if(b.equalsDto(chances.getBuilding().get(i))){
                    b.setChanceList(chances.getChances().get(i));
                    for(Road r: roads){
                        if(r.getBuilding1().equals(b)){
                            r.getBuilding1().setChanceList(chances.getChances().get(i));
                        }
                        else if(r.getBuilding2().equals(b)){
                            r.getBuilding2().setChanceList(chances.getChances().get(i));
                        }
                    }
                }
            }
        }
    }

    public Road getRoadTowardsBuilding(BuildingDto start, Building end){
        System.out.println("OD");
        System.out.println(start);
        System.out.println("DO");
        System.out.println(end);
        List<Building> connections = new ArrayList<>();
        for (Road r : roads){
            if(r.getBuilding1().equals(end))
                connections.add(r.getBuilding2());
            else if(r.getBuilding2().equals(end))
                connections.add(r.getBuilding1());
        }
        for (Road r: roads) {
            if(connections.contains(r.getBuilding1())){
                if(r.getBuilding2().equalsDto(start))
                    return r;
            }
            else if(connections.contains(r.getBuilding2())){
                if (r.getBuilding1().equalsDto(start))
                    return r;
            }
        }
        return null;
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
