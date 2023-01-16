package net.gonzq.uhcmeetup.Managers;

import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.Scenarios.Scenario;
import net.gonzq.uhcmeetup.Scenarios.Types.*;

import java.util.HashSet;
import java.util.List;

public class ScenarioManager {
    private HashSet<Scenario> scenarios = new HashSet<>();
    private static ScenarioManager manager = new ScenarioManager();

    public static ScenarioManager getInstance() {
        return manager;
    }

    public void setup() {
        scenarios.add(new AbsortionLess());
        scenarios.add(new BloodHunter());
        scenarios.add(new BowLess());
        scenarios.add(new CobwebLess());
        scenarios.add(new FastGetaway());
        scenarios.add(new GappleRoulette());
        scenarios.add(new GoldenRetriever());
        scenarios.add(new GraveRobbers());
        scenarios.add(new HeavyPockets());
        scenarios.add(new KillEffect());
        scenarios.add(new NoClean(Main.pl));
        scenarios.add(new TimeBomb());
        scenarios.add(new WebCage());
    }

    public Scenario getScenario(String name) {
        for (Scenario s : scenarios) {
            if (name.equalsIgnoreCase(s.getName())) return s;
        }
        return null;
    }

    public List<Scenario> getScenarios() {
        return scenarios.stream().toList();
    }

    public List<Scenario> getEnabledScenarios() {
        return scenarios.stream().filter(Scenario::isEnabled).toList();
    }

    public List<Scenario> getDisabledScenarios() {
        return scenarios.stream().filter(s -> !s.isEnabled()).toList();
    }
}
