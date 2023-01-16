package net.gonzq.uhcmeetup.VoteSystem;


import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.Managers.ScenarioManager;
import net.gonzq.uhcmeetup.players.GamePlayer;
import net.gonzq.uhcmeetup.Scenarios.Scenario;
import net.gonzq.uhcmeetup.Utils.Utils;
import org.bukkit.Bukkit;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoteScenarios {
    public HashMap<Scenario, Integer> scensToVote;
    boolean canVote;
    Main pl = Main.pl;

    public VoteScenarios() {
        scensToVote = new HashMap<>();
        pl.config.getConfig().getStringList("vote-system.scenarios-to-vote").forEach(s -> {
            ScenarioManager scen = ScenarioManager.getInstance();
            if (scen.getScenario(s) != null) {
                scensToVote.put(scen.getScenario(s), 0);
            } else {
                pl.getLogger().warning(s + " isn't a scenario. Check the config.yml");
            }
        });
        canVote = false;
    }


    public void setVote(GamePlayer p, Scenario s) {
        if (p.isVotedScen()) {
            if (p.isOnline()) p.getPlayer().sendMessage(Utils.chat(pl.prefix + "&cYou already voted a Scenario."));
        } else {
            scensToVote.put(s, scensToVote.getOrDefault(s,0) + 1);
            p.setVotedScen(true);
            if (p.isOnline()) p.getPlayer().sendMessage(Utils.chat(pl.lang.getConfig().getString("vote-scen")).replace("%scenario%", s.getName()));
        }
    }

    public boolean canVote() {
        return canVote;
    }
    public void setCanVote(boolean b) {
        canVote = b;
    }

    public int getVotes(Scenario s) {
        return scensToVote.getOrDefault(s,0);
    }

    public void endVotes() {
        List<Map.Entry<Scenario, Integer>> list = scensToVote.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).toList();
        if (list.get(0).getValue() > 0) {
            Scenario s = list.get(0).getKey();
            Bukkit.broadcastMessage(Utils.chat(pl.prefix + pl.lang.getConfig().getString("vote-end")).replace("%scenario%", s.getName()).replace("%votes%", String.valueOf(list.get(0).getValue())));
            s.enable();
        }
        setCanVote(false);
    }
}
