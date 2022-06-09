package com.catoni.controllers;

import com.catoni.models.GameStats;
import com.catoni.services.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/query")
public class QueryController {

    private final QueryService queryService;

    @Autowired
    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping(value = "/get-result", produces = "application/json")
    public ResponseEntity<Map<String, GameStats>> getResult() {
        Map<String, GameStats> gameStats = queryService.getResult();
        return new ResponseEntity<>(gameStats, HttpStatus.OK);
    }
}
