package net.gonzq.uhcmeetup.Commands;

import net.gonzq.uhcmeetup.Guis.ScenGui;
import net.gonzq.uhcmeetup.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ScenarioCommands implements CommandExecutor {
    private Main pl = Main.pl;

    public ScenarioCommands() {
        pl.getCommand("scenarios").setExecutor(this);
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            new ScenGui().open(p);
        }
        return false;
    }
}
