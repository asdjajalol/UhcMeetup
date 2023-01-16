package net.gonzq.uhcmeetup.Tasks;

import me.clip.placeholderapi.PlaceholderAPI;
import net.gonzq.uhcmeetup.Enums.GameState;
import net.gonzq.uhcmeetup.Enums.PlayerState;
import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.Managers.GameManager;
import net.gonzq.uhcmeetup.Managers.PlayerManager;
import net.gonzq.uhcmeetup.Managers.ScenarioManager;
import net.gonzq.uhcmeetup.Managers.WorldManager;
import net.gonzq.uhcmeetup.players.GamePlayer;
import net.gonzq.uhcmeetup.Scenarios.Scenario;
import net.gonzq.uhcmeetup.Utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ScoreboardTask extends BukkitRunnable {
    Main pl = Main.pl;
    private HashMap<UUID, Integer> lastShowScen;
    public ScoreboardTask() {
        lastShowScen = new HashMap<>();
    }

    @Override
    public void run() {
        pl.boards.values().forEach(f -> {
            f.updateTitle(Utils.chat(pl.board.getConfig().getString("scoreboard.title")));
            switch (GameManager.getInstance().getState()) {
                case WAITING -> {
                    List<String> lines = pl.board.getConfig().getStringList("scoreboard.lobby");
                    for (int i = 0; i < lines.toArray().length; i++) {
                        String line = lines.get(i);
                        if (pl.placeholderapi) {
                            f.updateLine(i, PlaceholderAPI.setPlaceholders(f.getPlayer(), line));
                            PlaceholderAPI.setPlaceholders(f.getPlayer(), line);
                        } else {
                            line = replace(f.getPlayer(),line);
                            f.updateLine(i, Utils.chat(line));
                        }
                    }
                }
                case STARTING -> {
                    List<String> lines = pl.board.getConfig().getStringList("scoreboard.starting");
                    for (int i = 0; i < lines.toArray().length; i++) {
                        String line = lines.get(i);
                        if (pl.placeholderapi) {
                            f.updateLine(i, PlaceholderAPI.setPlaceholders(f.getPlayer(), line));
                            PlaceholderAPI.setPlaceholders(f.getPlayer(), lines);
                        } else {
                            line = replace(f.getPlayer(),line);
                            f.updateLine(i, Utils.chat(line));
                        }
                    }
                }
                case STARTED -> {
                    List<String> lines = pl.board.getConfig().getStringList("scoreboard.started");
                    for (int i = 0; i < lines.toArray().length; i++) {
                        String line = lines.get(i);
                        if (pl.placeholderapi) {
                            f.updateLine(i, PlaceholderAPI.setPlaceholders(f.getPlayer(),line));
                        } else {
                            line = replace(f.getPlayer(),line);
                            f.updateLine(i, Utils.chat(line));
                        }
                    }
                }
                case FINALIZED -> {
                    List<String> lines = pl.board.getConfig().getStringList("scoreboard.finalized");
                    for (int i = 0; i < lines.toArray().length; i++) {
                        String line = lines.get(i);
                        if (pl.placeholderapi) {
                            f.updateLine(i, PlaceholderAPI.setPlaceholders(f.getPlayer(),line));
                        } else {
                            line = replace(f.getPlayer(), line);
                            f.updateLine(i,Utils.chat(line));
                        }
                    }
                }
            }
        });
    }

    private String replace(Player player, String line) {
        GameManager game = GameManager.getInstance();
        WorldManager wm = WorldManager.getInstance();
        PlayerManager pm = Main.playerManager;
        ScenarioManager scen = ScenarioManager.getInstance();
        GamePlayer p = pm.getUhcPlayer(player);
        String key = "%meetup_";
        if (line.contains(key + "border%"))  line = line.replace(key + "border%",String.valueOf(wm.getBorder()));
        if (line.contains(key + "players_to_start%")) line = line.replace(key + "players_to_start%", String.valueOf(pl.config.getConfig().getInt("players-to-start")));
        if (line.contains(key + "spectators%"))  line = line.replace(key + "spectators%",String.valueOf(pm.getPlayerList().stream().filter(GamePlayer::isOnline).filter(a -> a.getState().equals(PlayerState.SPECTATING)).toList().size()));
        if (line.contains(key + "timeelapsed%")) line = line.replace(key + "timeelapsed%",Utils.timeConvert(game.getTimeElapsed()));
        if (line.contains(key + "playersalive%")) line = line.replace(key + "playersalive%", String.valueOf(pm.getPlayingPlayers().size()));
        if (line.contains(key + "kills%")) line = line.replace(key + "kills%", String.valueOf(p.getKills()));
        if (line.contains(key + "online%")) line = line.replace(key + "online%", String.valueOf(pm.getPlayerList().stream().filter(GamePlayer::isOnline).toList().size()));
        if (line.contains(key + "countdown%")) line = line.replace(key + "countdown%", String.valueOf(game.getCountdown()));
        if (line.contains(key + "scenarios%")) {
            if (scen.getEnabledScenarios().isEmpty()) {
                line = line.replace(key + "scenarios%", "none");
            } else {
                Scenario[] active = scen.getEnabledScenarios().toArray(new Scenario[0]);
                int showScen = lastShowScen.getOrDefault(p.getUid(),-1) + 1;
                if (showScen >= active.length) {
                    showScen = 0;
                }
                lastShowScen.put(p.getUid(),showScen);
                line = line.replace(key + "scenarios%", active[showScen].getName());
            }
        }
        if (line.contains(key + "winner%")) {
            if (game.getState().equals(GameState.FINALIZED)) {
                line = line.replace(key + "winner%", pm.getWinner().getName());
            } else {
                line = line.replace(key + "winner%", "none");
            }
        }
        return line;
    }
}
