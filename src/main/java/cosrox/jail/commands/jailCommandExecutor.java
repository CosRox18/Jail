package cosrox.jail.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static cosrox.jail.data.Menu.*;
import static cosrox.jail.data.Person.*;


public class jailCommandExecutor implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 0){
            sender.sendMessage("Use /jail wanted");
            return false;
        }
        if(args[0].equals("wanted")){
            if(args.length > 1){
                if(!sender.isOp()) {
                    sender.sendMessage("Use /jail wanted");
                    return false;
                }
                if (args[1].equals("add")){
                    if (args.length != 4){
                        sender.sendMessage("Use /jail wanted add <player> <wanted level>");
                        return false;
                    }
                    if(Integer.parseInt(args[3]) > 5 || Integer.parseInt(args[3]) < 1){
                        sender.sendMessage("Wanted level is from 1 to 5");
                        return false;
                    }
                    OfflinePlayer player = Bukkit.getOfflinePlayerIfCached(args[2]);
                    if(player == null){
                        sender.sendMessage("No player found");
                        return false;
                    }
                    setWantedLevel(player, Integer.parseInt(args[3]));
                } else if(args[1].equals("remove")){
                    if (args.length != 3){
                        sender.sendMessage("Use /jail wanted remove <player>");
                        return false;
                    }
                    OfflinePlayer player = Bukkit.getOfflinePlayerIfCached(args[2]);
                    if(player == null){
                        sender.sendMessage("No player found");
                        return false;
                    }
                    setWantedLevel(player, 0);
                } else {
                    sender.sendMessage("Use /jail wanted [add or remove] <player>");
                    return false;
                }
            } else {
                if(sender instanceof Player){
                    Player player = (Player) sender;
                    player.openInventory(createMenu(1));
                    return true;
                } else {
                    sender.sendMessage("Only player can use this command");
                    return false;
                }
            }
        } else {
            sender.sendMessage("Use /jail wanted");
            return false;
        }
        return false;
    }
}
