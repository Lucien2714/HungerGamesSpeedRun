package club.lucien2714.hungergamesspeedrun.events;

import club.lucien2714.hungergamesspeedrun.GameStatus.GameStatus;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerDisconnect implements Listener {
    public void disconnected(PlayerQuitEvent e){
        if(GameStatus.gameStatus== GameStatus.gameState.Waiting){
            GameStatus.onlinePlayers.remove(e.getPlayer());
        }

    }
}
