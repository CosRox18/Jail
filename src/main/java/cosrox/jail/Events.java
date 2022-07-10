package cosrox.jail;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;

import static cosrox.jail.Variables.*;
import static cosrox.jail.data.Person.*;

public class Events implements Listener {

    @EventHandler
    public void onDeath(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = ((Player) e.getEntity());
            if (player.getHealth() - e.getFinalDamage() < 0.1) {
                if (getWantedLevel(player) > 0 && e.getDamager() instanceof Player && !(((Player) e.getDamager()).equals(player))) {
                    save(player, getWantedLevel(player));
                    player.getInventory().clear();
                    player.updateInventory();
                    player.setHealth(0);
                    Player killer = (Player) e.getDamager();

                    Bukkit.getServer().broadcastMessage(player.getDisplayName() + " был пойман игроком " + killer.getName() + ". Кара настигла его! Да здраствует правосудие!");

                    player.setGameMode(GameMode.ADVENTURE);

                    /// Добавить обрабтку после поимки

                }
            }
        }
    }

}
