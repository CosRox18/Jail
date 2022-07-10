package cosrox.jail;

import cosrox.jail.data.Person;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class Variables {
    public static FileConfiguration config;
    public static Plugin plugin;

    public static boolean sectionExists(String section) {
        return config.getConfigurationSection(section) != null;
    }

    public static boolean sectionEmpty(String section) {
        return !sectionExists(section) || config.getConfigurationSection(section).getKeys(false).size() == 0;
    }

    public static int sectionSize(String section) {
        if(!sectionExists(section)) return 0;
        return config.getConfigurationSection(section).getKeys(false).size();
    }

    public static boolean varExists(String var){
        return config.get(var) != null;
    }
}
