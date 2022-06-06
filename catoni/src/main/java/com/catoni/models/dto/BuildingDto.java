package com.catoni.models.dto;

import com.catoni.models.enums.BuildingTypes;

public class BuildingDto {
    public int row;
    public int col;
    public String playerName;
    public BuildingTypes type;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public BuildingTypes getType() {
        return type;
    }

    public void setType(BuildingTypes type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "BuildingDto{" +
                "row=" + row +
                ", col=" + col +
                ", playerName='" + playerName + '\'' +
                ", type=" + type +
                '}';
    }
}
