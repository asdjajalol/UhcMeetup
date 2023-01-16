package net.gonzq.uhcmeetup.Tasks;

import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.Managers.GameManager;
import net.gonzq.uhcmeetup.Managers.WorldManager;
import net.gonzq.uhcmeetup.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class BorderTask extends BukkitRunnable {
    private int time;
    private int wbtime;
    private Main pl = Main.pl;
    private GameManager game = GameManager.getInstance();

    public BorderTask() {
        time = 60;
        wbtime = pl.config.getConfig().getInt("worldborder-time");
    }

    @Override
    public void run() {
        if (time == 60) {
            Bukkit.broadcastMessage(Utils.chat(pl.borderPrefix + pl.lang.getConfig().getString("border-shrink-one-minute")));
        }
        if (time == 0) {
            Bukkit.broadcastMessage(Utils.chat(pl.borderPrefix + pl.lang.getConfig().getString("border-shrink")));
            WorldManager.getInstance().getMeetupWorld().getWorldBorder().setSize(200,wbtime);
            new BorderActionTask().runTaskTimer(pl,0,20);
            cancel();
        }
        game.setTimeElapsed(game.getTimeElapsed() + 1);
        time--;
    }
}
