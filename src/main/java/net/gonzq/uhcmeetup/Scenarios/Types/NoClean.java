package net.gonzq.uhcmeetup.Scenarios.Types;

import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.Scenarios.Scenario;
import net.gonzq.uhcmeetup.Utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NoClean extends Scenario implements Listener {
    private Main pl = Main.pl;
    private boolean enabled = false;
    private List<UUID> noCleanList;

    public NoClean(Main pl) {
        super("NoClean", new ItemStack(Material.QUARTZ), "&7When you kill someone you will be immune for " + pl.config.getConfig().getInt("noclean-time"),
                "&7seconds to any damage");
        noCleanList = new ArrayList<>();
    }

    @EventHandler
    public void noClean(PlayerDeathEvent e) {
        if (e.getEntity().getKiller() != null) {
            Player k = e.getEntity().getKiller();
            k.setInvulnerable(true);
            noCleanList.add(k.getUniqueId());
            k.sendMessage(Utils.chat(pl.prefix + pl.lang.getConfig().getString("noclean").replace("%time%", String.valueOf(pl.config.getConfig().getInt("noclean-time")))));
            Utils.delay(pl.config.getConfig().getInt("noclean-time")* 20L, () -> {
                if (noCleanList.contains(k.getUniqueId())) {
                    noCleanList.remove(k.getUniqueId());
                    k.setInvulnerable(false);
                    k.sendMessage(Utils.chat(pl.prefix + "You are no longer invulnerable."));
                }
            });
        }
    }
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            if (noCleanList.contains(e.getDamager().getUniqueId())) {
                noCleanList.remove(e.getDamager().getUniqueId());
                e.getDamager().setInvulnerable(false);
                e.getDamager().sendMessage(Utils.chat(pl.prefix + "You are no longer invulnerable."));
            }
        } else if (e.getEntity() instanceof Player && e.getDamager() instanceof Arrow) {
            Arrow arrow = (Arrow) e.getDamager();
            if (arrow.getShooter() instanceof Player) {
                Player s = (Player) arrow.getShooter();
                if (noCleanList.contains(s.getUniqueId())) {
                    noCleanList.remove(s.getUniqueId());
                    s.setInvulnerable(false);
                    s.sendMessage(Utils.chat(pl.prefix + "You are no longer invulnerable."));
                }
            }
        } else if (e.getEntity() instanceof Player && e.getDamager() instanceof Trident) {
            Trident trident = (Trident) e.getDamager();
            if (trident.getShooter() instanceof Player) {
                Player s = (Player) trident.getShooter();
                if (noCleanList.contains(s.getUniqueId())) {
                    noCleanList.remove(s.getUniqueId());
                    s.setInvulnerable(false);
                    s.sendMessage(Utils.chat(pl.prefix + "You are no longer invulnerable."));
                }
            }
        } else if (e.getEntity() instanceof Player && e.getDamager() instanceof SpectralArrow) {
            SpectralArrow arrow = (SpectralArrow) e.getDamager();
            if (arrow.getShooter() instanceof Player) {
                Player s = (Player) arrow.getShooter();
                if (noCleanList.contains(s.getUniqueId())) {
                    noCleanList.remove(s.getUniqueId());
                    s.setInvulnerable(false);
                    s.sendMessage(Utils.chat(pl.prefix + "You are no longer invulnerable."));
                }
            }
        }
    }

    @EventHandler
    public void onLava(PlayerBucketEmptyEvent e) {
        Player p = e.getPlayer();
        if (e.getBucket() == Material.LAVA_BUCKET) {
            if (noCleanList.contains(p.getUniqueId())) {
                noCleanList.remove(p.getUniqueId());
                p.setInvulnerable(false);
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
