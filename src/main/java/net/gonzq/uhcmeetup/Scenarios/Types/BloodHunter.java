package net.gonzq.uhcmeetup.Scenarios.Types;

import net.gonzq.uhcmeetup.Enums.GameState;
import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.Managers.GameManager;
import net.gonzq.uhcmeetup.Scenarios.Scenario;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class BloodHunter extends Scenario implements Listener {
    private boolean enabled = false;

    public BloodHunter() {
        super("BloodHunter", new ItemStack(Material.IRON_SWORD), "&7Killing someone adds an extra heart");
    }

    @EventHandler
    public void onKill(PlayerDeathEvent e) {
        Player p = e.getEntity();
        Player k = p.getKiller();
        GameManager game = GameManager.getInstance();
        if (game.getState().equals(GameState.STARTED) && k != null) {
            k.setMaxHealth(k.getMaxHealth() + 2);
        }
    }

    @Override
    protected void setEnabled(boolean enable) {
        this.enabled = enable;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
