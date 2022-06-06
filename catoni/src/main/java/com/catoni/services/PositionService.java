package com.catoni.services;

import com.catoni.models.GlobalState;
import com.catoni.models.InputState;
import com.catoni.models.Position;
import com.catoni.models.dto.BuildingDto;
import com.catoni.models.dto.RoadDto;
import com.catoni.models.dto.StartSelectionDto;
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
}
