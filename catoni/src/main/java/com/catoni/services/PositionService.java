package com.catoni.services;

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
    private final KieSession kieSession;

    @Autowired
    public PositionService(KieContainer kieContainer){
        System.out.println("PositionService Initialising a new example session.");
        this.kieContainer = kieContainer;
        this.kieSession= kieContainer.newKieSession();
    }

    public StartSelectionDto getHouseAndRoadPosition(Position position){
//        System.out.println(position);
        StartSelectionDto dto = new StartSelectionDto();
        BuildingDto bDto = new BuildingDto();
        RoadDto rDto = new RoadDto();
//        System.out.println("BEFORE");
//        System.out.println(dto);

        kieSession.insert(position);
        kieSession.insert(bDto);
        kieSession.insert(rDto);
        kieSession.fireAllRules();

        dto.setBuilding(bDto);
        dto.setRoad(rDto);
//        System.out.println("AFTER");
//        System.out.println(dto);
        return dto;
    }
}
