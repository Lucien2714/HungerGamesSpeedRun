package club.lucien2714.hungergamesspeedrun.events;

import club.lucien2714.hungergamesspeedrun.GameStatus.GameStatus;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class playerJoin implements Listener {
    @EventHandler
    void playerJoin(PlayerJoinEvent p){
        p.getPlayer().setGameMode(GameMode.ADVENTURE);
        GameStatus.onlinePlayerAdd(p.getPlayer());
    }
}
