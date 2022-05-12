package com.catoni.services;

import com.catoni.models.Item;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    private final KieContainer kieContainer;

    @Autowired
    public ItemService(KieContainer kieContainer) {
        System.out.println("Initialising a new example session.");
        this.kieContainer = kieContainer;
    }

    public Item getClassifiedItem(Item i) {
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(i);
        kieSession.fireAllRules();
        kieSession.dispose();
        return i;
    }
}
