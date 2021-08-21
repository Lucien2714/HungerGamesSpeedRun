package club.lucien2714.hungergamesspeedrun.events;


import club.lucien2714.hungergamesspeedrun.GameStatus.GameStatus;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Objects;

public class chooseCareer implements Listener {
    @EventHandler
    void chooseCareer(InventoryClickEvent e) {//gets the item player chose
        Player player = (Player) e.getWhoClicked();
        if (e.getView().title().equals(Component.text("职业选择"))) {
            GameStatus game = new GameStatus();
            boolean chosen = false;
            //buffs(potionEffect) will be rewritten!
            switch (Objects.requireNonNull(e.getCurrentItem()).getType()) {
                case DIAMOND_SWORD:
                    game.Career(player, 1);
                    game.ready(player, true);
                    chosen = true;
                    break;
                case DIAMOND_BOOTS:
                    game.Career(player, 2);
                    game.ready(player, true);
                    chosen = true;
                    break;
                case DIAMOND_PICKAXE:
                    game.Career(player, 3);
                    game.ready(player, true);
                    chosen = true;
                    break;
            }
            e.setCancelled(true);
            if (chosen)
                player.closeInventory();
        }
    }
}
