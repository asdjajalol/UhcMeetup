package net.gonzq.uhcmeetup.Files;

import net.gonzq.uhcmeetup.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class BoardFile {
    private Main plugin;
    private FileConfiguration fileConfig = null;
    private File configFile = null;

    public BoardFile(Main plugin){
        this.plugin = plugin;
        saveDefaultConfig();
    }
    public void reloadConfig() {
        if (this.configFile == null){
            this.configFile = new File(this.plugin.getDataFolder(), "scoreboard.yml");
        }
        this.fileConfig = YamlConfiguration.loadConfiguration(this.configFile);
        InputStream defaultStream = this.plugin.getResource("scoreboard.yml");
        if (defaultStream != null){
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.fileConfig.setDefaults(defaultConfig);
        }
    }
    public FileConfiguration getConfig(){
        if (this.fileConfig == null){
            reloadConfig();
        }
        return this.fileConfig;
    }
    public File getFile() {
        if (this.configFile == null) {
            reloadConfig();
        }
        return this.configFile;
    }
    public void saveConfig(){
        if (this.fileConfig == null || this.configFile == null){
            return;
        }
        try {
            this.getConfig().save(this.configFile);
        } catch (IOException e){
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + this.configFile, e);
        }
    }
    public void saveDefaultConfig(){
        if (this.configFile == null){
            this.configFile = new File(this.plugin.getDataFolder(), "scoreboard.yml");
        }
        if (!this.configFile.exists()){
            this.plugin.saveResource("scoreboard.yml", false);
        }
    }
}
