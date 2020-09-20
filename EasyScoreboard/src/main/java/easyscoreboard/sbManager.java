package easyscoreboard;

import easyscoreboard.ScoreBoard;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.*;

public class sbManager {

    private Scoreboard scoreBoard;
    private Objective objective;
    private Team team;
    private Score score;

    public void onJoin(Player player) {
        Plugin plugin = ScoreBoard.getPlugin(ScoreBoard.class);
        createSB(DisplaySlot.SIDEBAR, "Test123", "dummy","test", plugin.getServer().getScoreboardManager());
        player.setScoreboard(this.scoreBoard);
        player.sendMessage("Scoreboard open?");
        System.out.print("sbManager open scoreboard?");
    }

    public void createSB(DisplaySlot slot, String title, String Criteria, String name, ScoreboardManager sb, String score) {
        this.scoreBoard = sb.getNewScoreboard();
        this.objective = scoreBoard.registerNewObjective(name, Criteria, title);
        this.objective.setDisplaySlot(slot);
        this.score = this.objective.getScore(score);
    }

    public void setScore(int score) {
        this.score.setScore(score);
    }
}
