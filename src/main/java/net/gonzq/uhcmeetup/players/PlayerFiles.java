package net.gonzq.uhcmeetup.players;

import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerFiles {
    FileConfiguration config;
    File file;

    public static PlayerFiles get(UUID uid) {
        return new PlayerFiles(uid);
    }

    public static List<FileConfiguration> getConfigs() {
        List<FileConfiguration> l = new ArrayList<>();
        Main.playerManager.getPlayerList().forEach(p -> l.add(p.getFile().c()));
        return l;
    }

    private PlayerFiles(UUID uid) {
        Main pl = Main.pl;
        File folder = new File(pl.getDataFolder() + File.separator + "players" + File.separator);
        if (!folder.exists()) {
            folder.mkdir();
        }
        file = new File(folder, uid.toString() + ".yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                pl.getLogger().severe(Utils.chat("Could not create " + uid + ".yml!"));
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
        config.set("username", Bukkit.getOfflinePlayer(uid).getName());
        save();
    }

    public FileConfiguration c() {
        return config;
    }

    public void save() {
        try {
            config.save(file);
        } catch (Exception e) {
            Main.pl.getLogger().severe(ChatColor.RED + "Could not save " + file.getName() + "!");
        }
    }
}
