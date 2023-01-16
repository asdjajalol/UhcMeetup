package net.gonzq.uhcmeetup.Scenarios;

import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class Scenario {
    private String name;
    private ItemStack icon;
    private List<String> desc;
    private final Main pl = Main.pl;

    protected Scenario(String name, ItemStack icon, String... desc) {
        this.name = name;
        this.icon = icon;
        this.desc = new ArrayList<>();
        for (String s : desc) {
            this.desc.add(Utils.chat(s));
        }
    }

    public String getName() {
        return name;
    }

    public List<String> getDescription() {
        return desc;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public void enable() {
        if (this instanceof Listener) {
            Bukkit.getServer().getPluginManager().registerEvents((Listener) this,pl);
        }
        setEnabled(true);
    }
    public void disable() {
        if (this instanceof Listener) {
            HandlerList.unregisterAll((Listener) this);
        }
        setEnabled(false);
    }

    protected abstract void setEnabled(boolean enable);
    public abstract boolean isEnabled();
}
