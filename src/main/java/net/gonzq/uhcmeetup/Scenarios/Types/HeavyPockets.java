package net.gonzq.uhcmeetup.Scenarios.Types;

import net.gonzq.uhcmeetup.Managers.ScenarioManager;
import net.gonzq.uhcmeetup.Managers.WorldManager;
import net.gonzq.uhcmeetup.Scenarios.Scenario;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class HeavyPockets extends Scenario implements Listener {
    private boolean enabled = false;

    public HeavyPockets() {
        super("HeavyPockets", new ItemStack(Material.NETHERITE_SCRAP), "&7When someone dies drop 2 netherite scrap");
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        ScenarioManager scen = ScenarioManager.getInstance();
        if (!scen.getScenario("TimeBomb").isEnabled() && !scen.getScenario("GraveRobbers").isEnabled()) {
            if (p.getWorld().equals(WorldManager.getInstance().getMeetupWorld())) {
                e.getDrops().add(new ItemStack(Material.NETHERITE_SCRAP,2));
            }
        }
    }

    @Override
    protected void setEnabled(boolean enable) {
        enabled = enable;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
