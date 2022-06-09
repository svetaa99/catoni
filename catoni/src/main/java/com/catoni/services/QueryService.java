package com.catoni.services;

import com.catoni.models.GameStats;
import com.catoni.models.InputState;
import org.apache.tools.ant.taskdefs.Input;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class QueryService {

    private final KieContainer kieContainer;

    @Autowired
    public QueryService(KieContainer kieContainer) {
        System.out.println("QueryService Initialising a new example session.");
        this.kieContainer = kieContainer;
    }

    public Map<String, GameStats> getResult() {
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert("player1");
        kieSession.insert("player2");
        kieSession.insert("player3");
        kieSession.insert("bot");
        kieSession.insert(InputState.getInstance());


        System.out.println(InputState.getInstance());

        Map<String, GameStats> endGameStats = new HashMap<>();

        QueryResults results = kieSession.getQueryResults("Average houses for input state", InputState.getInstance());
        for(QueryResultsRow result : results) {
            String playerName = (String) result.get("$playerName");
            int houses = (int) result.get("$houses");
            int hotels = (int) result.get("$hotels");
            int roads = (int) result.get("$roads");
            endGameStats.put(playerName, new GameStats(houses, hotels, roads));

            System.out.println(result.get("$playerName"));
            System.out.println(result.get("$houses"));
            System.out.println(result.get("$hotels"));
            System.out.println(result.get("$roads"));
            System.out.println("----------------");
        }

        kieSession.insert("average");
        kieSession.fireAllRules();
        kieSession.dispose();

        System.out.println("END GAME STATISTICS:");
        System.out.println(endGameStats);

        return endGameStats;
    }
}
