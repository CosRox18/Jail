package cosrox.jail.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static cosrox.jail.Variables.config;
import static cosrox.jail.Variables.sectionExists;

public class jailTabCompliter implements TabCompleter {
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        List<String> list = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("jail")) {
            if (args.length == 1) {
                list.add("wanted");

                /// list.add("rewards"); В будующем времени

            } else if (sender.isOp()) {
                if (args.length == 2) {
                    list.add("add");
                    list.add("remove");
                } else if (args.length == 3) {
                    if (args[1].equals("add")) {
                        OfflinePlayer[] players = Bukkit.getServer().getOfflinePlayers();
                        for (OfflinePlayer player : players) {
                            list.add(player.getName());
                        }
                        Collections.sort(list);
                    } else if (args[1].equals("remove")) {
                        if (!sectionExists("prison")) config.createSection("prison");
                        String[] keys = config.getConfigurationSection("prison").getKeys(false).toArray(new String[0]);
                        int size = keys.length;
                        if(size == 0) {
                            list.add("No prisoners");
                        } else {
                            for (String id : keys) {
                                list.add(Bukkit.getServer().getOfflinePlayer(UUID.fromString(id)).getName());
                            }
                            Collections.sort(list);
                        }
                    }
                } else if (args.length == 4 && args[1].equals("add")) {
                    list.add("1");
                    list.add("2");
                    list.add("3");
                    list.add("4");
                    list.add("5");
                }
            }
            return list;
        }

        return null;
    }
}
