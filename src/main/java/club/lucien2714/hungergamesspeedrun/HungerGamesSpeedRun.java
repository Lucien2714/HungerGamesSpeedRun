package club.lucien2714.hungergamesspeedrun;

import club.lucien2714.hungergamesspeedrun.GameStatus.GameStatus;
import club.lucien2714.hungergamesspeedrun.GameStatus.player;
import club.lucien2714.hungergamesspeedrun.commands.careerSelect;
import club.lucien2714.hungergamesspeedrun.commands.start;
import club.lucien2714.hungergamesspeedrun.components.components;
import club.lucien2714.hungergamesspeedrun.events.*;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;

public final class HungerGamesSpeedRun extends JavaPlugin {
    @Override
    public void onEnable() {
        ArrayList<PotionMeta> potionsMeta = new ArrayList<PotionMeta>();
        PotionEffectType bufftype[] = {PotionEffectType.INCREASE_DAMAGE, PotionEffectType.SPEED, PotionEffectType.FAST_DIGGING};
        PotionEffect buffs[] = {new PotionEffect(bufftype[0], 30 * 20, 1), new PotionEffect(bufftype[1], 30 * 20, 1), new PotionEffect(bufftype[2], 30 * 20, 1)};


        for (int temp = 0; temp < 3; temp++) {
            GameStatus.potions.add(new ItemStack(Material.POTION));
            potionsMeta.add((PotionMeta) GameStatus.potions.get(temp).getItemMeta());
            potionsMeta.get(temp).setBasePotionData(new PotionData(PotionType.WATER));
            potionsMeta.get(temp).clearCustomEffects();
            potionsMeta.get(temp).displayName(components.createComponent("技能",TextColor.color(0,0,255)));
            potionsMeta.get(temp).addCustomEffect(buffs[temp], true);
            GameStatus.potions.get(temp).setItemMeta(potionsMeta.get(temp));
        }
        Runnable givePotion = () -> {
            if (GameStatus.gameStatus == GameStatus.gameState.Started) {
                for (player p : GameStatus.onlinePlayers
                ) {
                    if (p.cd != 0) {
                        p.cd -= 1;
                    } else {
                        Inventory pInventory = p.p.getInventory();
                        if (!pInventory.contains(GameStatus.potions.get(p.career - 1))) {
                            if (pInventory.firstEmpty() == -1) {
                                p.p.sendMessage("背包已满！请丢掉一些物品以获取药水");
                                p.cd=20;
                            } else
                                pInventory.addItem(GameStatus.potions.get(p.career - 1));
                        }
                    }

                }
            }
        };
        Runnable r = () -> {
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
                                compass.displayName(components.createComponent(
                                        "正在追踪：<" + p.compassTarget.p.getName() + ">",
                                        TextColor.color(255, 0, 0)));
                                item.setItemMeta(compass);
                                //System.out.println("target:"+p.compassTarget.p.getName()+"\nlocaiton:"+p.compassTarget.p.getLocation().getX()+","+p.compassTarget.p.getLocation().getY()+","+p.compassTarget.p.getLocation().getZ());
                            }
                            p.p.updateInventory();
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
                    GameStatus.gameStatus = GameStatus.gameState.Started;
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
            temp.setWalkSpeed((float) 0.2);
            GameStatus.onlinePlayerAdd(temp);
        }
        // Plugin startup logic
        getCommand("careerSelect").setExecutor(new careerSelect());
        getCommand("start").setExecutor(new start());
        getServer().getPluginManager().registerEvents(new chooseCareer(), this);
        getServer().getPluginManager().registerEvents(new playerJoin(), this);
        getServer().getPluginManager().registerEvents(new protectArea(), this);
        getServer().getPluginManager().registerEvents(new PlayerDisconnect(), this);
        getServer().getPluginManager().registerEvents(new UseAbility(), this);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, r, 0L, 1L);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, reload, 0L, 1L);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, givePotion, 0L, 1L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
