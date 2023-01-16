package net.gonzq.uhcmeetup.Guis;

import net.gonzq.uhcmeetup.FastInv.FastInv;
import net.gonzq.uhcmeetup.FastInv.ItemBuilder;
import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.Managers.ScenarioManager;
import net.gonzq.uhcmeetup.Utils.Utils;

public class ScenGui extends FastInv {

    public ScenGui() {
        super(18, "Scenarios");
        ScenarioManager.getInstance().getEnabledScenarios().forEach(s -> addItem(new ItemBuilder(s.getIcon()).name(Utils.chat(Main.pl.config.getConfig().getString("scenarios-gui.scenarios-name").replace("%name%", s.getName()))).lore(s.getDescription()).build(),
                e -> e.setCancelled(true)));
    }
}
