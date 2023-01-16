package net.gonzq.uhcmeetup.Utils;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.gonzq.uhcmeetup.Enums.GameState;
import net.gonzq.uhcmeetup.Enums.PlayerState;
import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.Managers.GameManager;
import net.gonzq.uhcmeetup.Managers.PlayerManager;
import net.gonzq.uhcmeetup.Managers.ScenarioManager;
import net.gonzq.uhcmeetup.Managers.WorldManager;
import net.gonzq.uhcmeetup.players.GamePlayer;
import net.gonzq.uhcmeetup.Scenarios.Scenario;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class Placeholders extends PlaceholderExpansion {
    private Main pl = Main.pl;
    private HashMap<UUID, Integer> lastShowScen;

    public Placeholders() {
        lastShowScen = new HashMap<>();
    }

    @Override
    public @NotNull String getIdentifier() {
        return "meetup";
    }

    @Override
    public @NotNull String getAuthor() {
        return "! Gonzq#4451";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        GameManager game = GameManager.getInstance();
        WorldManager wm = WorldManager.getInstance();
        PlayerManager pm = Main.playerManager;
        ScenarioManager scen = ScenarioManager.getInstance();
        GamePlayer p = pm.getUhcPlayer(player);
        if (identifier.equals("border")) return String.valueOf(wm.getBorder());
        if (identifier.equals("players_to_start")) return String.valueOf(pl.config.getConfig().getInt("players-to-start"));
        if (identifier.equals("spectators")) return String.valueOf(pm.getPlayerList().stream().filter(GamePlayer::isOnline).filter(a -> a.getState().equals(PlayerState.SPECTATING)).toList().size());
        if (identifier.equals("timeelapsed")) return String.valueOf(Utils.timeConvert(game.getTimeElapsed()));
        if (identifier.equals("playersalive")) return String.valueOf(pm.getPlayingPlayers().size());
        if (identifier.equals("kills")) return String.valueOf(p.getKills());
        if (identifier.equals("online")) return String.valueOf(pm.getPlayerList().stream().filter(GamePlayer::isOnline).toList().size());
        if (identifier.equals("countdown")) return String.valueOf(game.getCountdown());
        if (identifier.equals("scenarios")) {
            if (scen.getEnabledScenarios().isEmpty()) return "none";
            Scenario[] active = scen.getEnabledScenarios().toArray(new Scenario[0]);
            int showScen = lastShowScen.getOrDefault(p.getUid(), -1) + 1;
            if (showScen >= active.length) {
                showScen = 0;
            }
            lastShowScen.put(p.getUid(), showScen);
            return active[showScen].getName();
        }
        if (identifier.equals("winner")) {
            if (game.getState().equals(GameState.FINALIZED)) {
                return pm.getWinner().getName();
            }
            return "none";
        }
        return null;
    }
}
