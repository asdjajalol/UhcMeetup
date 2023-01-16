package net.gonzq.uhcmeetup.Tasks;

import net.gonzq.uhcmeetup.Enums.GameState;
import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.Managers.GameManager;
import net.gonzq.uhcmeetup.Managers.WorldManager;
import net.gonzq.uhcmeetup.players.GamePlayer;
import net.gonzq.uhcmeetup.Utils.Utils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.scheduler.BukkitRunnable;

public class BorderActionTask extends BukkitRunnable {
    private Main pl = Main.pl;
    private String msg;
    private GameManager game = GameManager.getInstance();

    public BorderActionTask() {
        msg = pl.lang.getConfig().getString("border-actionbar");
    }


    @Override
    public void run() {
        game.setTimeElapsed(game.getTimeElapsed() + 1);
        Main.playerManager.getPlayerList().stream().filter(GamePlayer::isOnline).forEach(p -> p.getPlayer()
                .spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Utils.chat(msg.replace("%prefix%", pl.borderPrefix).replace("%size%", String.valueOf(WorldManager.getInstance().getBorder()))))));
        if (GameManager.getInstance().getState().equals(GameState.FINALIZED)) cancel();
    }
}
