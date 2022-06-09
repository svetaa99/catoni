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
    public Map<Integer, String> mostKnights; //ZAR NE TREBA OBRNUTO
    public String playerOnTurn;

    public int pointsForWin;

    private GlobalState()
    {
        playerNames = new ArrayList<>();
        longestRoad = new HashMap<>();
        longestRoad.put(5, "player1");
        mostKnights = new HashMap<>();
        mostKnights.put(3, "player2");
        playerOnTurn = "";
        pointsForWin = 10;
    }

    public static GlobalState getInstance()
    {
        if (instance == null)
            instance = new GlobalState();

        return instance;
    }

    public void setPointsForWin(int pointsForWin) {
        this.pointsForWin = pointsForWin;
    }

    @Override
    public String toString() {
        return "GlobalState{" +
                "playerNames=" + playerNames +
                ", longestRoad=" + longestRoad +
                ", mostKnights=" + mostKnights +
                ", playerOnTurn='" + playerOnTurn + '\'' +
                ", pointsForWin=" + pointsForWin +
                '}';
    }
}
