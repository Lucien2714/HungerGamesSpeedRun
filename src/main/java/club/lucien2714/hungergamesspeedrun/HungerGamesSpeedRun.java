package club.lucien2714.hungergamesspeedrun;

import club.lucien2714.hungergamesspeedrun.GameStatus.GameStatus;
import club.lucien2714.hungergamesspeedrun.GameStatus.player;
import club.lucien2714.hungergamesspeedrun.commands.careerSelect;
import club.lucien2714.hungergamesspeedrun.commands.start;
import club.lucien2714.hungergamesspeedrun.events.PlayerDisconnect;
import club.lucien2714.hungergamesspeedrun.events.chooseCareer;
import club.lucien2714.hungergamesspeedrun.events.playerJoin;
import club.lucien2714.hungergamesspeedrun.events.protectArea;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class HungerGamesSpeedRun extends JavaPlugin {
    @Override
    public void onEnable() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                if (GameStatus.gameStatus == GameStatus.gameState.Started) {
                    for (player p : GameStatus.onlinePlayers
                    ) {
                        for (ItemStack item : p.p.getInventory().getContents()) {
                            if (item != null) {
                                if (item.getType() == Material.COMPASS) {
                                    //System.out.println("founded compass from player:"+p.p.getName());
                                    CompassMeta compass = (CompassMeta) item.getItemMeta();
                                    compass.setLodestone(p.compassTarget.p.getLocation());
                                    compass.setLodestoneTracked(false);
                                    item.setItemMeta(compass);
                                    //System.out.println("target:"+p.compassTarget.p.getName()+"\nlocaiton:"+p.compassTarget.p.getLocation().getX()+","+p.compassTarget.p.getLocation().getY()+","+p.compassTarget.p.getLocation().getZ());
                                }
                                p.p.updateInventory();
                            }
                        }
                    }
                }
            }
        };


        Runnable reload = new Runnable() {
            @Override
            public void run() {
                if (GameStatus.gameStatus == GameStatus.gameState.Loading) {
                    for (int timer = 3; timer >= 0; timer--) {
                        for (player temp : GameStatus.onlinePlayers
                        ) {
                            if (timer != 0) {
                                temp.p.sendTitle(timer + "...", "", 0, 20, 0);
                            } else {
                                temp.p.getInventory().clear();
                                ItemStack compass = new ItemStack(Material.COMPASS);
                                temp.p.getInventory().addItem(compass);
                                GameStatus.getnext(temp);
                                temp.p.teleport(GameStatus.worlds.get(0).getSpawnLocation());
                                temp.p.setGameMode(GameMode.SURVIVAL);
                                temp.p.sendTitle("猎杀开始！", "成为首个猎杀末影龙之人吧！", 10, 30, 10);
                            }
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    GameStatus.gameStatus= GameStatus.gameState.Started;
                }
            }
        };
        GameStatus.worlds = getServer().getWorlds();
        for (World world : GameStatus.worlds
        ) {
            world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            world.setTime(0);
            world.setDifficulty(Difficulty.PEACEFUL);
        }
        for (Player temp : getServer().getOnlinePlayers()
        ) {
            temp.setGameMode(GameMode.ADVENTURE);
            GameStatus.onlinePlayerAdd(temp);
        }
        // Plugin startup logic
        getCommand("careerSelect").setExecutor(new careerSelect());
        getCommand("start").setExecutor(new start());
        getServer().getPluginManager().registerEvents(new chooseCareer(), this);
        getServer().getPluginManager().registerEvents(new playerJoin(), this);
        getServer().getPluginManager().registerEvents(new protectArea(), this);
        getServer().getPluginManager().registerEvents(new PlayerDisconnect(), this);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, r, 0L, 1L);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, reload, 0L, 1L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
