package net.gonzq.uhcmeetup.Managers;

import net.gonzq.uhcmeetup.Enums.GameState;
import net.gonzq.uhcmeetup.Exceptions.GamePlayerDoesNotExistException;
import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.players.GamePlayer;
import net.gonzq.uhcmeetup.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class PlayerManager {
    private final List<GamePlayer> players;

    public PlayerManager() {
        players = new ArrayList<>();
    }

    public boolean doesPlayerExist(Player p) {
        try {
            getUhcPlayer(p.getUniqueId());
            return true;
        } catch (GamePlayerDoesNotExistException e) {
            return false;
        }
    }

    public GamePlayer getUhcPlayer(Player p) {
        try {
            return getUhcPlayer(p.getUniqueId());
        } catch (GamePlayerDoesNotExistException e) {
            throw new RuntimeException(e);
        }
    }
    public GamePlayer getUhcPlayer(String name) throws GamePlayerDoesNotExistException{
        for (GamePlayer p : getPlayerList()) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        throw new RuntimeException(name);
    }
    public GamePlayer getUhcPlayer(UUID uid) throws GamePlayerDoesNotExistException{
        for (GamePlayer p : getPlayerList()) {
            if (p.getUid().equals(uid)) {
                return p;
            }
        }
        throw new GamePlayerDoesNotExistException(uid.toString());
    }

    public GamePlayer getOrCreateGamePlayer(Player p) {
        if (doesPlayerExist(p)) {
            return getUhcPlayer(p);
        } else {
            return newUhcPlayer(p);
        }
    }
    public synchronized GamePlayer newUhcPlayer(Player p) {
        return newUhcPlayer(p.getUniqueId(), p.getName());
    }
    public synchronized GamePlayer newUhcPlayer(UUID uid, String name) {
        GamePlayer newPlayer = new GamePlayer(uid,name);
        getPlayerList().add(newPlayer);
        return newPlayer;
    }
    public synchronized List<GamePlayer> getPlayerList() {
        return players;
    }

    public Set<GamePlayer> getPlayingPlayers() {
        return players.stream()
                .filter(GamePlayer::isPlaying)
                .collect(Collectors.toSet());
    }

    public void setAllPlayersEndGame() {
        GameManager manager = GameManager.getInstance();
        GamePlayer winner = getWinner();
        Main plugin = Main.pl;
        Bukkit.broadcastMessage(Utils.chat(plugin.prefix + plugin.lang.getConfig().getString("win-solo")).replace("%player%", winner.getName()).replace("%kills%", String.valueOf(winner.getKills())));
        StatsManager.WINS.add(winner.getPlayer());
        manager.setState(GameState.FINALIZED);
        plugin.boards.values().forEach(f -> f.updateLines(List.of("")));
        Bukkit.broadcastMessage(Utils.chat(plugin.prefix + plugin.lang.getConfig().getString("server-restart")).replace("%time%", String.valueOf(plugin.config.getConfig().getInt("restart-countdown"))));
        Utils.delay(plugin.config.getConfig().getInt("restart-countdown") * 20L, () -> {
            Bukkit.getServer().spigot().restart();
        });
    }
    public GamePlayer getWinner() {
        return getPlayerList().stream().filter(GamePlayer::isPlaying).toList().get(0);
    }

    public void playerJoinsTheGame(Player p) {
        GamePlayer gp;
        if (doesPlayerExist(p)) {
            gp = getUhcPlayer(p);
        } else {
            gp = newUhcPlayer(p);
        }
    }

    public void checkIfRemainingPlayers(){
        int playingPlayers = 0;

        for (GamePlayer player : getPlayerList()) {
            if (player.isPlaying()) playingPlayers++;
        }

        if(playingPlayers <= 1){
            setAllPlayersEndGame();
        }
    }
}
