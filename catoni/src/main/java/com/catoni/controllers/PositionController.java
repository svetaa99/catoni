package com.catoni.controllers;

import com.catoni.models.Building;
import com.catoni.models.InputState;
import com.catoni.models.Position;
import com.catoni.models.Road;
import com.catoni.models.dto.BuildingDto;
import com.catoni.models.dto.ChanceInitDto;
import com.catoni.models.dto.RoadDto;
import com.catoni.models.dto.StartSelectionDto;
import com.catoni.services.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("position")
public class PositionController {

    @Autowired
    private PositionService service;

    private Position position = Position.getInstance();

    @PostMapping(value="road", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Road> addRoad(@RequestBody RoadDto road){
        position.addRoad(road);
        System.out.println(position);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value="building", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Building> addBuilding(@RequestBody BuildingDto building){
        position.addBuilding(building);
        System.out.println(position);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value="starting-house", produces = "application/json")
    public ResponseEntity<StartSelectionDto> getStartingHouse(){
//        System.out.println("PRE");
//
//        System.out.println(position);
        StartSelectionDto dto = service.getHouseAndRoadPosition(position);
        position.addBuilding(dto.getBuilding());
        position.addRoad(dto.getRoad());
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

    //TESTING METHOD
    @GetMapping(value = "available-player-building-spots", produces = "application/json")
    public ResponseEntity<List<Building>> avaiableForPlayer(){
        List<Building> retVal = position.getAvailableBuildingSpotsForPlayer("bot");

        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }
}
