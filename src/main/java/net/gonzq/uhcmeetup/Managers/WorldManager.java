package net.gonzq.uhcmeetup.Managers;

import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.Utils.Utils;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class WorldManager {

    Main pl = Main.pl;
    private static WorldManager instace = new WorldManager();

    public static WorldManager getInstance() {
        return instace;
    }

    public World getMeetupWorld() {
        return Bukkit.getWorld(Main.pl.config.getConfig().getString("meetup-world"));
    }
    public World getLobby() {
        return Bukkit.getWorld(Main.pl.config.getConfig().getString("lobby.name"));
    }
    public Location getLobbyLocation() {
        FileConfiguration c = pl.config.getConfig();
        return new Location(getLobby(), c.getInt("lobby.location.x",0), c.getInt("lobby.location.y", 50),
                c.getInt("lobby.location.z",0), (float) c.getInt("lobby.location.yaw",0), (float) c.getInt("lobby.location.pitch",0));
    }
    public void setLobbyLocation(Location l) {
        pl.config.getConfig().set("lobby.location.x", l.getBlockX());
        pl.config.getConfig().set("lobby.location.y", l.getBlockY());
        pl.config.getConfig().set("lobby.location.z", l.getBlockZ());
        pl.config.getConfig().set("lobby.location.yaw", l.getYaw());
        pl.config.getConfig().set("lobby.location.pitch", l.getPitch());
        pl.config.saveConfig();
    }

    public int getBorder() {
        return (int) (getMeetupWorld().getWorldBorder().getSize() / 2);
    }

    public void createWorlds() {
        if (getLobby() == null) {
            WorldCreator creator = new WorldCreator(pl.config.getConfig().getString("lobby.name"));
            creator.type(WorldType.FLAT);
            creator.generateStructures(false);
            creator.createWorld();
            getLobby().setDifficulty(Difficulty.PEACEFUL);
            getLobby().setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS,false);
            getLobby().setGameRule(GameRule.DO_WEATHER_CYCLE,false);
        }
        WorldCreator creator = new WorldCreator(pl.config.getConfig().getString("meetup-world"));
        creator.type(WorldType.NORMAL);
        creator.generateStructures(false);
        creator.generatorSettings();
        int total = pl.config.getConfig().getLongList("seed-list").toArray().length;
        creator.seed(pl.config.getConfig().getLongList("seed-list").get(new Random().nextInt(total)));
        creator.createWorld();
        getMeetupWorld().setTime(9000);
        getMeetupWorld().setGameRule(GameRule.DO_DAYLIGHT_CYCLE,false);
        getMeetupWorld().setGameRule(GameRule.DO_MOB_SPAWNING,false);
        getMeetupWorld().setGameRule(GameRule.DO_INSOMNIA,false);
        getMeetupWorld().setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS,false);
        getMeetupWorld().setGameRule(GameRule.NATURAL_REGENERATION,false);
        getMeetupWorld().getWorldBorder().setSize(pl.config.getConfig().getInt("worldborder-size"));
        getMeetupWorld().getWorldBorder().setDamageBuffer(0);
    }

    public void deleteWorld() {
        if (getMeetupWorld() != null) {
            try {
                Bukkit.unloadWorld(getMeetupWorld(),false);
                FileUtils.deleteDirectory(new File(getMeetupWorld().getName()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
