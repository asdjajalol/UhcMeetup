package net.gonzq.uhcmeetup.Events;

import net.gonzq.uhcmeetup.Enums.GameState;
import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.Managers.GameManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;

public class HorseEvent implements Listener {
    Main pl = Main.pl;
    GameManager game = GameManager.getInstance();

    public HorseEvent() {
        pl.getServer().getPluginManager().registerEvents(this, pl);
    }

    @EventHandler
    public void onEnter(VehicleEnterEvent e) {
        if (game.getState().equals(GameState.STARTING)) e.setCancelled(true);
    }

    @EventHandler
    public void onExit(VehicleExitEvent e) {
        if (game.getState().equals(GameState.STARTING)) e.setCancelled(true);
    }
}
