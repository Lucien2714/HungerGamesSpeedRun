package club.lucien2714.hungergamesspeedrun.commands;

import club.lucien2714.hungergamesspeedrun.GameStatus.GameStatus;
import club.lucien2714.hungergamesspeedrun.GameStatus.player;
import org.bukkit.Difficulty;
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
            String notReady=new String();
            for (player p : GameStatus.onlinePlayers
            ) {
                if (p.ready != true)
                    notReady+=p.p.getName()+"、";
            }
            if (notReady.isEmpty()) {
                GameStatus.gameStatus = GameStatus.gameState.Started;
                for (World w : GameStatus.worlds
                ) {
                    w.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
                    w.setTime(0);
                    w.setDifficulty(Difficulty.EASY);
                }
                GameStatus.gameStatus= GameStatus.gameState.Loading;

            } else {
                commandSender.sendMessage("还没有选择完职业！！");
            }
        } else {
            commandSender.sendMessage("游戏已开始!请勿重新启动!");
        }
        return true;
    }


}
