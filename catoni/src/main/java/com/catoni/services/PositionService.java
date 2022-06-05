package com.catoni.services;

import com.catoni.models.Position;
import com.catoni.models.dto.BuildingDto;
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

    public BuildingDto getHousePosition(Position position){
        System.out.println("POZICIJA");
        System.out.println(position);
        BuildingDto dto = new BuildingDto();
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(position);
        kieSession.insert(dto);
        kieSession.fireAllRules();
        kieSession.dispose();
        return dto;
    }
}
