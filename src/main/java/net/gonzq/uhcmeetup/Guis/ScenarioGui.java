package net.gonzq.uhcmeetup.Guis;

import net.gonzq.uhcmeetup.Enums.GameState;
import net.gonzq.uhcmeetup.FastInv.FastInv;
import net.gonzq.uhcmeetup.FastInv.ItemBuilder;
import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.Managers.GameManager;
import net.gonzq.uhcmeetup.Managers.ScenarioManager;
import net.gonzq.uhcmeetup.Utils.Utils;
import org.bukkit.entity.Player;

public class ScenarioGui extends FastInv {

    private Main pl = Main.pl;
    public ScenarioGui() {
        super(18, "Scenarios");
        ScenarioManager.getInstance().getScenarios().forEach(s -> addItem(new ItemBuilder(s.getIcon()).name(Utils.chat(pl.config.getConfig().getString("scenarios-gui.scenarios-name").replace("%name%", s.getName()))).lore(s.getDescription()).build(),
                e -> {
                    Player p = (Player) e.getWhoClicked();
                    e.setCancelled(true);
                    if (p.hasPermission("meetup.scenarios") && !GameManager.getInstance().getState().equals(GameState.STARTED) &&
                            !GameManager.getInstance().getVoteScenarios().canVote()) {
                        if (s.isEnabled()) {
                            s.disable();
                            if (pl.config.getConfig().getStringList("vote-system.scenarios-to-vote").contains(s.getName())) {
                                GameManager.getInstance().getVoteScenarios().scensToVote.put(s, 0);
                            }
                        } else {
                            s.enable();
                            if (pl.config.getConfig().getStringList("vote-system.scenarios-to-vote").contains(s.getName())) {
                                GameManager.getInstance().getVoteScenarios().scensToVote.remove(s);
                            }
                        }
                    }
                }));
       }
}
