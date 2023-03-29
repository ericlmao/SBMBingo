package games.negative.bingo.core.provider;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import games.negative.bingo.api.BingoTeamManager;
import games.negative.bingo.api.model.team.BingoColor;
import games.negative.bingo.api.model.team.BingoTeam;
import games.negative.bingo.core.structure.BingoTeamImpl;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BingoTeamManagerProvider implements BingoTeamManager {

    private final JavaPlugin plugin;
    private final List<BingoTeam> teams;
    private final Map<UUID, BingoTeam> users;
    public BingoTeamManagerProvider(JavaPlugin plugin, FileConfiguration config) {
        this.plugin = plugin;
        this.teams = Lists.newArrayList();
        this.users = Maps.newHashMap();

        onReload(config);
    }

    @Override
    public Collection<BingoTeam> getTeams() {
        return teams;
    }

    @Override
    public void addTeam(BingoTeam team) {
        teams.add(team);
    }

    @Override
    public void removeTeam(BingoTeam team) {
        teams.remove(team);
    }

    @Override
    public Map<UUID, BingoTeam> getUserTeams() {
        return users;
    }

    @Override
    public void addUserTeam(UUID uuid, BingoTeam team) {
        users.put(uuid, team);
    }

    @Override
    public void removeUserTeam(UUID uuid) {
        users.remove(uuid);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onReload(FileConfiguration config) {

        for (BingoTeam team : teams) {
            Team minecraftTeam = team.getMinecraftTeam();
            if (minecraftTeam == null)
                continue;

            try {
                minecraftTeam.unregister();
            } catch (IllegalStateException e) {
                // Ignore
                plugin.getLogger().warning("Team for BingoTeam " + team.getBingoColor().toString() + " was already unregistered!");
                return;
            }
        }

        this.teams.clear();
        this.users.clear();

        ConfigurationSection teams = config.getConfigurationSection("teams");
        Preconditions.checkNotNull(teams, "Teams section is null");

        for (String raw : teams.getKeys(false)) {
            boolean enabled = teams.getBoolean(raw);
            if (!enabled) {
                plugin.getLogger().warning("Team " + raw + " is disabled");
                continue;
            }
            plugin.getLogger().info("Team " + raw + " is enabled");

            BingoColor color = BingoColor.valueOf(raw);
            BingoTeam team = new BingoTeamImpl(color);
            addTeam(team);
        }
    }
}
