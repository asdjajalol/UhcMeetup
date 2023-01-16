package net.gonzq.uhcmeetup.Scenarios.Types;

import net.gonzq.uhcmeetup.Scenarios.Scenario;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class CobwebLess extends Scenario implements Listener {
    private boolean enabled = false;

    public CobwebLess() {
        super("CobwebLess", new ItemStack(Material.COBWEB),"&7You can't use cobwebs");
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (e.getBlock().getType().equals(Material.COBWEB)) e.setCancelled(true);
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
