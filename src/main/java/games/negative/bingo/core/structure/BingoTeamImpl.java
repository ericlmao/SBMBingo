package games.negative.bingo.core.structure;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import games.negative.bingo.api.model.goal.BingoGoal;
import games.negative.bingo.api.model.team.BingoColor;
import games.negative.bingo.api.model.team.BingoTeam;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BingoTeamImpl implements BingoTeam {

    private final BingoColor color;
    private final Map<BingoGoal, Integer> progress;
    private final List<UUID> members;
    private final Team team;

    public BingoTeamImpl(BingoColor color) {
        this.color = color;
        this.members = Lists.newArrayList();
        this.progress = Maps.newHashMap();

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Preconditions.checkNotNull(manager, "Scoreboard manager is null");

        Scoreboard board = manager.getMainScoreboard();

        Team temp = board.getTeam("bingo-" + color.toString());
        if (temp == null)
            temp = board.registerNewTeam("bingo-" + color);

        this.team = temp;
        team.setColor(color.getColor());
    }

    @Override
    public Team getMinecraftTeam() {
        return team;
    }

    @Override
    public Collection<UUID> getMembers() {
        return members;
    }

    @Override
    public void addMember(UUID uuid) {
        members.add(uuid);
    }

    @Override
    public void removeMember(UUID uuid) {
        members.remove(uuid);
    }

    @Override
    public BingoColor getBingoColor() {
        return color;
    }

    @Override
    public Map<BingoGoal, Integer> getProgresses() {
        return progress;
    }

    @Override
    public void addProgress(BingoGoal goal, int amount) {
        if (progress.containsKey(goal)) {
            progress.put(goal, progress.get(goal) + amount);
        } else {
            progress.put(goal, amount);
        }
    }

    @Override
    public void setProgress(BingoGoal goal, int amount) {
        if (progress.containsKey(goal)) {
            progress.put(goal, amount);
        } else {
            progress.put(goal, amount);
        }
    }

    @Override
    public void clearProgress(BingoGoal goal) {
        progress.remove(goal);
    }

    @Override
    public int getProgress(BingoGoal goal) {
        return progress.getOrDefault(goal, 0);
    }

    @Override
    public @NotNull BingoColor getKey() {
        return color;
    }

    @Override
    public void setKey(@NotNull BingoColor bingoColor) {
        throw new UnsupportedOperationException("Cannot change the key of a team");
    }
}
