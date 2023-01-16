package net.gonzq.uhcmeetup.Events;

import net.gonzq.uhcmeetup.Enums.GameState;
import net.gonzq.uhcmeetup.Guis.ScenGui;
import net.gonzq.uhcmeetup.Guis.ScenarioGui;
import net.gonzq.uhcmeetup.Guis.VoteGui;
import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.Managers.GameManager;
import net.gonzq.uhcmeetup.Managers.WorldManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InteractEvent implements Listener {
    private Main pl = Main.pl;
    private WorldManager world = WorldManager.getInstance();

    public InteractEvent() {
        pl.getServer().getPluginManager().registerEvents(this,pl);
    }

    @EventHandler
    public void onItem(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack item = e.getItem();
        if (item == null) return;
        if (e.hasItem()) {
            if (GameManager.getInstance().getState().equals(GameState.WAITING)) {
                if (item.getType().equals(Material.ENCHANTED_BOOK)){
                    new ScenarioGui().open(p);
                    return;
                }
            }
            if (p.getWorld().equals(world.getLobby())) {
                switch (item.getType()) {
                    case BOOK -> p.chat("/scen");
                    case DIAMOND -> p.chat("/stats");
                }
            }
        }
    }
}
