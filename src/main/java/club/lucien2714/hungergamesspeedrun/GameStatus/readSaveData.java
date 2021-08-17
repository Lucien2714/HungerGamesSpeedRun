package club.lucien2714.hungergamesspeedrun.GameStatus;

import club.lucien2714.hungergamesspeedrun.saveData.playerdata;
import club.lucien2714.hungergamesspeedrun.saveData.saveData;

import java.util.ArrayList;
import java.util.List;

public class readSaveData {//gets data from json file
    public static List<playerdata> allPlayersStored=new ArrayList<playerdata>();
    public void getPlayers(){
        GameStatus game=new GameStatus();
        saveData s=new saveData();
        allPlayersStored=s.getPlayers();
        for (player p:game.onlinePlayers
             ) {
            for (playerdata temp:allPlayersStored
                 ) {
                if(p.p.getName()==temp.player){
                    game.Career(p.p,temp.career);
                }
            }
        }
    }
}
