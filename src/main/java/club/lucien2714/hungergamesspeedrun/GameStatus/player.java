package club.lucien2714.hungergamesspeedrun.GameStatus;

import org.bukkit.entity.Player;

import java.sql.Timestamp;

public class player {
    public Player p;
    public int career;
    public boolean ready;
    public Timestamp cd;
    public boolean resetCD()
    {
        this.cd=new Timestamp(System.currentTimeMillis());
        return true;
    }
    public long AbilityCD(){
        Timestamp temp=new Timestamp(System.currentTimeMillis());
        return temp.getTime()-this.cd.getTime();
    }
    public boolean useAbility(){
        this.p.
    }
}
