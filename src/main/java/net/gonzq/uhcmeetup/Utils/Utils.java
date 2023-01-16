package net.gonzq.uhcmeetup.Utils;


import net.gonzq.uhcmeetup.Commands.*;
import net.gonzq.uhcmeetup.Events.*;
import net.gonzq.uhcmeetup.FastInv.ItemBuilder;
import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.Managers.ScenarioManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static String chat(String s) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
            s = s.replace("&#", "#");
            Matcher match = pattern.matcher(s);
            while (match.find()) {
                String color = s.substring(match.start(), match.end());
                s = s.replace(color, net.md_5.bungee.api.ChatColor.of(color) + "");
                match = pattern.matcher(s);
            }
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static void delay(long ticks, Runnable run) {
        Bukkit.getScheduler().runTaskLater(Main.pl,run, ticks);
    }

    public static Integer getRandomInt(Integer i) {
        Random rand = new Random();
        return rand.nextInt(i) + 1;
    }
    public static Integer getRandomInt(Integer min, Integer max) {

        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    public static String timeConvert(int t) {
        int hours = t / 3600;

        int minutes = (t % 3600) / 60;
        int seconds = t % 60;

        return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes, seconds)
                : String.format("%02d:%02d", minutes, seconds);
    }

    public static ItemStack goldenHead() {
        return new ItemBuilder(Material.GOLDEN_APPLE).name(Utils.chat("&6&lGolden Head")).build();
    }

    public static void setObjectives() {
        Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
        Objective tab = board.getObjective("health_tab");
        Objective name = board.getObjective("health_name");
        if (Main.pl.board.getConfig().getBoolean("scoreboard.tab-health")) {
            if (tab == null) {
                tab = board.registerNewObjective("health_tab","health",".", RenderType.HEARTS);
                tab.setDisplaySlot(DisplaySlot.PLAYER_LIST);
            }
        }
        if (Main.pl.board.getConfig().getBoolean("scoreboard.name-health")) {
            if (name == null) {
                name = board.registerNewObjective("health_name", "health");
                name.setDisplaySlot(DisplaySlot.BELOW_NAME);
                name.setDisplayName(Utils.chat("&c❤"));
            }
        }
    }

    public static void registerCommandsAndEvents() {
        ScenarioManager scen = ScenarioManager.getInstance();
        scen.setup();
         Main.pl.config.getConfig().getStringList("default-scenarios-enabled").forEach(s -> {
             if (scen.getScenario(s) != null) {
                 scen.getScenario(s).enable();
             }
         });

        // Listeners
        new ConsumeEvent();
        new HorseEvent();
        new DeathEvent();
        new FightsEvent();
        new HubEvents();
        new InteractEvent();

        // Commands
        new MeetupCommands();
        new KtCommand();
        new ScenarioCommands();
        new StatsCommand();
        new VoteCommand();
        new ForceStartCommand();
    }
}
