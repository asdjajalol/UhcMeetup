package net.gonzq.uhcmeetup.Managers;

import net.gonzq.uhcmeetup.Enums.PlayerState;
import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.players.GamePlayer;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class ScatterManager {
    private Main pl = Main.pl;

    private static ScatterManager instance = new ScatterManager();

    public static ScatterManager getInstance() {
        return instance;
    }

    public void scatter(GamePlayer p) {
        Player player = p.getPlayer();
        player.getPlayer().teleport(calculateCoords(), PlayerTeleportEvent.TeleportCause.PLUGIN);
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 120000, 5));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120000, 127));
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 120000, 200));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 120000, 5));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 12000, 5));
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 120000, 5));
        player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 120000, 5));
        player.getPlayer().setGameMode(GameMode.SURVIVAL);
        p.setKit();
    }

    public void postScatter(GamePlayer p) {
        p.getPlayer().getActivePotionEffects().forEach(pe -> p.getPlayer().removePotionEffect(pe.getType()));
        p.setState(PlayerState.PLAYING);
        p.getPlayer().setInvulnerable(false);
        StatsManager.PLAYED.add(p.getPlayer());
    }

    private Location calculateCoords() {
        WorldManager wm = WorldManager.getInstance();
        Random rand = new Random();
        int upperbound = wm.getBorder();
        int x = rand.nextInt(upperbound);
        int z = rand.nextInt(upperbound);
        double randDouble = Math.random();
        if (randDouble <= 0.5D) {
            x = -1 * x;
        }
        double randDouble2 = Math.random();
        if (randDouble2 <= 0.5D) {
            z = -1 * z;
        }
        int y;
        y = wm.getMeetupWorld().getHighestBlockYAt(x,z) + 1;
        if (isWeterNearby(new Location(wm.getMeetupWorld(),x,y,z))) {
            return calculateCoords();
        }
        return new Location(wm.getMeetupWorld(),x,y,z);
    }

    private boolean isWeterNearby(Location l) {
        boolean iswater = false;
        if (l.getBlock().getType().equals(Material.WATER)) {
            iswater = true;
        }
        else if (l.getBlock().getBiome().equals(Biome.OCEAN) ) {
            iswater = true;
        }
        else if (l.getBlock().getBiome().equals(Biome.RIVER)) {
            iswater = true;
        }
        if (l.add(0,-1,0).getBlock().getType().equals(Material.WATER)) {
            iswater = true;
        }
        if (l.getBlock().getType().equals(Material.WATER)) {
            iswater = true;
        }
        if (l.getBlock().getType().equals(Material.VOID_AIR)) {
            iswater = true;
        }
        if (l.add(0,-1,0).getBlock().getType().equals(Material.VOID_AIR)) {
            iswater = true;
        }
        return iswater;
    }
}
