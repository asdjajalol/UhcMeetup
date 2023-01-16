package net.gonzq.uhcmeetup.Scenarios.Types;

import net.gonzq.uhcmeetup.Managers.ScenarioManager;
import net.gonzq.uhcmeetup.Managers.WorldManager;
import net.gonzq.uhcmeetup.Scenarios.Scenario;
import net.gonzq.uhcmeetup.Utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GoldenRetriever extends Scenario implements Listener {
    private boolean enabled = false;

    public GoldenRetriever() {
        super("GoldenRetriever", new ItemStack(Material.GOLDEN_APPLE), "&7When someone dies they drop a golden head");
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        ScenarioManager scen = ScenarioManager.getInstance();
            if (!scen.getScenario("TimeBomb").isEnabled() && !scen.getScenario("GraveRobbers").isEnabled()) {
                if (event.getEntity().getWorld().equals(WorldManager.getInstance().getMeetupWorld())) {
                    event.getDrops().add(Utils.goldenHead());
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
