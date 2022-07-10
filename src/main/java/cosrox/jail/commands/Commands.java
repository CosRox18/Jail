package cosrox.jail.commands;

import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static cosrox.jail.Variables.*;
import static cosrox.jail.data.Person.*;

/// Команды необходимые для тестирования

public class Commands implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.isOp() && sender instanceof Player) {
            Player player = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("save")) {
                //person.save(((Player) sender), 0);
                ItemStack stack = player.getInventory().getItemInMainHand();
                config.set(args[0], stack);
                plugin.saveConfig();
            } else if (cmd.getName().equalsIgnoreCase("load")) {
                load(((Player) sender));
            }
        }
        return false;
    }
}
