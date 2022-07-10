package cosrox.jail.data;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

import static cosrox.jail.Variables.*;


public class Person {
    public Person() {
    }

    public static int getWantedLevel(OfflinePlayer player) {
        if (sectionEmpty(player.getUniqueId() + ".wanted_level")) {
            return 0;
        }
        return config.getInt(player.getUniqueId() + ".wanted_level.level");
    }

    public static void setWantedLevel(OfflinePlayer player, int wanted_level) {
        if (!sectionExists(player.getUniqueId() + ".wanted_level"))
            config.createSection(player.getUniqueId() + ".wanted_level");
        if(getWantedLevel(player) == wanted_level){return;}
        if(getWantedLevel(player) > 0 && wanted_level == 0){
            if (!sectionExists("prison"))
                config.createSection("prison");
            config.set("prison." + player.getUniqueId(), null);
            config.set(player.getUniqueId() + ".wanted_level.level", 0);
            plugin.saveConfig();
        } else {
            if (!sectionExists("prison"))
                config.createSection("prison");
            config.set("prison." + player.getUniqueId(), wanted_level);
            config.set(player.getUniqueId() + ".wanted_level.level", wanted_level);
            plugin.saveConfig();
        }
    }

    public static void save(Player player, int wanted_level) {
        Location spawn = player.getBedSpawnLocation();
        UUID id = player.getUniqueId();
        PlayerInventory inventory = player.getInventory();
        if (!sectionExists(id + ".spawn")) config.createSection(id + ".spawn");
        if (!sectionExists(id + ".wanted_level")) config.createSection(id + ".wanted_level");
        config.set(id + ".wanted_level.level", wanted_level);
        config.set(id + ".spawn.location", spawn);
        if (!sectionExists(id + ".inventory")) config.createSection(id + ".inventory");
        ItemStack[] contents = inventory.getContents();
        for (int i = 0; i < contents.length; i++) {
            if (contents[i] != null && contents[i].getType() != Material.AIR) {
                config.set(id + ".inventory." + i, contents[i]);
            } else {
                config.set(id + ".inventory." + i, null);
            }
        }
        plugin.saveConfig();
    }

    public static void load(OfflinePlayer offlinePlayer) {
        Player player = offlinePlayer.getPlayer();

        if (sectionEmpty(player.getUniqueId() + ".inventory")) {
            System.err.println("Error while loading " + player.getName());
            return;
        }
        if (sectionEmpty(player.getUniqueId() + ".wanted_level")) {
            System.err.println("Error while loading " + player.getName());
            return;
        }
        if (sectionEmpty(player.getUniqueId() + ".spawn")) {
            player.setBedSpawnLocation(Bukkit.getWorld(config.getString("config.world-name")).getSpawnLocation(), true);
        } else {
            player.setBedSpawnLocation(config.getLocation(player.getUniqueId() + ".spawn.location"), true);
        }
        ItemStack[] contents = new ItemStack[36];
        for (int i = 0; i < contents.length; i++) {
            ItemStack x = config.getItemStack(player.getUniqueId() + ".inventory." + i, contents[i]);
            if (x != null) contents[i] = x;
        }
        player.getInventory().setContents(contents);
        player.updateInventory();
    }

    public static ItemStack Skull(String skullOwner) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1, (short)3);
        SkullMeta skullMeta = (SkullMeta)skull.getItemMeta();
        skullMeta.setOwner(skullOwner);
        skull.setItemMeta(skullMeta);
        return skull;
    }
}
