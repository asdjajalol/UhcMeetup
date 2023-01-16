package net.gonzq.uhcmeetup.players;

import net.gonzq.uhcmeetup.Enums.PlayerState;
import net.gonzq.uhcmeetup.FastInv.ItemBuilder;
import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.Managers.ScenarioManager;
import net.gonzq.uhcmeetup.Managers.StatsManager;
import net.gonzq.uhcmeetup.Utils.Utils;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntityLiving;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.animal.EntityPig;
import net.minecraft.world.entity.animal.horse.EntityHorse;
import net.minecraft.world.level.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.HashMap;
import java.util.UUID;

public class GamePlayer {
    private final UUID uid;
    private final String name;

    private PlayerState state;
    private int kills;
    private boolean isVotedScen;

    private PlayerFiles file;
    //
    public GamePlayer(UUID uid, String name) {
        this.uid = uid;
        this.name = name;

        setState(PlayerState.WAITING);
        kills = 0;
        isVotedScen = false;
        file = PlayerFiles.get(uid);
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uid);
    }

    public boolean isVotedScen() {
        return isVotedScen;
    }
    public void setVotedScen(boolean b) {
        isVotedScen = b;
    }

    public PlayerFiles getFile() {
        return file;
    }

    public boolean isOnline() {
        return getPlayer() != null;
    }

    public String getName() {
        return name;
    }

    public UUID getUid() {
        return uid;
    }

    public PlayerState getState() {
        return state;
    }

    public boolean isPlaying() {
        return state == PlayerState.PLAYING;
    }


    public boolean isSpectating() {
        return state == PlayerState.SPECTATING;
    }

    public void setState(PlayerState state) {
        this.state = state;
    }

    public int getKills() {
        return kills;
    }

    public void addKill() {
        StatsManager.KILLS.add(getPlayer());
        kills++;
    }

    public void setKit() {
        FileConfiguration c = Main.pl.kit.getConfig();
        if (isOnline()) {
            String weaponKey = "kits.weapons";
            String armorKey = "kits.armor";
            PlayerInventory inv = getPlayer().getInventory();
            inv.clear();
            inv.setItemInOffHand(new ItemStack(Material.SHIELD));
            // Helmet
            ItemStack helmet = null;
            int hNumber = Utils.getRandomInt(c.getConfigurationSection(armorKey + ".helmets").getKeys(false).size());
            helmet = new ItemStack(Material.valueOf(c.getString(armorKey + ".helmets." + hNumber + ".item")));
            if (!c.getString(armorKey + ".helmets." + hNumber + ".enchants").equalsIgnoreCase("none")) {
                for (String s : c.getString(armorKey + ".helmets." + hNumber + ".enchants").split(";")) {
                    String[] enchant = s.split("-");
                    helmet.addEnchantment(Enchantment.getByName(enchant[0]), Integer.parseInt(enchant[1]));
                }
            }
            inv.setHelmet(helmet);

            // Chestplate
            ItemStack chestplate = null;
            int cNumber = Utils.getRandomInt(c.getConfigurationSection(armorKey + ".chestplates").getKeys(false).size());
            chestplate = new ItemStack(Material.valueOf(c.getString(armorKey + ".chestplates." + cNumber + ".item")));
            if (!c.getString(armorKey + ".chestplates." + cNumber + ".enchants").equalsIgnoreCase("none")) {
                for (String s : c.getString(armorKey + ".chestplates." + cNumber + ".enchants").split(";")) {
                    String[] enchant = s.split("-");
                    chestplate.addEnchantment(Enchantment.getByName(enchant[0]), Integer.parseInt(enchant[1]));
                }
            }
            inv.setChestplate(chestplate);

            // Leggings
            ItemStack leggings = null;
            int lNumber = Utils.getRandomInt(c.getConfigurationSection(armorKey + ".leggings").getKeys(false).size());
            leggings = new ItemStack(Material.valueOf(c.getString(armorKey + ".leggings." + lNumber + ".item")));
            if (!c.getString(armorKey + ".leggings." + lNumber + ".enchants").equalsIgnoreCase("none")) {
                for (String s : c.getString(armorKey + ".leggings." + lNumber + ".enchants").split(";")) {
                    String[] enchant = s.split("-");
                    leggings.addEnchantment(Enchantment.getByName(enchant[0]), Integer.parseInt(enchant[1]));
                }
            }
            inv.setLeggings(leggings);

            // Boots
            ItemStack boots = null;
            int bNumber = Utils.getRandomInt(c.getConfigurationSection(armorKey + ".boots").getKeys(false).size());
            boots = new ItemStack(Material.valueOf(c.getString(armorKey + ".boots." + bNumber + ".item")));
            if (!c.getString(armorKey + ".boots." + bNumber + ".enchants").equalsIgnoreCase("none")) {
                for (String s : c.getString(armorKey + ".boots." + bNumber + ".enchants").split(";")) {
                    String[] enchant = s.split("-");
                    boots.addEnchantment(Enchantment.getByName(enchant[0]), Integer.parseInt(enchant[1]));
                }
            }
            inv.setBoots(boots);

            // Swords
            ItemStack sword = null;
            int swordNumber = Utils.getRandomInt(c.getConfigurationSection(weaponKey + ".swords").getKeys(false).size());
            sword = new ItemStack(Material.valueOf(c.getString(weaponKey + ".swords." + swordNumber + ".item")));
            if (!c.getString(weaponKey + ".swords." + swordNumber + ".enchants").equalsIgnoreCase("none")) {
                for (String s : c.getString(weaponKey + ".swords." + swordNumber + ".enchants").split(";")) {
                    String[] enchant = s.split("-");
                    sword.addEnchantment(Enchantment.getByName(enchant[0]), Integer.parseInt(enchant[1]));
                }
            }
            inv.addItem(sword);

            // Axe
            ItemStack axe = null;
            int axeNumber = Utils.getRandomInt(c.getConfigurationSection(weaponKey + ".axe").getKeys(false).size());
            axe = new ItemStack(Material.valueOf(c.getString(weaponKey + ".axe." + axeNumber + ".item")));
            if (!c.getString(weaponKey + ".axe." + axeNumber + ".enchants").equalsIgnoreCase("none")) {
                for (String s : c.getString(weaponKey + ".axe." + axeNumber + ".enchants").split(";")) {
                    String[] enchant = s.split("-");
                    axe.addEnchantment(Enchantment.getByName(enchant[0]), Integer.parseInt(enchant[1]));
                }
            }
            inv.addItem(axe);

            // Bow
            ItemStack bow = null;
            int bowNumber = Utils.getRandomInt(c.getConfigurationSection(weaponKey + ".bow").getKeys(false).size() - 2);
            bow = new ItemStack(Material.valueOf(c.getString(weaponKey + ".bow.item")));
            if (!c.getString(weaponKey + ".bow." + bowNumber + ".enchants").equalsIgnoreCase("none")) {
                for (String s : c.getString(weaponKey + ".bow." + bowNumber + ".enchants").split(";")) {
                    String[] enchant = s.split("-");
                    bow.addEnchantment(Enchantment.getByName(enchant[0]), Integer.parseInt(enchant[1]));
                }
            }
            inv.addItem(bow);
            inv.addItem(new ItemStack(Material.ARROW, Utils.getRandomInt(c.getInt(weaponKey + ".bow.arrows.min"), c.getInt(weaponKey + ".bow.arrows.max"))));

            // Healing
            inv.addItem(new ItemStack(Material.GOLDEN_APPLE, Utils.getRandomInt(c.getInt("kits.healing.golden-apples.min"), c.getInt("kits.healing.golden-apples.max"))));

            inv.addItem(new ItemBuilder(Utils.goldenHead()).amount(c.getInt("kits.healing.golden-heads.min",
                    c.getInt("kits.healing.golden-heads.max"))).build());

            // Cobwebs
            if (Utils.getRandomInt(100) - c.getInt("kits.cobwebs.chance") <= 1) {
                inv.addItem(new ItemStack(Material.COBWEB, Utils.getRandomInt(c.getInt("kits.cobwebs.min"), c.getInt("kits.cobwebs.max"))));
            }

            // Fire Res
            if (Utils.getRandomInt(100) - c.getInt("kits.potions.fire-resistance-chance") <= 1) {
                ItemStack potion = new ItemStack(Material.POTION, 1);
                PotionMeta meta = (PotionMeta) potion.getItemMeta();
                meta.setBasePotionData(new PotionData(PotionType.FIRE_RESISTANCE));
                potion.setItemMeta(meta);
                inv.addItem(potion);
            }

            // Speed
            if (Utils.getRandomInt(100) - c.getInt("kits.potions.speed-chance") <= 1) {
                ItemStack potion = new ItemStack(Material.POTION, 1);
                PotionMeta meta = (PotionMeta) potion.getItemMeta();
                meta.setBasePotionData(new PotionData(PotionType.SPEED));
                potion.setItemMeta(meta);
                inv.addItem(potion);
            }

            if (Utils.getRandomInt(100) - c.getInt("kits.potions.poison-chance") <= 1) {
                ItemStack potion = new ItemStack(Material.SPLASH_POTION, 1);
                PotionMeta meta = (PotionMeta) potion.getItemMeta();
                meta.setBasePotionData(new PotionData(PotionType.POISON));
                potion.setItemMeta(meta);
                inv.addItem(potion);
            }

            ItemStack miscellaneous = null;
            int miscNumber = Utils.getRandomInt(c.getConfigurationSection("kits.miscellaneous").getKeys(false).size());
            if (miscNumber > 0) {
                miscellaneous = new ItemStack(Material.valueOf(c.getString("kits.miscellaneous." + miscNumber + ".item")));
                if (!c.getString("kits.miscellaneous." + miscNumber + ".enchants").equalsIgnoreCase("none")) {
                    for (String s : c.getString("kits.miscellaneous." + miscNumber + ".enchants").split(";")) {
                        String[] enchant = s.split("-");
                        miscellaneous.addEnchantment(Enchantment.getByName(enchant[0]), Integer.parseInt(enchant[1]));
                    }
                }
                inv.addItem(miscellaneous);
            }
            inv.addItem(new ItemStack(Material.LAVA_BUCKET));
            inv.addItem(new ItemStack(Material.WATER_BUCKET));
            inv.addItem(new ItemStack(Material.COBBLESTONE,64));
            inv.addItem(new ItemStack(Material.COOKED_BEEF,32));
            inv.addItem(new ItemStack(Material.OAK_PLANKS,64));
            inv.addItem(new ItemStack(Material.ANVIL));
            inv.addItem(new ItemStack(Material.DIAMOND_PICKAXE));
            inv.addItem(new ItemStack(Material.LAPIS_LAZULI,64));
            inv.addItem(new ItemStack(Material.CRAFTING_TABLE));
            inv.addItem(new ItemStack(Material.ENCHANTING_TABLE));
            inv.addItem(new ItemStack(Material.EXPERIENCE_BOTTLE,c.getInt("xpbottles")));
            if (c.getInt("kits.books") > 0) inv.addItem(new ItemStack(Material.BOOK, c.getInt("kits.books")));
            if (ScenarioManager.getInstance().getScenario("HeavyPockets").isEnabled()) inv.addItem(new ItemStack(Material.SMITHING_TABLE));
        }
    }
}
