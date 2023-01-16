package net.gonzq.uhcmeetup.Managers;

import net.gonzq.uhcmeetup.Enums.GameState;
import net.gonzq.uhcmeetup.Main;
import net.gonzq.uhcmeetup.players.GamePlayer;
import net.gonzq.uhcmeetup.Tasks.CountdownTask;
import net.gonzq.uhcmeetup.Utils.Utils;
import net.gonzq.uhcmeetup.VoteSystem.VoteScenarios;

public class GameManager {
    Main pl = Main.pl;
    private static GameManager instance = new GameManager();
    private VoteScenarios voteScenarios = new VoteScenarios();

    public static GameManager getInstance() {
        return instance;
    }

    public VoteScenarios getVoteScenarios() {
        return voteScenarios;
    }

    int countdown = pl.config.getConfig().getInt("countdown-time");
    public int getCountdown() {
        return countdown;
    }
    public void restCountdown() {
        countdown--;
    }

    GameState state = GameState.WAITING;
    public void setState(GameState state) {
        this.state = state;
    }
    public GameState getState() {
        return state;
    }

    int elapsed = 0;
    public void setTimeElapsed(int i) {
        elapsed = i;
    }
    public int getTimeElapsed() {
        return elapsed;
    }

    int teamsize = 0;
    public void setTeamsize(int i) {
        teamsize = i;
    }
    public int getTeamsize() {
        return teamsize;
    }

    boolean teamManagement = false;
    public void setTeamManagement(boolean teamManagement) {
        this.teamManagement = teamManagement;
    }
    public boolean getTeamManagement() {
        return teamManagement;
    }

    public String getLobby() {
        return Main.pl.config.getConfig().getString("lobby-world");
    }

    public String getWorld() {
        return Main.pl.config.getConfig().getString("meetup-world");
    }


    public void start() {
        Main.playerManager.getPlayerList().stream().filter(GamePlayer::isOnline).forEach(p -> {
            Utils.delay(5, () -> {
                ScatterManager.getInstance().scatter(p);
                p.getPlayer().setInvulnerable(false);
            });
        });
        setState(GameState.STARTING);
        new CountdownTask().runTaskTimer(pl,0,20);
        if (pl.config.getConfig().getBoolean("vote-system.scenario-system")) {
            getVoteScenarios().setCanVote(true);
        }
    }
}
