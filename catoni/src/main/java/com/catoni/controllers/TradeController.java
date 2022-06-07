package com.catoni.controllers;

import com.catoni.models.InputState;
import com.catoni.models.Move;
import com.catoni.models.Trade;
import com.catoni.models.TradeOffer;
import com.catoni.services.TradeService;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("trade")
public class TradeController {

    @Autowired
    private TradeService service;

    @PostMapping(value="accept", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Move> acceptTrade(@RequestBody Trade acceptedTrade){
        throw new NotImplementedException();
    }

    @PostMapping(value = "decline", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Move> declineTrade(@RequestBody Trade declinedTrade){
        throw new NotImplementedException();
    }

    @GetMapping(value = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<TradeOffer> offerTrade(@RequestBody InputState inputState){
        return new ResponseEntity<>(service.getTradeOffer(inputState), HttpStatus.OK);
    }
}
