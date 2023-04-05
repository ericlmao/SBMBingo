package games.negative.bingo.listener;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import games.negative.bingo.api.BingoGameManager;
import games.negative.bingo.api.BingoTeamManager;
import games.negative.bingo.api.event.BingoConfigReloadEvent;
import games.negative.bingo.api.event.game.BingoGameEndEvent;
import games.negative.bingo.api.model.BingoGame;
import games.negative.bingo.menu.BingoTeamMenu;
import games.negative.framework.base.itembuilder.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class BingoTeamSelectorListener implements Listener {

    private final NamespacedKey key;
    private ItemStack item;
    private int slot;
    private boolean locked;

    private final BingoTeamManager manager;
    private final BingoGameManager gameManager;

    public BingoTeamSelectorListener(JavaPlugin plugin, BingoTeamManager manager, BingoGameManager gameManager) {
        this.manager = manager;
        this.gameManager = gameManager;

        this.key = new NamespacedKey(plugin, "bingo-team-selector");

        FileConfiguration config = plugin.getConfig();
        ConfigurationSection section = config.getConfigurationSection("bingo-team-selector");
        Preconditions.checkNotNull(section, "bingo-team-selector section is null");

        String name = section.getString("name", "&aBingo Team Selector &7(Right Click)");
        Material material = Material.valueOf(section.getString("material"));

        this.item = ItemBuilder.newItemBuilder(material).setName(name).build();

        this.slot = section.getInt("slot", 1);
        this.locked = section.getBoolean("locked", true);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // We don't want the player to get this item
        // if there is an active game ongoing
        BingoGame game = gameManager.getActiveGame();
        if (game != null) return;

        give(player);
    }

    @EventHandler
    public void onGameEnd(BingoGameEndEvent event) {
        Bukkit.getOnlinePlayers().forEach(this::give);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        ItemStack item = event.getItemDrop().getItemStack();
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        PersistentDataContainer container = meta.getPersistentDataContainer();
        if (!container.has(key, PersistentDataType.BYTE)) return;

        if (!locked) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item == null || item.getType() == Material.AIR) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        PersistentDataContainer container = meta.getPersistentDataContainer();
        if (!container.has(key, PersistentDataType.BYTE)) return;

        if (!locked) return;

        event.setCancelled(true);
        event.getView().setCursor(null);

        Player player = (Player) event.getWhoClicked();
        player.updateInventory();
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item == null || item.getType() == Material.AIR) return;

        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        PersistentDataContainer container = meta.getPersistentDataContainer();
        if (!container.has(key, PersistentDataType.BYTE)) return;

        new BingoTeamMenu(manager).open(player);
    }

    @EventHandler
    public void onReload(BingoConfigReloadEvent event) {
        FileConfiguration config = event.getConfig();
        ConfigurationSection section = config.getConfigurationSection("bingo-card");
        Preconditions.checkNotNull(section, "bingo-card section is null");

        String name = section.getString("name", "&aBingo Card &7(Right Click)");
        Material material = Material.valueOf(section.getString("material"));

        this.item = ItemBuilder.newItemBuilder(material).setName(name).build();

        this.slot = section.getInt("slot", 9);
        this.locked = section.getBoolean("locked", true);
    }

    private void give(Player player) {

        ItemStack item = this.item.clone();
        ItemMeta meta = item.getItemMeta();
        Preconditions.checkNotNull(meta, "ItemMeta is null");

        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(key, PersistentDataType.BYTE, (byte) 1);

        item.setItemMeta(meta);

        // todo Maybe make this a utility method to avoid boilerplate code
        // Loop through the inventory and check if any of the items are the bingo card
        // If it is then remove the item
        PlayerInventory inv = player.getInventory();
        List<ItemStack> duplicate = Lists.newArrayList();
        for (ItemStack content : inv.getContents()) {
            if (content == null || content.getType() == Material.AIR) continue;

            ItemMeta contentMeta = content.getItemMeta();
            if (contentMeta == null) continue;

            PersistentDataContainer contentContainer = contentMeta.getPersistentDataContainer();
            if (!contentContainer.has(key, PersistentDataType.BYTE)) continue;

            duplicate.add(content);
        }

        duplicate.forEach(inv::remove);

        inv.setItem((slot - 1), item);

        player.updateInventory();
    }
}
