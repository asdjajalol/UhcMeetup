package net.gonzq.uhcmeetup.Commands;

import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.Managers.GameManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ForceStartCommand implements CommandExecutor {
    private Main pl = Main.pl;

    public ForceStartCommand() {
        pl.getCommand("forcestart").setExecutor(this);
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("meetup.forcestart")) {
            GameManager.getInstance().start();
        }
        return false;
    }
}
