package com.catoni.services;

import com.catoni.models.Building;
import com.catoni.models.GlobalState;
import com.catoni.models.InputState;
import com.catoni.models.Position;
import com.catoni.models.dto.BuildingDto;
import com.catoni.models.dto.RoadDto;
import com.catoni.models.dto.StartSelectionDto;
import com.catoni.models.enums.BuildingTypes;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PositionService {

    private final KieContainer kieContainer;

    @Autowired
    public PositionService(KieContainer kieContainer){
        System.out.println("PositionService Initialising a new example session.");
        this.kieContainer = kieContainer;
    }

    public StartSelectionDto getHouseAndRoadPosition(InputState inputState){

        StartSelectionDto dto = new StartSelectionDto();
        BuildingDto bDto = new BuildingDto();
        RoadDto rDto = new RoadDto();

        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(inputState);
        kieSession.insert(bDto);
        kieSession.insert(rDto);
        kieSession.insert(GlobalState.getInstance());
        kieSession.fireAllRules();
        kieSession.dispose();

        dto.setBuilding(bDto);
        dto.setRoad(rDto);

        return dto;
    }

    public void updateInputStateAfterBuildingHouse(Building building){
        InputState inputState = InputState.getInstance();
        if(building.getType() == BuildingTypes.HOUSE){
            int previousHouses = inputState.getPlayerStates().get(building.getOwner()).getNumberOfHouses();
            inputState.getPlayerStates().get(building.getOwner()).setNumberOfHouses(previousHouses+1);
        }
        if(building.getType() == BuildingTypes.HOTEL){
            int previousHouses = inputState.getPlayerStates().get(building.getOwner()).getNumberOfHouses();
            int previousHotels = inputState.getPlayerStates().get(building.getOwner()).getNumberOfHotels();
            inputState.getPlayerStates().get(building.getOwner()).setNumberOfHouses(previousHouses-1);
            inputState.getPlayerStates().get(building.getOwner()).setNumberOfHotels(previousHotels+1);
        }
    }
}
