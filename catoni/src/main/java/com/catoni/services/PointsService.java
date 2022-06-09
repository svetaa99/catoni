package com.catoni.services;

import com.catoni.models.PointsForWin;
import com.catoni.utils.RuleBasedSystemUtil;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class PointsService {

    private final KieContainer kieContainer;

    private KieSession kieSession;

    private final static String PATH_TO_TEMPLATE = "../drools-spring-kjar/src/main/resources/sbnz/integracija/end-game-rules.drt";

    private final static String PATH_TO_DRL = "../drools-spring-kjar/src/main/resources/sbnz/integracija/end-game-rules.drl";

    @Autowired
    public PointsService(KieContainer kieContainer) {
        System.out.println("PointsService Initialising a new example session.");
        this.kieContainer = kieContainer;
        this.kieSession = kieContainer.newKieSession();
    }

    public int changePointsForWin(int pointsForWin) throws Exception {
        if (pointsForWin < 1) {
            throw new Exception("Invalid params!");
        }

        List<PointsForWin> data = new ArrayList<>();
        data.add(new PointsForWin(pointsForWin));

        PrintWriter writer = new PrintWriter(PATH_TO_DRL);
        writer.print("");
        writer.close();

        InputStream template = new FileInputStream(PATH_TO_TEMPLATE);
        String drl = (new ObjectDataCompiler()).compile(data, template);
        Files.write(Paths.get(PATH_TO_DRL), drl.getBytes(), StandardOpenOption.WRITE);
        RuleBasedSystemUtil.mavenCleanAndInstall();

        return pointsForWin;
    }
}
