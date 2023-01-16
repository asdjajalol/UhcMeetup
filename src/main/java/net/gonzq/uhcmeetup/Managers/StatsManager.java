package net.gonzq.uhcmeetup.Managers;

import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.players.PlayerFiles;
import net.gonzq.uhcmeetup.Utils.Utils;
import org.bukkit.entity.Player;

import java.util.*;

public enum StatsManager {
    KILLS("kills"),
    DEATHS("deaths"),
    WINS("wins"),
    GAPPS("gapps-eaten"),
    PLAYED("games-played");


    String stat;
    StatsManager(String stat) {
        this.stat = stat;
    }

    public String getName() {
        return stat;
    }

    public int getValue(Player p) {
        return Main.playerManager.getUhcPlayer(p).getFile().c().getInt("stats." + stat,0);
    }

    public void add(Player p) {
        PlayerFiles u = Main.playerManager.getUhcPlayer(p).getFile();
        u.c().set("stats." + stat, u.c().getInt("stats." + stat,0) + 1);
        u.save();
    }

    public List<String> getTop(Player p) {
        List<String> list = new ArrayList<>();
        HashMap<String, Integer> hash = new HashMap<>();
        PlayerFiles.getConfigs().forEach(c -> hash.put(c.getString("username"), c.getInt("stats." + stat,0)));
        List<Map.Entry<String, Integer>> entry = hash.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(10).toList();
        for (int i = 0; i < entry.toArray().length; i++) {
            String name = entry.get(i).getKey();
            int value = entry.get(i).getValue();
            int top = i + 1;
            list.add(Utils.chat("&f#" + top + " &a" + name + ": &6" + value));
        }
        return list;
    }
}
