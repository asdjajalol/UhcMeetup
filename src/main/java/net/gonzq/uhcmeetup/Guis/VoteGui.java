package net.gonzq.uhcmeetup.Guis;

import net.gonzq.uhcmeetup.FastInv.FastInv;
import net.gonzq.uhcmeetup.FastInv.ItemBuilder;
import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.Managers.GameManager;
import net.gonzq.uhcmeetup.Managers.ScenarioManager;
import net.gonzq.uhcmeetup.players.GamePlayer;
import net.gonzq.uhcmeetup.Utils.Utils;
import org.bukkit.entity.Player;

public class VoteGui extends FastInv {

    Main pl = Main.pl;
    public VoteGui() {
        super(18,Utils.chat(Main.pl.lang.getConfig().getString("vote-gui-title")));
        GameManager.getInstance().getVoteScenarios().scensToVote.keySet().forEach(scen -> {
            GameManager game = GameManager.getInstance();
            if (scen != null) {
                addItem(new ItemBuilder(scen.getIcon()).name(Utils.chat("&9" + scen.getName()))
                        .lore(Utils.chat("&7Votes: &b" + game.getVoteScenarios().getVotes(scen))).build(), e -> {
                    e.setCancelled(true);
                    GamePlayer p = Main.playerManager.getUhcPlayer((Player) e.getWhoClicked());
                    game.getVoteScenarios().setVote(p, scen);
                    p.getPlayer().getOpenInventory().close();
                });
            }
        });
        addOpenHandler(e -> e.getPlayer().sendMessage(Utils.chat(pl.prefix + "Opening Vote Menu...")));
    }
}
