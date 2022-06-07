package com.catoni.services;

import com.catoni.models.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoveService {

    private final KieContainer kieContainer;
    private final KieSession kieSession;

    @Autowired
    public MoveService(KieContainer kieContainer) {
        System.out.println("MoveService Initialising a new example session.");
        this.kieContainer = kieContainer;
        this.kieSession = kieContainer.newKieSession();
    }

    public Move getMove(InputState inputState) {
        Move move = new Move();
//        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(inputState);
        kieSession.insert(move);
        kieSession.insert(GlobalState.getInstance());
        kieSession.fireAllRules();
//        kieSession.dispose();
        return move;
    }
}
