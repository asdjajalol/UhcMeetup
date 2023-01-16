package net.gonzq.uhcmeetup.Events;

import net.gonzq.uhcmeetup.Enums.GameState;
import net.gonzq.uhcmeetup.Enums.PlayerState;
import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.Managers.GameManager;
import net.gonzq.uhcmeetup.Managers.StatsManager;
import net.gonzq.uhcmeetup.Managers.WorldManager;
import net.gonzq.uhcmeetup.players.GamePlayer;
import net.gonzq.uhcmeetup.Utils.Utils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathEvent implements Listener {
    private Main pl = Main.pl;
    private GameManager game = GameManager.getInstance();
    private WorldManager world = WorldManager.getInstance();

    public DeathEvent() {
        pl.getServer().getPluginManager().registerEvents(this,pl);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        GamePlayer p = Main.playerManager.getUhcPlayer(e.getEntity());
        if (game.getState().equals(GameState.STARTED)) {
            if (p.getPlayer().getKiller() != null) {
                GamePlayer k = Main.playerManager.getUhcPlayer(p.getPlayer().getKiller());
                k.addKill();
            }
            p.setState(PlayerState.SPECTATING);
            Main.playerManager.checkIfRemainingPlayers();
            StatsManager.DEATHS.add(p.getPlayer());
            Utils.delay(1, () -> {
                p.getPlayer().teleport(new Location(world.getMeetupWorld(),0,100,0));
                p.getPlayer().setGameMode(GameMode.SPECTATOR);
            });
        }
    }
}
