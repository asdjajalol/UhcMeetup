package net.gonzq.uhcmeetup.Guis;

import net.gonzq.uhcmeetup.FastInv.FastInv;
import net.gonzq.uhcmeetup.FastInv.ItemBuilder;
import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.Managers.StatsManager;
import net.gonzq.uhcmeetup.Utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class StatsGui extends FastInv {

    private Main pl = Main.pl;

    public StatsGui(Player p) {
        super(9, "Stats");
        addItem(new ItemBuilder(Material.GOLD_BLOCK).name(Utils.chat("&aWins: " + StatsManager.WINS.getValue(p))).build());
        addItem(new ItemBuilder(Material.SKELETON_SKULL).name(Utils.chat("&cDeaths: " + StatsManager.DEATHS.getValue(p))).build());
        addItem(new ItemBuilder(Material.IRON_SWORD).name(Utils.chat("&cKills: " + StatsManager.KILLS.getValue(p))).build());
        addItem(new ItemBuilder(Material.GOLDEN_APPLE).name(Utils.chat("&6Gapps Eaten: " + StatsManager.GAPPS.getValue(p))).build());
        addItem(new ItemBuilder(Material.GRASS_BLOCK).name(Utils.chat("&aGames Played: " + StatsManager.PLAYED.getValue(p))).build());

        addOpenHandler(e -> e.getPlayer().sendMessage(Utils.chat(pl.prefix + "Opening " + p.getName() + "'s stats...")));
        addClickHandler(e -> e.setCancelled(true));
    }
}
