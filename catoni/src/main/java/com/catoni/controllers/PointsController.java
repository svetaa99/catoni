package com.catoni.controllers;

import com.catoni.models.GlobalState;
import com.catoni.services.PointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/points")
public class PointsController {

    @Autowired
    private PointsService pointsService;

    public PointsController() {}

    @GetMapping("/change-points-for-win/{pointsForWin}")
    public ResponseEntity<Integer> changePoints(@PathVariable int pointsForWin) {
        try {
            return new ResponseEntity<>(pointsService.changePointsForWin(pointsForWin), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/print-gs")
    public void printGlobalState() {
        try {
            System.out.println(GlobalState.getInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
