package net.gonzq.uhcmeetup.Commands;

import net.gonzq.uhcmeetup.Guis.VoteGui;
import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.Managers.GameManager;
import net.gonzq.uhcmeetup.Utils.Utils;
import net.gonzq.uhcmeetup.VoteSystem.VoteScenarios;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class VoteCommand implements CommandExecutor {
    private Main pl = Main.pl;

    public VoteCommand() {
        pl.getCommand("vote").setExecutor(this);
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        VoteScenarios vote = GameManager.getInstance().getVoteScenarios();
        if (sender instanceof Player p) {
            if (p.hasPermission("meetup.vote")) {
                if (pl.config.getConfig().getBoolean("vote-system.scenario-system")) {
                    if (vote.canVote()) {
                        new VoteGui().open(p);
                    } else {
                        p.sendMessage(Utils.chat("&cVoting is not available now."));
                    }
                }
            }
        }
        return false;
    }
}
