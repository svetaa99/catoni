package com.catoni.controllers;

import com.catoni.models.Building;
import com.catoni.models.Position;
import com.catoni.models.Road;
import com.catoni.models.dto.BuildingDto;
import com.catoni.models.dto.RoadDto;
import com.catoni.services.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("position")
public class PositionController {

    @Autowired
    private PositionService service;

    private Position position = new Position();

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
}
