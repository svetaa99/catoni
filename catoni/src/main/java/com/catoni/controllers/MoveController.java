package com.catoni.controllers;

import com.catoni.models.InputState;
import com.catoni.models.Item;
import com.catoni.models.Move;
import com.catoni.services.MoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/moves")
public class MoveController {

    @Autowired
    private MoveService moveService;

    public MoveController() {};

    @PostMapping(value = "/", produces = "application/json")
    public Move getMoves(@RequestBody InputState inputState) {

        System.out.println("Current input state: " + inputState);
        Move move = moveService.getMove(inputState);
        System.out.println("Move to play -> " + move.moveList);
        return move;
    }

    @GetMapping(value="/check-position")
    public void printPosition(@RequestBody InputState inputState) {
        System.out.println(inputState.getPosition());
    }

}
