package club.lucien2714.hungergamesspeedrun.commands;

import club.lucien2714.hungergamesspeedrun.GameStatus.GameStatus;
import club.lucien2714.hungergamesspeedrun.GameStatus.player;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class start implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (GameStatus.gameStatus != GameStatus.gameState.Started) {
            boolean ready = true;
            for (player p : GameStatus.onlinePlayers
            ) {
                if (p.ready != true)
                    ready = false;
            }
            if (ready) {
                GameStatus.gameStatus = GameStatus.gameState.Started;
                for (World w : GameStatus.worlds
                ) {
                    w.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
                    w.setTime(0);
                    w.setDifficulty(Difficulty.EASY);
                }
                for (int timer = 3; timer >= 0; timer--) {
                    for (player temp : GameStatus.onlinePlayers
                    ) {
                        if (timer != 0) {
                            temp.p.sendTitle(timer + "...", "", 0, 20, 0);
                        } else {
                            temp.p.teleport(GameStatus.worlds.get(0).getSpawnLocation());
                            temp.p.setGameMode(GameMode.SURVIVAL);
                            temp.p.sendTitle("猎杀开始！", "成为首个猎杀末影龙之人吧！", 10, 30, 10);
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }

            }
        }
        else{
            commandSender.sendMessage("还有人没有选择完职业！！");
        }
        return true;
    }


}
