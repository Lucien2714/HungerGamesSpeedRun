package club.lucien2714.hungergamesspeedrun.events;


import club.lucien2714.hungergamesspeedrun.GameStatus.GameStatus;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class chooseCareer implements Listener {
    @EventHandler
    void chooseCareer(InventoryClickEvent e) {//gets the item player chose
        Player player = (Player) e.getWhoClicked();
        PotionEffectType buffs[] = {PotionEffectType.INCREASE_DAMAGE, PotionEffectType.SPEED, PotionEffectType.FAST_DIGGING};
        if (e.getView().title().equals(Component.text("职业选择"))) {
            GameStatus game=new GameStatus();
            boolean choosed=false;
            //buffs(potionEffect) will be rewritten!
            switch (e.getCurrentItem().getType()) {
                case DIAMOND_SWORD:
                    System.out.println(game.Career(player));
                    if (game.Career(player) != 0) {
                        player.removePotionEffect(buffs[game.Career(player) - 1]);
                    }
                    choosed=true;
                    game.Career(player, 1);
                    System.out.println(game.Career(player));
                    player.addPotionEffect(new PotionEffect(buffs[0], 60 * 60 * 10, 0));
                    break;
                case DIAMOND_BOOTS:
                    if (game.Career(player) != 0) {
                        player.removePotionEffect(buffs[game.Career(player) - 1]);
                    }
                    choosed=true;
                    game.Career(player, 2);
                    player.addPotionEffect(new PotionEffect(buffs[1], 60 * 60 * 10, 0));
                    break;
                case DIAMOND_PICKAXE:
                    if (game.Career(player) != 0) {
                        player.removePotionEffect(buffs[game.Career(player) - 1]);
                    }
                    choosed=true;
                    game.Career(player, 3);
                    player.addPotionEffect(new PotionEffect(buffs[2], 60 * 60 * 10, 0));
                    break;
            }
            e.setCancelled(true);
            if(choosed)
                player.closeInventory();
        }
    }
}
