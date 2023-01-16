package net.gonzq.uhcmeetup.Scenarios.Types;

import net.gonzq.uhcmeetup.Scenarios.Scenario;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FastGetaway extends Scenario implements Listener {
    private boolean enabled = false;

    public FastGetaway() {
        super("FastGetaway", new ItemStack(Material.SUGAR),"&7When you kill someone you will have Speed II for 1 minute.");
    }

    @EventHandler
    public void onKill(PlayerDeathEvent e) {
        Player k = e.getEntity().getKiller();
        if (k != null) {
            k.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,1200,1,true,true));
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
