package com.catoni.controllers;

import com.catoni.models.*;
import com.catoni.models.dto.*;
import com.catoni.models.enums.BuildingTypes;
import com.catoni.models.enums.CrazyTypes;
import com.catoni.models.enums.ResourceTypes;
import com.catoni.services.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("position")
public class PositionController {

    @Autowired
    private PositionService service;

    private InputState inputState = InputState.getInstance();

    private final Position position = inputState.getPosition();


    //INPUT STATE MANIPULATION
    @GetMapping(value="init-players/{cnt}")
    public ResponseEntity<InputState> initPlayers(@PathVariable int cnt){
        Map<String, State> players = new HashMap<>();
        for(int i = 0; i < cnt; i++){
            players.put("player" + (i + 1), new State());
        }
        players.put("bot", new State());
        inputState.setPlayerStates(players);
        inputState.setResources(new ArrayList<>());
        inputState.setDistanceToHarbor(5); //TO DO: find harbor distance

        return new ResponseEntity<>(inputState, HttpStatus.OK);
    }

    @PostMapping(value="add-resources/{playerName}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<InputState> addResourcesToPlayer(@PathVariable String playerName, @RequestBody List<ResourceTypes> resources){
        if(playerName.equals("bot")){
            inputState.addResources(resources);
        }
        State previousState = inputState.getPlayerStates().get(playerName);
        previousState.addResources(resources);
        inputState.getPlayerStates().replace(playerName, previousState);

        return new ResponseEntity<>(inputState, HttpStatus.OK);
    }

    @PostMapping(value = "set-crazies/{playerName}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<InputState> addCraziesToPlayer(@PathVariable String playerName, @RequestBody List<CrazyTypes> crazies){
        State previousState = inputState.getPlayerStates().get(playerName);
        previousState.setCraziesList(crazies);
        inputState.getPlayerStates().replace(playerName, previousState);

        return new ResponseEntity<>(inputState, HttpStatus.OK);
    }

    @PutMapping(value = "update-resource-chances/{playerName}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<InputState> updateResourceChances(@PathVariable String playerName, @RequestBody List<ResourceChanceDto> chances){
        State previousState = inputState.getPlayerStates().get(playerName);
        for (ResourceChanceDto rc: chances) {
            double prevVal = previousState.getResourceChances().get(rc.getType());

            previousState.getResourceChances().replace(rc.getType(), prevVal + rc.getChance());
        }
        inputState.getPlayerStates().replace(playerName, previousState);

        return new ResponseEntity<>(inputState, HttpStatus.OK);
    }

    //POSITION MANIPULATION
    @PostMapping(value="road", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Road> addRoad(@RequestBody RoadDto road){
        position.addRoad(road);
//        System.out.println(position);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value="building", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Building> addBuilding(@RequestBody BuildingDto building){
        position.addBuilding(building);
        if(building.getType() == BuildingTypes.HOUSE){
            int previousHouses = inputState.getPlayerStates().get(building.getPlayerName()).getNumberOfHouses();
            inputState.getPlayerStates().get(building.getPlayerName()).setNumberOfHouses(previousHouses+1);
        }
        if(building.getType() == BuildingTypes.HOTEL){
            int previousHouses = inputState.getPlayerStates().get(building.getPlayerName()).getNumberOfHouses();
            int previousHotels = inputState.getPlayerStates().get(building.getPlayerName()).getNumberOfHotels();
            inputState.getPlayerStates().get(building.getPlayerName()).setNumberOfHouses(previousHouses-1);
            inputState.getPlayerStates().get(building.getPlayerName()).setNumberOfHotels(previousHotels+1);
        }
//        System.out.println(position);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value="starting-house", produces = "application/json", consumes = "application/json")
    public ResponseEntity<StartSelectionDto> getStartingHouse(@RequestBody InputState inputState){
//        System.out.println("PRE");
//
//        System.out.println(position);
        StartSelectionDto dto = service.getHouseAndRoadPosition(inputState);
//        position.addBuilding(dto.getBuilding());
//        position.addRoad(dto.getRoad());
//        System.out.println("GOTOVO");
//        System.out.println(position);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping(value="init-chances", produces = "application/json", consumes="application/json")
    public ResponseEntity<Position> initChances(@RequestBody ChanceInitDto chances){
        position.initChances(chances);
        System.out.println(position);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //TESTING METHODS
    @GetMapping(value = "available-player-building-spots", produces = "application/json")
    public ResponseEntity<List<Building>> avaiableForPlayer(){
        List<Building> retVal = position.getAvailableBuildingSpotsForPlayer("bot");

        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    @GetMapping(value = "available-player-hotel-spots", produces = "application/json")
    public ResponseEntity<List<Building>> availableHotelsForPlayer(){
        return new ResponseEntity<>(position.getAvailableHotelSpotsForPlayer("bot"), HttpStatus.OK);
    }

    @GetMapping(value = "available-player-road-spots", produces = "application/json")
    public ResponseEntity<List<Road>> availableRoadsForPlayer(){
        return new ResponseEntity<>(position.getAvailableRoadSpotsForPlayer("bot"), HttpStatus.OK);
    }

    @PostMapping(value="set-is", consumes = "application/json")
    public ResponseEntity<InputState> setInputState(@RequestBody InputState is){
        inputState = is;
        return new ResponseEntity<>(inputState, HttpStatus.OK);
    }
}
