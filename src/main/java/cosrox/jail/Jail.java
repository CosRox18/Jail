package cosrox.jail;

import cosrox.jail.commands.Commands;
import cosrox.jail.commands.jailCommandExecutor;
import cosrox.jail.commands.jailTabCompliter;
import cosrox.jail.data.Menu;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import static cosrox.jail.Variables.*;

public final class Jail extends JavaPlugin{

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.getConfig().options().copyDefaults(true);
        config = this.getConfig();

        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new Events(), this);
        pm.registerEvents(new Menu(), this);
        plugin = this;

        Commands cmd = new Commands();
        getCommand("save").setExecutor(cmd); // temp
        getCommand("load").setExecutor(cmd); // temp

        getCommand("jail").setExecutor(new jailCommandExecutor());
        getCommand("jail").setTabCompleter(new jailTabCompliter());
    }

    @Override
    public void onDisable() {
        this.saveConfig();
    }
}
