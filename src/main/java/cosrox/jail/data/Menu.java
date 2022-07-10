package cosrox.jail.data;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static cosrox.jail.Variables.*;

public class Menu implements Listener {
    public static Inventory createMenu(int page) {
        if (!sectionExists("prison")) config.createSection("prison");
        String[] keys = config.getConfigurationSection("prison").getKeys(false).toArray(new String[0]);
        int size = keys.length;

        Inventory menu = Bukkit.createInventory(null, 18, ChatColor.RED + "Розыск" + ChatColor.GRAY + " " + String.valueOf(page));

        ItemStack empty = config.getItemStack("menu.empty");
        for (int i = 0; i < 9; i++) {
            menu.setItem(i, empty);
        }
        int I = (page - 1) * 9;
        for (int i = 0; i < 9; i++) {
            if (I >= size) break;
            String name = Bukkit.getOfflinePlayer(UUID.fromString(keys[I])).getName();
            ItemStack skull = Person.Skull(name);
            //String s = name + "'s wanted level is " + config.getString(keys[I] + ".wanted_level.level");
            String s = "Уровень розыска " + config.getString(keys[I] + ".wanted_level.level");
            List<String> lore = new ArrayList<>();
            lore.add(s);
            skull.setLore(lore);
            menu.setItem(i, skull);
            I++;
        }

        ItemStack frame = config.getItemStack("menu.frame");
        for (int i = 9; i < 18; i++) {
            menu.setItem(i, frame);
        }
        if (page > 1) {
            menu.setItem(12, config.getItemStack("menu.prev"));
        }
        if (size - (page - 1) * 9 > 9) {
            menu.setItem(14, config.getItemStack("menu.next"));
        }
        return menu;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().startsWith(ChatColor.RED + "Розыск")) {
            int page = Integer.parseInt(event.getView().getTitle().split(" ")[1]);
            if (event.getClickedInventory() != null && event.getClickedInventory().getItem(event.getSlot()) != null) {
                if (event.getClickedInventory().getItem(event.getSlot()).equals(config.getItemStack("menu.prev"))) {
                    player.openInventory(createMenu(page - 1));
                } else if (event.getClickedInventory().getItem(event.getSlot()).equals(config.getItemStack("menu.next"))) {
                    player.openInventory(createMenu(page + 1));
                } else
                    event.setCancelled(true);
            } else
                event.setCancelled(true);
        }
    }
}
