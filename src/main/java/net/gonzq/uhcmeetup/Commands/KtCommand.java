package net.gonzq.uhcmeetup.Commands;

import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.Utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KtCommand implements CommandExecutor {
    private Main pl = Main.pl;

    public KtCommand() {
        pl.getCommand("kt").setExecutor(this);
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        HashMap<String, Integer> hash = new HashMap<>();
        Main.playerManager.getPlayerList().forEach(p -> hash.put(p.getName(), p.getKills()));
        List<Map.Entry<String, Integer>> entry = hash.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(10).toList();

        sender.sendMessage(Utils.chat(pl.prefix + "Top Kills:"));
        entry.forEach(e -> sender.sendMessage(Utils.chat(pl.lang.getConfig().getString("kt-msg"))
                .replace("%player%", e.getKey()).replace("%kills%", String.valueOf(e.getValue()))));
        return false;
    }
}
