package net.gonzq.uhcmeetup;

import net.gonzq.uhcmeetup.FastBoard.FastBoard;
import net.gonzq.uhcmeetup.FastInv.FastInvManager;
import net.gonzq.uhcmeetup.Files.BoardFile;
import net.gonzq.uhcmeetup.Files.ConfigFile;
import net.gonzq.uhcmeetup.Files.KitFile;
import net.gonzq.uhcmeetup.Files.LangFile;
import net.gonzq.uhcmeetup.Managers.PlayerManager;
import net.gonzq.uhcmeetup.Managers.WorldManager;
import net.gonzq.uhcmeetup.Tasks.ScoreboardTask;
import net.gonzq.uhcmeetup.Utils.Placeholders;
import net.gonzq.uhcmeetup.Utils.UpdateChecker;
import net.gonzq.uhcmeetup.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;

import java.util.HashMap;
import java.util.Map;

public final class Main extends JavaPlugin {

    public static Main pl;

    public BoardFile board;
    public ConfigFile config;
    public KitFile kit;
    public LangFile lang;

    public static PlayerManager playerManager;

    public String prefix;
    public String borderPrefix;

    public Map<String, FastBoard> boards = new HashMap<>();

    public boolean placeholderapi;

    @Override
    public void onEnable() {
        // Plugin startup logic
        pl = this;
        board = new BoardFile(this);
        config = new ConfigFile(this);
        kit = new KitFile(this);
        lang = new LangFile(this);

        prefix = Utils.chat(lang.getConfig().getString("prefix"));
        borderPrefix = Utils.chat(lang.getConfig().getString("border-prefix"));


        playerManager = new PlayerManager();

        WorldManager.getInstance().createWorlds();

        FastInvManager.register(this);

        new UpdateChecker(this, 107342).getVersion(version -> {
            if (!getDescription().getVersion().equals(version)) {
                getLogger().warning("There is a new version of UHC Meetup available!");
                getLogger().warning("Your Version: 1.0");
                getLogger().warning("New Version: " + version);
                getLogger().warning("");
                getLogger().warning("DownloadLink: https://www.spigotmc.org/resources/");
                getLogger().warning("DonationLink: https://paypal.me/gonzq1");
            }
        });

        new ScoreboardTask().runTaskTimer(this,0,20);

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
            placeholderapi = false;
        } else {
            placeholderapi = true;
            new Placeholders().register();
        }

        Utils.setObjectives();
        Utils.registerCommandsAndEvents();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getScoreboardManager().getMainScoreboard().getObjectives().forEach(Objective::unregister);
        WorldManager.getInstance().deleteWorld();
    }
}
