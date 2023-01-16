package net.gonzq.uhcmeetup.Events;

import net.gonzq.uhcmeetup.Enums.GameState;
import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.Managers.GameManager;
import net.gonzq.uhcmeetup.Utils.Utils;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class FightsEvent implements Listener {
    private Main pl = Main.pl;
    private GameManager game = GameManager.getInstance();

    public FightsEvent() {
        pl.getServer().getPluginManager().registerEvents(this, pl);
    }

    @EventHandler
    public void onShoot(EntityDamageByEntityEvent e) {
        if (game.getState().equals(GameState.STARTED)) {
            if (e.getEntity() instanceof Player p && e.getDamager() instanceof Arrow arrow) {
                if (arrow.getShooter() instanceof Player d) {
                    Utils.delay(1, () -> {
                        double h = p.getHealth() + p.getAbsorptionAmount();
                        if (h > 20) {
                            d.sendMessage(Utils.chat(pl.prefix + pl.lang.getConfig().getString("arrow-damage")).replace("%player%", p.getName())
                                    .replace("%color%", Utils.chat("&2")).replace("%hearts%", String.valueOf((int) h)));
                        } else if (h >= 16) {
                            d.sendMessage(Utils.chat(pl.prefix + pl.lang.getConfig().getString("arrow-damage")).replace("%player%", p.getName())
                                    .replace("%color%", Utils.chat("&2")).replace("%hearts%", String.valueOf((int) h)));
                        } else if (h >= 12) {
                            d.sendMessage(Utils.chat(pl.prefix + pl.lang.getConfig().getString("arrow-damage")).replace("%player%", p.getName())
                                    .replace("%color%", Utils.chat("&a")).replace("%hearts%", String.valueOf((int) h)));
                        } else if (h >= 8) {
                            d.sendMessage(Utils.chat(pl.prefix + pl.lang.getConfig().getString("arrow-damage")).replace("%player%", p.getName())
                                    .replace("%color%", Utils.chat("&6")).replace("%hearts%", String.valueOf((int) h)));
                        } else if (h >= 4) {
                            d.sendMessage(Utils.chat(pl.prefix + pl.lang.getConfig().getString("arrow-damage")).replace("%player%", p.getName())
                                    .replace("%color%", Utils.chat("&c")).replace("%hearts%", String.valueOf((int) h)));
                        } else if (h >= 0) {
                            d.sendMessage(Utils.chat(pl.prefix + pl.lang.getConfig().getString("arrow-damage")).replace("%player%", p.getName())
                                    .replace("%color%", Utils.chat("&4")).replace("%hearts%", String.valueOf((int) h)));
                        }
                    });
                }
            }
        }
    }

    @EventHandler
    public void onShieldSound(EntityDamageByEntityEvent e) {
        if (pl.config.getConfig().getBoolean("shield-break-sound")) {
            if (e.getEntity() instanceof Player p && e.getDamager() instanceof Player d) {
                if (d.getInventory().getItemInMainHand().getType().toString().endsWith("_AXE") && p.isBlocking()) {
                    double h1 = p.getHealth() + p.getAbsorptionAmount();
                    Utils.delay(1, () -> {
                        double h2 = p.getHealth() + p.getAbsorptionAmount();
                        if (h1 == h2) d.playSound(p.getLocation(), Sound.ITEM_SHIELD_BREAK, 1, 1);
                    });
                }
            }
        }
    }
}
