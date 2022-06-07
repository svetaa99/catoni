package com.catoni.services;

import com.catoni.models.*;
import com.catoni.models.enums.MoveTypes;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MoveService {

    private final KieContainer kieContainer;
    private KieSession kieSession;

    private Move move = new Move();

    FactHandle handle;
    @Autowired
    public MoveService(KieContainer kieContainer) {
        System.out.println("MoveService Initialising a new example session.");
        this.kieContainer = kieContainer;
        this.kieSession = kieContainer.newKieSession();
    }

    public Move getMove(InputState inputState) {
        move = new Move();
//        KieSession kieSession = kieContainer.newKieSession();
        handle = kieSession.insert(move);
        kieSession.insert(inputState);
        //kieSession.insert(move);
        kieSession.insert(GlobalState.getInstance());
        kieSession.fireAllRules();
        if(move.moveList.get(move.moveList.size() - 1) == MoveTypes.END_TURN){
            System.out.println("DISPOSE");
            kieSession.dispose();
            kieSession = kieContainer.newKieSession();
        }
        return move;
    }

    public Move getMove(InputState inputState, Trade trade){
        move.trade = trade;
        System.out.println(move);
        //kieSession = kieContainer.newKieSession();
        //kieSession.insert(inputState);
        kieSession.update(handle, move);
        //kieSession.insert(GlobalState.getInstance());
        kieSession.fireAllRules();

        if(move.moveList.get(move.moveList.size() - 1) == MoveTypes.END_TURN){
            System.out.println("DISPOSE");
            kieSession.dispose();
            kieSession = kieContainer.newKieSession();
        }
        return move;
    }
}
