package net.gonzq.uhcmeetup.Guis;

import net.gonzq.uhcmeetup.FastInv.FastInv;
import net.gonzq.uhcmeetup.FastInv.ItemBuilder;
import net.gonzq.uhcmeetup.Managers.StatsManager;
import net.gonzq.uhcmeetup.Utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;


public class TopStatsGui extends FastInv {

    public TopStatsGui(Player p) {
        super(9, "Top Stats");
        addItem(new ItemBuilder(Material.GOLD_BLOCK).name(Utils.chat("&aTop Wins:")).lore(StatsManager.WINS.getTop(p)).build());
        addItem(new ItemBuilder(Material.SKELETON_SKULL).name(Utils.chat("&cTop Deaths:")).lore(StatsManager.DEATHS.getTop(p)).build());
        addItem(new ItemBuilder(Material.IRON_SWORD).name(Utils.chat("&cTop Kills:")).lore(StatsManager.KILLS.getTop(p)).build());
        addItem(new ItemBuilder(Material.GOLDEN_APPLE).name(Utils.chat("&6Top Gapps Eaten:")).lore(StatsManager.GAPPS.getTop(p)).build());
        addItem(new ItemBuilder(Material.GRASS_BLOCK).name(Utils.chat("&aTop Games Played:")).lore(StatsManager.PLAYED.getTop(p)).build());
        addClickHandler(e -> e.setCancelled(true));
    }
}
