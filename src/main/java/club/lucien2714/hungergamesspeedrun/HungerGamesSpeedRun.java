package club.lucien2714.hungergamesspeedrun;

import club.lucien2714.hungergamesspeedrun.GameStatus.GameStatus;
import club.lucien2714.hungergamesspeedrun.commands.careerSelect;
import club.lucien2714.hungergamesspeedrun.commands.start;
import club.lucien2714.hungergamesspeedrun.events.chooseCareer;
import club.lucien2714.hungergamesspeedrun.events.playerJoin;
import club.lucien2714.hungergamesspeedrun.events.protectArea;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class HungerGamesSpeedRun extends JavaPlugin {

    @Override
    public void onEnable() {
        GameStatus.worlds=getServer().getWorlds();
        for (World world : GameStatus.worlds
        ) {
            world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            world.setTime(0);
            world.setDifficulty(Difficulty.PEACEFUL);
        }
        for (Player temp : getServer().getOnlinePlayers()
        ) {
            temp.setWalkSpeed(0.2F);
            temp.setGameMode(GameMode.ADVENTURE);
            GameStatus.onlinePlayerAdd(temp);
        }
        // Plugin startup logic
        getCommand("careerSelect").setExecutor(new careerSelect());
        getCommand("start").setExecutor(new start());
        getServer().getPluginManager().registerEvents(new chooseCareer(), this);
        getServer().getPluginManager().registerEvents(new playerJoin(), this);
        getServer().getPluginManager().registerEvents(new protectArea(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
