package net.gonzq.uhcmeetup.Tasks;

import net.gonzq.uhcmeetup.Enums.GameState;
import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.Managers.GameManager;
import net.gonzq.uhcmeetup.Managers.ScatterManager;
import net.gonzq.uhcmeetup.players.GamePlayer;
import net.gonzq.uhcmeetup.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class CountdownTask extends BukkitRunnable {
    private String msg;
    private String votemsg;
    private boolean vote;
    private Main pl = Main.pl;
    private final GameManager g = GameManager.getInstance();
    public CountdownTask() {
        msg = pl.lang.getConfig().getString("countdown-msg");
        vote = pl.config.getConfig().getBoolean("vote-system.scenario-system");
        votemsg = pl.lang.getConfig().getString("vote-msg");
    }


    @Override
    public void run() {
        if (g.getCountdown() == 60) {
            Bukkit.broadcastMessage(Utils.chat(pl.prefix + msg.replace("%time%", "60")));
            if (vote) Main.playerManager.getPlayerList().stream().filter(GamePlayer::isOnline).filter(p -> !p.isVotedScen()).filter(p -> p.getPlayer().hasPermission("meetup.vote"))
                    .forEach(p -> p.getPlayer().sendMessage(Utils.chat(pl.prefix + votemsg)));
        }
        if (g.getCountdown() == 30) {
            Bukkit.broadcastMessage(Utils.chat(pl.prefix + msg.replace("%time%", "30")));
            if (vote) Main.playerManager.getPlayerList().stream().filter(GamePlayer::isOnline).filter(p -> !p.isVotedScen()).filter(p -> p.getPlayer().hasPermission("meetup.vote"))
                    .forEach(p -> p.getPlayer().sendMessage(Utils.chat(pl.prefix + votemsg)));
        }
        if (g.getCountdown() == 10) {
            Bukkit.broadcastMessage(Utils.chat(pl.prefix + msg.replace("%time%", "10")));
            if (vote) Main.playerManager.getPlayerList().stream().filter(GamePlayer::isOnline).filter(p -> !p.isVotedScen()).filter(p -> p.getPlayer().hasPermission("meetup.vote"))
                    .forEach(p -> p.getPlayer().sendMessage(Utils.chat(pl.prefix + votemsg)));
        }
        if (g.getCountdown() == 5) {
            Bukkit.broadcastMessage(Utils.chat(pl.prefix + msg.replace("%time%", "5")));
            if (vote) Main.playerManager.getPlayerList().stream().filter(GamePlayer::isOnline).filter(p -> !p.isVotedScen()).filter(p -> p.getPlayer().hasPermission("meetup.vote"))
                    .forEach(p -> p.getPlayer().sendMessage(Utils.chat(pl.prefix + votemsg)));
        }
        if (g.getCountdown() == 4) {
            Bukkit.broadcastMessage(Utils.chat(pl.prefix + msg.replace("%time%", "4")));
            if (vote) Main.playerManager.getPlayerList().stream().filter(GamePlayer::isOnline).filter(p -> !p.isVotedScen()).filter(p -> p.getPlayer().hasPermission("meetup.vote"))
                    .forEach(p -> p.getPlayer().sendMessage(Utils.chat(pl.prefix + votemsg)));
        }
        if (g.getCountdown() == 3) {
            Bukkit.broadcastMessage(Utils.chat(pl.prefix + msg.replace("%time%", "3")));
            if (vote) Main.playerManager.getPlayerList().stream().filter(GamePlayer::isOnline).filter(p -> !p.isVotedScen()).filter(p -> p.getPlayer().hasPermission("meetup.vote"))
                    .forEach(p -> p.getPlayer().sendMessage(Utils.chat(pl.prefix + votemsg)));
        }
        if (g.getCountdown() == 2) {
            Bukkit.broadcastMessage(Utils.chat(pl.prefix + msg.replace("%time%", "2")));
            if (vote) Main.playerManager.getPlayerList().stream().filter(GamePlayer::isOnline).filter(p -> !p.isVotedScen()).filter(p -> p.getPlayer().hasPermission("meetup.vote"))
                    .forEach(p -> p.getPlayer().sendMessage(Utils.chat(pl.prefix + votemsg)));
        }
        if (g.getCountdown() == 1) {
            Bukkit.broadcastMessage(Utils.chat(pl.prefix + msg.replace("%time%", "1")));
            if (vote) Main.playerManager.getPlayerList().stream().filter(GamePlayer::isOnline).filter(p -> !p.isVotedScen()).filter(p -> p.getPlayer().hasPermission("meetup.vote"))
                    .forEach(p -> p.getPlayer().sendMessage(Utils.chat(pl.prefix + votemsg)));
        }
        if (g.getCountdown() == 0) {
            Main.playerManager.getPlayerList().stream().filter(GamePlayer::isOnline).forEach(ScatterManager.getInstance()::postScatter);
            if (vote) GameManager.getInstance().getVoteScenarios().endVotes();
            g.setState(GameState.STARTED);
            new BorderTask().runTaskTimer(pl,0,20);
            cancel();
        }
        g.restCountdown();
    }
}
