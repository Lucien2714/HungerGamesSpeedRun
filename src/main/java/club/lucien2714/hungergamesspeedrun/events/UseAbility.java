package club.lucien2714.hungergamesspeedrun.events;

import club.lucien2714.hungergamesspeedrun.GameStatus.GameStatus;
import club.lucien2714.hungergamesspeedrun.GameStatus.player;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class UseAbility implements Listener {
    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent e) {
        ItemStack consumed = e.getItem();
        player p = GameStatus.getPlayer(e.getPlayer());
        if (consumed.getType() == Material.POTION) {
            System.out.println("drank potion,career:");
            if (consumed.equals(GameStatus.potions.get(p.career - 1))) {
                System.out.println("used potion");
                GameStatus.onlinePlayers.get(GameStatus.onlinePlayers.indexOf(p)).cd = 30 * 20 + GameStatus.cds[p.career - 1] * 20;

            }

        }
    }
    //not written yet!
}
