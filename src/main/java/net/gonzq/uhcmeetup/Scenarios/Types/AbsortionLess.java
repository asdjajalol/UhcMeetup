package net.gonzq.uhcmeetup.Scenarios.Types;

import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.Scenarios.Scenario;
import net.gonzq.uhcmeetup.Utils.Utils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class AbsortionLess extends Scenario implements Listener {
    private boolean enabled = false;
    public AbsortionLess() {
        super("AbsortionLess", new ItemStack(Material.GOLDEN_APPLE), "&7Eating a golden apple will not give you the absorption");
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent e) {
        if (e.getItem().getType().equals(Material.GOLDEN_APPLE)) {
            Utils.delay(1, () -> e.getPlayer().removePotionEffect(PotionEffectType.ABSORPTION));
        }
    }

    @Override
    protected void setEnabled(boolean enable) {
        this.enabled = enable;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
