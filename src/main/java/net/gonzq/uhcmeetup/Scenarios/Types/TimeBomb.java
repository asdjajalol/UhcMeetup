package net.gonzq.uhcmeetup.Scenarios.Types;

import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.Managers.ScenarioManager;
import net.gonzq.uhcmeetup.Managers.WorldManager;
import net.gonzq.uhcmeetup.Scenarios.Scenario;
import net.gonzq.uhcmeetup.Utils.Utils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class TimeBomb extends Scenario implements Listener {
    private boolean enabled = false;

    public TimeBomb() {
        super("TimeBomb", new ItemStack(Material.TNT), "&7When someone dies a chest appears with their loot",
                "&7that explodes after 30 seconds");
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        ScenarioManager scen = ScenarioManager.getInstance();
        Player p = e.getEntity();
        if (p.getWorld().equals(WorldManager.getInstance().getMeetupWorld())) {
            Location loc = p.getLocation();
            if (Math.signum(e.getEntity().getLocation().getBlockY()) == -1.0 || Math.signum(e.getEntity().getLocation().getBlockY()) == 0.0)
                return;

            Block leftSide = loc.getBlock();
            Block rightSide = loc.clone().add(-1, 0, 0).getBlock();

            if (rightSide.getRelative(BlockFace.UP).getType() == Material.BEDROCK) {
                rightSide.getRelative(BlockFace.UP).setType(Material.AIR);
            }
            if (leftSide.getRelative(BlockFace.UP).getType() == Material.BEDROCK) {
                leftSide.getRelative(BlockFace.UP).setType(Material.AIR);
            }

            leftSide.setType(Material.CHEST);
            rightSide.setType(Material.CHEST);

            BlockData leftData = leftSide.getBlockData();
            ((Directional) leftData).setFacing(BlockFace.NORTH);
            leftSide.setBlockData(leftData);

            org.bukkit.block.data.type.Chest chestDataLeft = (org.bukkit.block.data.type.Chest) leftData;
            chestDataLeft.setType(org.bukkit.block.data.type.Chest.Type.RIGHT);
            leftSide.setBlockData(chestDataLeft);

            Chest leftChest = (Chest) leftSide.getState();

            BlockData rightData = rightSide.getBlockData();
            ((Directional) rightData).setFacing(BlockFace.NORTH);
            rightSide.setBlockData(rightData);

            org.bukkit.block.data.type.Chest chestDataRight = (org.bukkit.block.data.type.Chest) rightData;
            chestDataRight.setType(org.bukkit.block.data.type.Chest.Type.LEFT);
            rightSide.setBlockData(chestDataRight);

            Chest rightChest = (Chest) rightSide.getState();

            for (int i = 0; i < e.getDrops().size(); i++) {
                ItemStack item = e.getDrops().get(i);
                if (item == null || item.getType() == Material.AIR) {
                    continue;
                }

                if (i < 27) {
                    leftChest.getInventory().addItem(item);
                } else {
                    rightChest.getInventory().addItem(item);
                }
            }

            if (scen.getScenario("GoldenRetriever").isEnabled()) {


                if (e.getDrops().size() + 1 <= 27) {
                    leftChest.getInventory().addItem(Utils.goldenHead());
                } else {
                    rightChest.getInventory().addItem(Utils.goldenHead());
                }
            }

            if (scen.getScenario("HeavyPockets").isEnabled()) {
                ItemStack a = new ItemStack(Material.NETHERITE_SCRAP, 2);

                if (e.getDrops().size() + 2 <= 27) {
                    leftChest.getInventory().addItem(a);
                } else {
                    rightChest.getInventory().addItem(a);
                }
            }

            e.getDrops().clear();

            final ArmorStand stand = p.getWorld().spawn(leftChest.getLocation().clone().add(0, 1, 0.5), ArmorStand.class);

            stand.setCustomNameVisible(true);
            stand.setSmall(true);
            stand.setGravity(false);
            stand.setVisible(false);

            stand.setMarker(true);

            new BukkitRunnable() {
                private int time = 30;

                public void run() {
                    time--;

                    if (time == 0) {
                        Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "TimeBomb" + ChatColor.DARK_GRAY + "] " + "§a" + p.getName() + "'s §fcorpse has exploded!");
                        leftChest.getInventory().clear();
                        leftSide.setType(Material.AIR);
                        rightSide.setType(Material.AIR);
                        loc.getWorld().createExplosion(loc.getBlockX() + 0.5, loc.getBlockY() + 0.5, loc.getBlockZ() + 0.5, 5, false, true);
                        loc.getWorld().strikeLightning(loc); //actually kill the items with the lightning LOLLL!!!!!
                        stand.remove();
                        cancel();
                    } else if (time == 1) {
                        stand.setCustomName("§4" + time + "s");
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.playSound(stand.getLocation(), Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE, 1, 1);
                        }
                    } else if (time == 2) {
                        stand.setCustomName("§c" + time + "s");
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.playSound(stand.getLocation(), Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE, 2, 2);
                        }
                    } else if (time == 3) {
                        stand.setCustomName("§6" + time + "s");
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.playSound(stand.getLocation(), Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE, 3, 3);
                        }
                    } else if (time <= 15) {
                        stand.setCustomName("§e" + time + "s");
                    } else {
                        stand.setCustomName("§a" + time + "s");
                    }
                }
            }.runTaskTimer(Main.pl, 0, 20);
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
