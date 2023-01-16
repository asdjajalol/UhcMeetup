package net.gonzq.uhcmeetup.Commands;

import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.Managers.WorldManager;
import net.gonzq.uhcmeetup.Utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MeetupCommands implements CommandExecutor, TabExecutor {
    private Main pl = Main.pl;

    public MeetupCommands() {
        pl.getCommand("uhcmeetup").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(Utils.chat("&aUHC Meetup"));
            sender.sendMessage(Utils.chat("&9Discord/Author: &b! Gonzq#4451"));
            if (sender.hasPermission("meetup.admin")) {
                sender.sendMessage(Utils.chat("Usage: &9/uhcmeetup help"));
            }
            return true;
        }
        if (sender.hasPermission("meetup.admin")) {
            if (args[0].equalsIgnoreCase("help")) {
                sender.sendMessage(Utils.chat("&8- &9/uhcmeetup setlobby &8| &7Sets the Lobby"));
                sender.sendMessage(Utils.chat("&8- &9/uhcmeetup reload &8| &7Reloads all configs"));
                return true;
            }
            if (args[0].equalsIgnoreCase("setlobby") && sender instanceof Player p) {
                WorldManager.getInstance().setLobbyLocation(p.getLocation());
                p.sendMessage(Utils.chat(pl.prefix + "&aLobby has been setted"));
            }
            if (args[0].equalsIgnoreCase("reload")) {
                pl.config.reloadConfig();
                pl.board.reloadConfig();
                pl.lang.reloadConfig();
                pl.kit.reloadConfig();
                sender.sendMessage(Utils.chat("&aAll configs has been reloaded"));
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1 && sender.hasPermission("meetup.admin")) {
            list.add("setlobby");
            list.add("reload");
            return list;
        }
        return null;
    }
}
