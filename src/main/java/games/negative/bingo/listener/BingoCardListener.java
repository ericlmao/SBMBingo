package games.negative.bingo.listener;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import games.negative.bingo.api.event.BingoConfigReloadEvent;
import games.negative.framework.base.itembuilder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class BingoCardListener implements Listener {

    private final NamespacedKey card;
    private ItemStack cardItem;
    private int slot;
    private boolean locked;

    public BingoCardListener(JavaPlugin plugin) {
        this.card = new NamespacedKey(plugin, "bingo-card");

        FileConfiguration config = plugin.getConfig();
        ConfigurationSection section = config.getConfigurationSection("bingo-card");
        Preconditions.checkNotNull(section, "bingo-card section is null");

        String name = section.getString("name", "&aBingo Card &7(Right Click)");
        Material material = Material.valueOf(section.getString("material"));

        this.cardItem = ItemBuilder.newItemBuilder(material).setName(name).build();

        this.slot = section.getInt("slot", 9);
        this.locked = section.getBoolean("locked", true);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        //todo check for status of the plugin if it's enabled or not

        ItemStack item = cardItem.clone();
        ItemMeta meta = item.getItemMeta();
        Preconditions.checkNotNull(meta, "ItemMeta is null");

        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(card, PersistentDataType.BYTE, (byte) 1);

        item.setItemMeta(meta);

        // Loop through the inventory and check if any of the items are the bingo card
        // If it is then remove the item
        PlayerInventory inv = player.getInventory();
        List<ItemStack> duplicate = Lists.newArrayList();
        for (ItemStack content : inv.getContents()) {
            if (content == null || content.getType() == Material.AIR)
                continue;

            ItemMeta contentMeta = content.getItemMeta();
            if (contentMeta == null)
                continue;

            PersistentDataContainer contentContainer = contentMeta.getPersistentDataContainer();
            if (contentContainer.has(card, PersistentDataType.BYTE))
                duplicate.add(content);
        }

        for (ItemStack duplicateItem : duplicate) {
            inv.remove(duplicateItem);
        }

        inv.setItem(slot, item);

        player.updateInventory();
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        ItemStack item = event.getItemDrop().getItemStack();
        ItemMeta meta = item.getItemMeta();
        if (meta == null)
            return;

        PersistentDataContainer container = meta.getPersistentDataContainer();
        if (!container.has(card, PersistentDataType.BYTE))
            return;

        if (!locked)
            return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item == null || item.getType() == Material.AIR)
            return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null)
            return;

        PersistentDataContainer container = meta.getPersistentDataContainer();
        if (!container.has(card, PersistentDataType.BYTE))
            return;

        if (!locked)
            return;

        event.setCancelled(true);
        event.getView().setCursor(null);

        Player player = (Player) event.getWhoClicked();
        player.updateInventory();
    }

    @EventHandler
    public void onReload(BingoConfigReloadEvent event) {
        FileConfiguration config = event.getConfig();
        ConfigurationSection section = config.getConfigurationSection("bingo-card");
        Preconditions.checkNotNull(section, "bingo-card section is null");

        String name = section.getString("name", "&aBingo Card &7(Right Click)");
        Material material = Material.valueOf(section.getString("material"));

        this.cardItem = ItemBuilder.newItemBuilder(material).setName(name).build();

        this.slot = section.getInt("slot", 9);
        this.locked = section.getBoolean("locked", true);
    }
}
