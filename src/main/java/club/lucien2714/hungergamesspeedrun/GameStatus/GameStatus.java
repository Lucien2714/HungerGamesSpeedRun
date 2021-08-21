package club.lucien2714.hungergamesspeedrun.GameStatus;

import club.lucien2714.hungergamesspeedrun.saveData.saveData;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GameStatus {
    public enum gameState {
        Waiting,
        Loading,
        Started,
        Stopped,
        Ended
    }//check the game status

    public static List<World> worlds = new ArrayList<World>();
    public static gameState gameStatus = gameState.Waiting;
    public static List<player> onlinePlayers = new ArrayList<player>();

    public static void onlinePlayerAdd(Player p) {
        player temp = new player();
        temp.p = p;
        temp.career = 0;
        onlinePlayers.add(temp);
    }

    public int Career(Player p) {
        for (player temp : onlinePlayers
        ) {
            if (temp.p == p) {
                return temp.career;
            }
        }
        return 0;
    }

    public void Career(Player p, int career) {
        for (player temp : onlinePlayers
        ) {
            if (temp.p == p) {
                temp.career = career;
            }
        }
    }

    public int ready(Player p, boolean yes) {
        for (player temp : onlinePlayers
        ) {
            if (temp.p == p) {
                temp.ready = true;
                return 1;
            }
        }
        return 3;
    }

    public int ready(Player p) {
        for (player temp : onlinePlayers
        ) {
            if (temp.p == p) {
                return temp.ready ? 1 : 0;
            }
        }
        return 3;
    }

    public boolean saveDataToFile() {
        for (player temp : onlinePlayers
        ) {
            saveData s = new saveData();
            if (!s.saveData(temp.p.getName(), temp.career)) {
                System.out.println("储存" + temp.p.getName() + "数据时出问题！");
                return false;
            }
        }
        return true;
    }

    public static void getnext(player p) {
        int pIndex = GameStatus.onlinePlayers.indexOf(p);//The player whose target is going to change
        int index=-1;
        if (p.compassTarget == null) {
            for (player temp : GameStatus.onlinePlayers
            ) {
                if (temp != p) {
                    index = GameStatus.onlinePlayers.indexOf(temp);
                    break;
                }
            }
        } else {
            index = GameStatus.onlinePlayers.indexOf(p.compassTarget);//The origin target index
            int OnlinePlayerNum = GameStatus.onlinePlayers.size();
            if (OnlinePlayerNum < 1) {
            } else {
                if (index + 1 == OnlinePlayerNum) {
                    if (pIndex == 0) {
                        index = 1;
                    }
                    else index=0;
                } else {
                    if (index + 1 == pIndex) {
                        if (index + 2 == OnlinePlayerNum) {
                            index = 0;
                        } else
                            index += 2;

                    } else index += 1;
                }
            }
        }
        if(index!=-1)
            GameStatus.onlinePlayers.get(pIndex).compassTarget = GameStatus.onlinePlayers.get(index);
        else{
            GameStatus.onlinePlayers.get(pIndex).compassTarget=null;
        }

    }
}
