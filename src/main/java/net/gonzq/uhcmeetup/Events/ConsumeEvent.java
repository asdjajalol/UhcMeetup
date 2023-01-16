package net.gonzq.uhcmeetup.Events;

import net.gonzq.uhcmeetup.Enums.GameState;
import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.Managers.GameManager;
import net.gonzq.uhcmeetup.Managers.StatsManager;
import net.gonzq.uhcmeetup.players.GamePlayer;
import net.gonzq.uhcmeetup.Utils.Utils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ConsumeEvent implements Listener {
    private Main pl = Main.pl;
    private GameManager game = GameManager.getInstance();
    public ConsumeEvent() {
        pl.getServer().getPluginManager().registerEvents(this,pl);
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent e) {
        GamePlayer p = Main.playerManager.getUhcPlayer(e.getPlayer());
        if (game.getState().equals(GameState.STARTED)) {
            if (e.getItem().getType().equals(Material.GOLDEN_APPLE)) {
                StatsManager.GAPPS.add(p.getPlayer());
            }
        }
        if (e.getItem().equals(Utils.goldenHead())) {
            p.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,200,1));
        }
    }
}
