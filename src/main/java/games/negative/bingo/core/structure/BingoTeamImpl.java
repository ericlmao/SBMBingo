package games.negative.bingo.core.structure;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import games.negative.bingo.api.model.goal.BingoGoal;
import games.negative.bingo.api.model.team.BingoColor;
import games.negative.bingo.api.model.team.BingoTeam;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BingoTeamImpl implements BingoTeam {

    private final BingoColor color;
    private final Map<BingoGoal, Integer> progress;
    private final List<UUID> members;

    public BingoTeamImpl(BingoColor color) {
        this.color = color;
        this.members = Lists.newArrayList();
        this.progress = Maps.newHashMap();
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

    }
}
