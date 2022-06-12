package com.catoni.controllers;

import com.catoni.models.*;
import com.catoni.services.MoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/moves")
public class MoveController {

    @Autowired
    private MoveService moveService;

    private final InputState inputState = InputState.getInstance();

    public MoveController() {};

    @GetMapping(value = "/", produces = "application/json")
    public Move getMoves() {
        System.out.println("Current input state: " + inputState);
        Move move = moveService.getMove(inputState);
        System.out.println("Move to play -> " + move.moveList);
        return move;
    }

    @PostMapping(value = "/answer", produces = "application/json")
    public ResponseEntity<Move> answerTrade(@RequestBody Trade trade){
        System.out.println("Answer to trade: " + trade);
        Move move = moveService.getMove(inputState, trade);
        return new ResponseEntity<>(move, HttpStatus.CREATED);
    }

    //TESTING METHODS
    @GetMapping(value="/check-position")
    public void printPosition() {
        System.out.println(inputState.getPosition());
    }

    @GetMapping(value="print-is")
    public void print(){
        System.out.println(inputState);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> badRequest(Exception e){
        return new ResponseEntity<>(new Error("Bad request"), HttpStatus.BAD_REQUEST);
    }
}
