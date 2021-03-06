package com.catoni.controllers;

import com.catoni.exceptions.PositionNotAvailableException;
import com.catoni.models.*;
import com.catoni.models.dto.*;
import com.catoni.models.enums.BuildingTypes;
import com.catoni.models.enums.CrazyTypes;
import com.catoni.models.enums.ResourceTypes;
import com.catoni.models.enums.Status;
import com.catoni.services.PositionService;
import org.apache.commons.lang3.NotImplementedException;
import org.kie.api.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @PostMapping(value="add-resources-for-positions", consumes = "application/json", produces = "application/json")
    public ResponseEntity<InputState> addResourcesForPositions(@RequestBody List<ResourcesPositionsDto> resourcesPositions){
        for(ResourcesPositionsDto el: resourcesPositions){
           for(BuildingDto bDto: el.getPositions()){
               for(Building b: inputState.getPosition().getBuildings()){
                   if(b.equalsDto(bDto) && b.getStatus() == Status.TAKEN){
                       String owner = b.getOwner();
                       if(owner.equals("bot")){
                           inputState.addResource(el.getResource()); // 2 ako ima hotel
                       }
                       inputState.getPlayerStates().get(owner).addResource(el.getResource());
                   }
               }
           }
        }
        return new ResponseEntity<>(inputState, HttpStatus.OK);
    }

    @PostMapping(value="add-resources/{playerName}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<InputState> addResourcesToPlayer(@PathVariable String playerName, @RequestBody List<ResourceTypes> resources){
        if(playerName.equals("bot")){
            inputState.addResources(resources);
        }
        State previousState = inputState.getPlayerStates().get(playerName);
        previousState.addResources(resources);
        //inputState.getPlayerStates().replace(playerName, previousState);

        return new ResponseEntity<>(inputState, HttpStatus.OK);
    }

    @GetMapping(value="play-knight/{currentPlayerName}/{stealFrom}")
    public ResponseEntity<ResourcesAndCraziesListsDTO> stealResoruce(@PathVariable String currentPlayerName, @PathVariable String stealFrom) {
        List<ResourceTypes> stealFromResources = inputState.getPlayerStates().get(stealFrom).getResources();
        Random rand = new Random();
        int random = rand.nextInt(stealFromResources.size());
        ResourceTypes stolenResource = stealFromResources.remove(random);
        inputState.getPlayerStates().get(currentPlayerName).getResources().add(stolenResource);
        inputState.getPlayerStates().get(currentPlayerName).getCraziesList().remove(CrazyTypes.KNIGHT);

        return new ResponseEntity<>(new ResourcesAndCraziesListsDTO(inputState.getPlayerStates().get(currentPlayerName).getResources(),
                inputState.getPlayerStates().get(currentPlayerName).getCraziesList()), HttpStatus.OK);
    }

    @GetMapping(value = "play-monopoly/{currentPlayerName}/{resource}")
    public ResponseEntity<ResourcesAndCraziesListsDTO> playMonopoly(@PathVariable String currentPlayerName, @PathVariable ResourceTypes resource) {
        for (Map.Entry<String, State> entry : inputState.getPlayerStates().entrySet()) {
            if (entry.getKey().equals(currentPlayerName)) {
                continue;
            }

            List<ResourceTypes> resourceTypes = entry.getValue().getResources();
            if (resourceTypes.contains(resource)) {
                int numberOfResources = Collections.frequency(resourceTypes, resource);
                resourceTypes.removeAll(Collections.singleton(resource));
                for (int i = 0; i < numberOfResources; i++) {
                    inputState.getPlayerStates().get(currentPlayerName).getResources().add(resource);
                }
            }
        }

        inputState.getPlayerStates().get(currentPlayerName).getCraziesList().remove(CrazyTypes.MONOPOLY);

        return new ResponseEntity<>(new ResourcesAndCraziesListsDTO(inputState.getPlayerStates().get(currentPlayerName).getResources(),
                inputState.getPlayerStates().get(currentPlayerName).getCraziesList()), HttpStatus.OK);
    }

    @PostMapping(value = "play-yop/{currentPlayerName}")
    public ResponseEntity<ResourcesAndCraziesListsDTO> playYop(@PathVariable String currentPlayerName, @RequestBody List<ResourceTypes> resources) {
        inputState.getPlayerStates().get(currentPlayerName).getResources().addAll(resources);
        inputState.getPlayerStates().get(currentPlayerName).getCraziesList().remove(CrazyTypes.YEAR_OF_PLENTY);

        return new ResponseEntity<>(new ResourcesAndCraziesListsDTO(inputState.getPlayerStates().get(currentPlayerName).getResources(),
                inputState.getPlayerStates().get(currentPlayerName).getCraziesList()), HttpStatus.OK);
    }

    @GetMapping(value = "play-road-builder/{currentPlayerName}")
    public ResponseEntity<ResourcesAndCraziesListsDTO> playRB(@PathVariable String currentPlayerName) {
        inputState.getPlayerStates().get(currentPlayerName).getResources().addAll(Arrays.asList(ResourceTypes.CLAY, ResourceTypes.CLAY,
                ResourceTypes.WOOD, ResourceTypes.WOOD));
        inputState.getPlayerStates().get(currentPlayerName).getCraziesList().remove(CrazyTypes.ROAD_BUILDING);

        return new ResponseEntity<>(new ResourcesAndCraziesListsDTO(inputState.getPlayerStates().get(currentPlayerName).getResources(),
                inputState.getPlayerStates().get(currentPlayerName).getCraziesList()), HttpStatus.OK);
    }

    @GetMapping(value="get-resources/{playerName}", produces = "application/json")
    public ResponseEntity<List<ResourceTypes>> getResourcesToPlayer(@PathVariable String playerName){
        return new ResponseEntity<>(inputState.getPlayerStates().get(playerName).getResources(), HttpStatus.OK);
    }

    @PostMapping(value="set-resources/{playerName}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<ResourceTypes>> setResourcesToPlayer(@PathVariable String playerName, @RequestBody List<ResourceTypes> resources){
        inputState.getPlayerStates().get(playerName).setResources(resources);
        List<ResourceTypes> resourceTypes = inputState.getPlayerStates().get(playerName).getResources();
        return new ResponseEntity<>(resourceTypes, HttpStatus.OK);
    }

    @PostMapping(value = "set-crazies/{playerName}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<InputState> addCraziesToPlayer(@PathVariable String playerName, @RequestBody List<CrazyTypes> crazies){
        State previousState = inputState.getPlayerStates().get(playerName);
        previousState.setCraziesList(crazies);
        inputState.getPlayerStates().replace(playerName, previousState);

        return new ResponseEntity<>(inputState, HttpStatus.OK);
    }

    @GetMapping(value = "add-crazy/{playerName}/{crazyType}", produces = "application/json")
    public ResponseEntity<InputState> addCrazyToPlayer(@PathVariable String playerName, @PathVariable CrazyTypes crazyType){
        State previousState = inputState.getPlayerStates().get(playerName);
        previousState.getCraziesList().add(crazyType);
        inputState.getPlayerStates().replace(playerName, previousState);

        return new ResponseEntity<>(inputState, HttpStatus.OK);
    }

    @GetMapping(value = "get-crazies/{playerName}", produces = "application/json")
    public ResponseEntity<List<CrazyTypes>> addCrazyToPlayer(@PathVariable String playerName){
        return new ResponseEntity<>(inputState.getPlayerStates().get(playerName).getCraziesList(), HttpStatus.OK);
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
    public ResponseEntity<RoadDto> addRoad(@RequestBody RoadDto road){
        position.addRoad(road);
//        System.out.println(position);
        return new ResponseEntity<>(road, HttpStatus.OK);
    }

    @PostMapping(value="building", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Building> addBuilding(@RequestBody BuildingDto building){
        Building b = position.addBuilding(building);
        service.updateInputStateAfterBuildingHouse(b);
//        System.out.println(position);
        return new ResponseEntity<>(b, HttpStatus.OK);
    }

    @GetMapping(value="starting-house", produces = "application/json")
    public ResponseEntity<StartSelectionDto> getStartingHouse(){
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
//        System.out.println(position);
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
        inputState.updateState(is);
        return new ResponseEntity<>(inputState, HttpStatus.OK);
    }

    @GetMapping(value="print-is")
    public void print(){
        System.out.println(inputState);
    }

    @GetMapping(value="print-position")
    public void printPosition(){
        System.out.println(position);
    }

    @ExceptionHandler(PositionNotAvailableException.class)
    public ResponseEntity<Error> positonNotAvailable(PositionNotAvailableException e){
        return new ResponseEntity<>(new Error("Position is taken or blocked"), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> badRequest(Exception e){
        return new ResponseEntity<>(new Error("Bad request"), HttpStatus.BAD_REQUEST);
    }
}
