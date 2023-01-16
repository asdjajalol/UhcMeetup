package net.gonzq.uhcmeetup.Scenarios.Types;

import net.gonzq.uhcmeetup.Managers.WorldManager;
import net.gonzq.uhcmeetup.Scenarios.Scenario;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class WebCage extends Scenario implements Listener {
    private boolean enabled = false;

    public WebCage() {
        super("WebCage", new ItemStack(Material.COBWEB), "&7Killing someone spawns a box of cobwebs around",
                "&7of the player who died.");
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (!isEnabled()) {
            return;
        }
        Player p = e.getEntity();
        if (p.getWorld().equals(WorldManager.getInstance().getMeetupWorld())) {
            p.getWorld().getBlockAt(p.getLocation().add(3.0D, 0.0D, 0.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(3.0D, 1.0D, 0.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(3.0D, 2.0D, 0.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(0.0D, 0.0D, 3.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(0.0D, 1.0D, 3.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(0.0D, 2.0D, 3.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(-3.0D, 0.0D, 0.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(-3.0D, 1.0D, 0.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(-3.0D, 2.0D, 0.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(0.0D, 0.0D, -3.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(0.0D, 1.0D, -3.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(0.0D, 2.0D, -3.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(2.0D, 0.0D, 2.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(2.0D, 1.0D, 2.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(2.0D, 2.0D, 2.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(-2.0D, 0.0D, 2.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(-2.0D, 1.0D, 2.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(-2.0D, 2.0D, 2.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(-2.0D, 0.0D, -2.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(-2.0D, 1.0D, -2.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(-2.0D, 2.0D, -2.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(2.0D, 0.0D, -2.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(2.0D, 1.0D, -2.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(2.0D, 2.0D, -2.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(3.0D, 0.0D, 1.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(3.0D, 1.0D, 1.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(3.0D, 2.0D, 1.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(1.0D, 0.0D, 3.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(1.0D, 1.0D, 3.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(1.0D, 2.0D, 3.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(-3.0D, 0.0D, 1.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(-3.0D, 1.0D, 1.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(-3.0D, 2.0D, 1.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(1.0D, 0.0D, -3.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(1.0D, 1.0D, -3.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(1.0D, 2.0D, -3.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(3.0D, 0.0D, -1.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(3.0D, 1.0D, -1.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(3.0D, 2.0D, -1.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(-1.0D, 0.0D, 3.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(-1.0D, 1.0D, 3.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(-1.0D, 2.0D, 3.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(-3.0D, 0.0D, -1.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(-3.0D, 1.0D, -1.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(-3.0D, 2.0D, -1.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(-1.0D, 0.0D, -3.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(-1.0D, 1.0D, -3.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(-1.0D, 2.0D, -3.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(-1.0D, 3.0D, -2.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(-1.0D, 3.0D, -1.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(-1.0D, 3.0D, 0.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(-1.0D, 3.0D, 2.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(-1.0D, 3.0D, 1.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(0.0D, 3.0D, -1.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(1.0D, 3.0D, 2.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(1.0D, 3.0D, 1.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(1.0D, 3.0D, 0.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(1.0D, 3.0D, -2.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(1.0D, 3.0D, -1.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(0.0D, 3.0D, 1.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(2.0D, 3.0D, 1.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(-2.0D, 3.0D, 1.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(2.0D, 3.0D, -1.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(-2.0D, 3.0D, -1.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(2.0D, 3.0D, 0.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(-2.0D, 3.0D, 0.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(0.0D, 3.0D, 2.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(0.0D, 3.0D, -2.0D)).setType(Material.COBWEB);
            p.getWorld().getBlockAt(p.getLocation().add(0.0D, 3.0D, 0.0D)).setType(Material.COBWEB);
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
