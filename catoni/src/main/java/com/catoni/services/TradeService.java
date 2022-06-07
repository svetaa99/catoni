package com.catoni.services;

import com.catoni.models.GlobalState;
import com.catoni.models.InputState;
import com.catoni.models.Trade;
import com.catoni.models.TradeOffer;
import org.kie.api.internal.utils.KieService;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeService {
    private final KieContainer kieContainer;
    private final KieSession kieSession;

    private int tradeCnt = 0;

    @Autowired
    public TradeService(KieContainer kieContainer){
        this.kieContainer = kieContainer;
        this.kieSession = kieContainer.newKieSession();
    }

    public TradeOffer getTradeOffer(InputState inputState){
        TradeOffer offer = new TradeOffer();
        System.out.println(offer);
        kieSession.insert(inputState);
        kieSession.insert(offer);
        kieSession.insert(GlobalState.getInstance());
        kieSession.fireAllRules();
//        kieSession.dispose();

        return offer;
    }
}

