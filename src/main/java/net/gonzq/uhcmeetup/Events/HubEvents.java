package net.gonzq.uhcmeetup.Events;

import net.gonzq.uhcmeetup.Enums.GameState;
import net.gonzq.uhcmeetup.Enums.PlayerState;
import net.gonzq.uhcmeetup.FastBoard.FastBoard;
import net.gonzq.uhcmeetup.FastInv.ItemBuilder;
import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.Managers.GameManager;
import net.gonzq.uhcmeetup.Managers.ScatterManager;
import net.gonzq.uhcmeetup.Managers.ScenarioManager;
import net.gonzq.uhcmeetup.Managers.WorldManager;
import net.gonzq.uhcmeetup.players.GamePlayer;
import net.gonzq.uhcmeetup.Utils.Utils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class HubEvents implements Listener {
    private Main pl = Main.pl;
    private GameManager game = GameManager.getInstance();
    private ScatterManager scatter = ScatterManager.getInstance();
    private WorldManager world = WorldManager.getInstance();
    private ScenarioManager scen = ScenarioManager.getInstance();

    public HubEvents() {
        pl.getServer().getPluginManager().registerEvents(this,pl);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Main.playerManager.playerJoinsTheGame(e.getPlayer());
        GamePlayer p = Main.playerManager.getUhcPlayer(e.getPlayer());
        e.setJoinMessage(Utils.chat(pl.lang.getConfig().getString("player-join")).replace("%player%", p.getName()));
        pl.boards.put(p.getName(), new FastBoard(p.getPlayer()));
        switch (game.getState()) {
            case STARTING -> {
                Utils.delay(1, () -> {
                p.getPlayer().setLevel(0);
                p.getPlayer().setExp(0.0f);
                p.getPlayer().setFoodLevel(20);
                p.getPlayer().getInventory().clear();
                p.getPlayer().setAbsorptionAmount(0);
                p.getPlayer().getActivePotionEffects().forEach(pe -> p.getPlayer().removePotionEffect(pe.getType()));
                scatter.scatter(p);
                });
            }
            case WAITING ->  {
                Utils.delay(1, () -> {
                    p.getPlayer().setLevel(0);
                    p.getPlayer().setExp(0.0f);
                    p.getPlayer().setFoodLevel(20);
                    p.getPlayer().getInventory().clear();
                    p.getPlayer().setGameMode(GameMode.SURVIVAL);
                    p.getPlayer().setBedSpawnLocation(world.getLobbyLocation());
                    p.getPlayer().teleport(world.getLobbyLocation());
                    p.getPlayer().getActivePotionEffects().forEach(pe -> p.getPlayer().removePotionEffect(pe.getType()));
                    lobbyItems(p.getPlayer());
                });
                if (Main.playerManager.getPlayerList().stream().filter(GamePlayer::isOnline).toList().size() >= pl.config.getConfig().getInt("players-to-start")) {
                    game.start();
                }
            }
            case STARTED, FINALIZED -> {
                Utils.delay(1, () -> {
                    p.getPlayer().setLevel(0);
                    p.getPlayer().setExp(0.0f);
                    p.getPlayer().setFoodLevel(20);
                    p.getPlayer().getInventory().clear();
                    p.getPlayer().setBedSpawnLocation(world.getLobbyLocation());
                    p.getPlayer().teleport(new Location(world.getMeetupWorld(),0,100,0));
                    p.getPlayer().setGameMode(GameMode.SPECTATOR);
                    p.setState(PlayerState.SPECTATING);
                    p.getPlayer().getActivePotionEffects().forEach(pe -> p.getPlayer().removePotionEffect(pe.getType()));
                });
            }
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        GamePlayer p = Main.playerManager.getUhcPlayer(e.getPlayer());
        Utils.delay(5, () -> {
        p.getPlayer().setLevel(0);
        p.getPlayer().setExp(0.0f);
        p.getPlayer().setFoodLevel(20);
        p.getPlayer().getInventory().clear();
        p.getPlayer().setBedSpawnLocation(world.getLobbyLocation());
        p.getPlayer().teleport(new Location(world.getMeetupWorld(),0,100,0));
        p.getPlayer().setGameMode(GameMode.SPECTATOR);
        p.setState(PlayerState.SPECTATING);
        p.getPlayer().getActivePotionEffects().forEach(pe -> p.getPlayer().removePotionEffect(pe.getType()));
        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        GamePlayer p = Main.playerManager.getUhcPlayer(e.getPlayer());
        Player player = e.getPlayer();
        FastBoard board = pl.boards.remove(p.getName());
        p.getPlayer().eject();
        if (board != null) board.delete();
        e.setQuitMessage(Utils.chat(pl.lang.getConfig().getString("player-quit").replace("%player%", p.getName())));
        if (game.getState() == GameState.STARTED) {
            if (p.isPlaying()) {
                p.setState(PlayerState.SPECTATING);
                Arrays.stream(player.getInventory().getContents()).filter(a -> a.getType() != Material.AIR).forEach(a -> player.getWorld().dropItemNaturally(player.getLocation(), a));
                Arrays.stream(player.getInventory().getArmorContents()).filter(a -> a.getType() != Material.AIR).forEach(a -> player.getWorld().dropItemNaturally(player.getLocation(), a));
                ItemStack offHand = player.getInventory().getItemInOffHand();
                if (offHand != null && offHand.getType() != Material.AIR) {
                    player.getWorld().dropItemNaturally(player.getLocation(), offHand);
                }
            }
            Main.playerManager.checkIfRemainingPlayers();
        }
    }

    @EventHandler
    public void onGamemodeChange(PlayerGameModeChangeEvent e) {
        GamePlayer p = Main.playerManager.getUhcPlayer(e.getPlayer());
        if (game.getState().equals(GameState.STARTED) && p.isPlaying() && e.getNewGameMode().equals(GameMode.SPECTATOR)) {
            p.setState(PlayerState.SPECTATING);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        switch (game.getState()) {
            case WAITING, STARTING, FINALIZED -> {
                if (!e.getPlayer().hasPermission("meetup.admin")) e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        switch (game.getState()) {
            case WAITING, STARTING -> {
                if (!e.getPlayer().hasPermission("meetup.admin")) e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBucket(PlayerBucketEmptyEvent e) {
        switch (game.getState()) {
            case WAITING, STARTING -> {
                if (!e.getPlayer().hasPermission("meetup.admin")) e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        switch (game.getState()) {
            case WAITING, STARTING, FINALIZED -> {
                e.setFoodLevel(20);
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        switch (game.getState()) {
            case WAITING, STARTING, FINALIZED -> e.setCancelled(true);
        }
    }

    private void lobbyItems(Player p) {
        if (p.hasPermission("meetup.admin")) p.getInventory().setItem(8, new ItemBuilder(Material.ENCHANTED_BOOK).name(Utils.chat("&cEdit Scenarios")).build());
        p.getInventory().addItem(new ItemBuilder(Material.BOOK).name(Utils.chat("&aEnabled Scenarios")).build());
        p.getInventory().setItem(4, new ItemBuilder(Material.DIAMOND).name(Utils.chat("&bStats")).build());
    }
}
