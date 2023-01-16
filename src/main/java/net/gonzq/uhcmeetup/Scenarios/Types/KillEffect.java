package net.gonzq.uhcmeetup.Scenarios.Types;

import net.gonzq.uhcmeetup.Scenarios.Scenario;
import net.gonzq.uhcmeetup.Utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class KillEffect extends Scenario implements Listener {
    private boolean enabled = false;

    public KillEffect() {
        super("KillEffect", new ItemStack(Material.IRON_SWORD), "&7When you kill someone it will give you a random potion effect.");
    }

    @EventHandler
    public void onKill(PlayerDeathEvent e) {
        Player k = e.getEntity().getKiller();
        if (k != null) {
            switch (Utils.getRandomInt(14)) {
                case 1 -> k.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,250,1));
                case 2 -> k.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,250,0));
                case 3 -> k.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,250,0));
                case 4 -> k.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,250,1));
                case 5 -> k.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION,250,0));
                case 6 -> k.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,250,1));
                case 7 -> k.addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER,250,1));
                case 8 -> k.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE,250,0));
                case 9 -> k.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,250,2));
                case 10 -> k.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE,250,1));
                case 11 -> k.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST,250,4));
                case 12 -> k.addPotionEffect(new PotionEffect(PotionEffectType.LUCK,250,4));
                case 13 -> k.addPotionEffect(new PotionEffect(PotionEffectType.WITHER,250,0));
                case 14 -> k.addPotionEffect(new PotionEffect(PotionEffectType.POISON,250,0));
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
