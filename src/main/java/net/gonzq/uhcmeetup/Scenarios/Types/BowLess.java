package net.gonzq.uhcmeetup.Scenarios.Types;

import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.Scenarios.Scenario;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

public class BowLess extends Scenario implements Listener {
    private boolean enabled = false;

    public BowLess() {
        super("BowLess", new ItemStack(Material.BOW), "&7You cannot use bows");
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent e) {
        if (e.getEntity() instanceof Player) e.setCancelled(true);
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
