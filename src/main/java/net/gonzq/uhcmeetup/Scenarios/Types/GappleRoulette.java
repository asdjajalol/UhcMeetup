package net.gonzq.uhcmeetup.Scenarios.Types;

import net.gonzq.uhcmeetup.Scenarios.Scenario;
import net.gonzq.uhcmeetup.Utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GappleRoulette extends Scenario implements Listener {
    private boolean enabled = false;

    public GappleRoulette() {
        super("GappleRoulette", new ItemStack(Material.GOLDEN_APPLE), "&7&7Eating a golden apple will give you a random potion effect");
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent e) {
        Player p = e.getPlayer();
        if (e.getItem().getType().equals(Material.GOLDEN_APPLE)) {
            switch (Utils.getRandomInt(14)) {
                case 1 -> p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 250, 1));
                case 2 -> p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 250, 0));
                case 3 -> p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 250, 0));
                case 4 -> p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 250, 1));
                case 5 -> p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 250, 0));
                case 6 -> p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 250, 1));
                case 7 -> p.addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, 250, 1));
                case 8 -> p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 250, 0));
                case 9 -> p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 250, 2));
                case 10 -> p.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 250, 1));
                case 11 -> p.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 250, 4));
                case 12 -> p.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, 250, 4));
                case 13 -> p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 250, 0));
                case 14 -> p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 250, 0));
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
