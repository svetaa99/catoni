package com.catoni.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GlobalState {

    private static GlobalState instance = null;

    public List<String> playerNames;
    // Map<numberOfLongestRoads, PlayerName>
    public Map<Integer, String> longestRoad;
    // Map<numberOfMostUsedKnights, PlayerName>
    public Map<Integer, String> mostKnights;
    public String playerOnTurn;

    private GlobalState()
    {
        playerNames = new ArrayList<>();
        longestRoad = new HashMap<>();
        mostKnights = new HashMap<>();
        playerOnTurn = "";
    }

    public static GlobalState getInstance()
    {
        if (instance == null)
            instance = new GlobalState();

        return instance;
    }
}
