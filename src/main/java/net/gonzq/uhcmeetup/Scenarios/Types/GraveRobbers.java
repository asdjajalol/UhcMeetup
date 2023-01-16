package net.gonzq.uhcmeetup.Scenarios.Types;

import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.Managers.ScenarioManager;
import net.gonzq.uhcmeetup.Managers.WorldManager;
import net.gonzq.uhcmeetup.Scenarios.Scenario;
import net.gonzq.uhcmeetup.Utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Rotatable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class GraveRobbers extends Scenario implements Listener {
    private boolean enabled = false;

    public GraveRobbers() {
        super("GraveRobbers", new ItemStack(Material.GRAVEL), "&7&7When killing someone, their grave comes out with their items.");
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        ScenarioManager scen = ScenarioManager.getInstance();
        Player p = e.getEntity();
        Location loc = p.getLocation();
        if (p.getWorld().equals(WorldManager.getInstance().getMeetupWorld())) {
            if (Math.signum(e.getEntity().getLocation().getBlockY()) == -1.0 || Math.signum(e.getEntity().getLocation().getBlockY()) == 0.0)
                return;
            Material grava = Material.GRAVEL;
            loc.clone().add(0, +1, 0).getBlock().setType(grava);
            loc.clone().add(-1, +1, 0).getBlock().setType(grava);
            loc.clone().add(-2, +1, 0).getBlock().setType(Material.COBBLESTONE); //CARTEL ACA
            loc.clone().add(-2, +1, -1).getBlock().setType(Material.MOSSY_COBBLESTONE);
            loc.clone().add(-1, +1, -1).getBlock().setType(Material.COBBLESTONE);
            loc.clone().add(0, +1, -1).getBlock().setType(Material.MOSSY_COBBLESTONE);
            loc.clone().add(+1, +1, -1).getBlock().setType(Material.COBBLESTONE);
            loc.clone().add(+1, +1, 0).getBlock().setType(Material.COBBLESTONE);
            loc.clone().add(+1, +1, +1).getBlock().setType(Material.COBBLESTONE);
            loc.clone().add(0, +1, +1).getBlock().setType(Material.MOSSY_COBBLESTONE);
            loc.clone().add(-1, +1, +1).getBlock().setType(Material.COBBLESTONE);
            loc.clone().add(-2, +1, +1).getBlock().setType(Material.MOSSY_COBBLESTONE);
            Block s = loc.clone().add(-2, +2, 0).getBlock();
            s.setType(Material.WARPED_SIGN);
            Sign sign = (Sign) s.getState();
            Rotatable dir = (Rotatable) sign.getBlockData();
            dir.setRotation(BlockFace.EAST);
            sign.setBlockData(dir);
            sign.update(true);
            sign.setLine(0, Utils.chat("&cR.I.P"));
            sign.setLine(1, p.getName());
            if (p.getKiller() != null) {
                Player k = p.getKiller();
                sign.setLine(2, Utils.chat("&cKilled by"));
                sign.setLine(3, k.getName());
            }
            sign.update();


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
