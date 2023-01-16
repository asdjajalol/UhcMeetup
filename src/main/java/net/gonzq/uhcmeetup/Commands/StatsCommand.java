package net.gonzq.uhcmeetup.Commands;

import net.gonzq.uhcmeetup.Guis.StatsGui;
import net.gonzq.uhcmeetup.Guis.TopStatsGui;
import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StatsCommand implements CommandExecutor {
    private Main pl = Main.pl;

    public StatsCommand() {
        pl.getCommand("topstats").setExecutor(this);
        pl.getCommand("stats").setExecutor(this);
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            if (cmd.getName().equalsIgnoreCase("topstats")) {
                new TopStatsGui(p).open(p);
            } else if (cmd.getName().equalsIgnoreCase("stats")) {
                if (args.length == 0) {
                    new StatsGui(p).open(p);
                } else if (args.length == 1) {
                    Player t = Bukkit.getPlayer(args[0]);
                    if (t == null) {
                        p.sendMessage(Utils.chat(pl.prefix + "&cThis player isn't online"));
                        return true;
                    }
                    new StatsGui(t).open(p);
                }
            }
        }
        return false;
    }
}
