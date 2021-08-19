package club.lucien2714.hungergamesspeedrun.GameStatus;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.sql.Timestamp;

public class player {
    public PotionEffectType buffs[] = {PotionEffectType.INCREASE_DAMAGE, PotionEffectType.SPEED, PotionEffectType.FAST_DIGGING};
    private int buffTime[] = {10, 10, 15};
    private int cds[] = {10, 10, 15};
    public Player p;
    public int career;
    public boolean ready=false;
    public Timestamp cd;

    public boolean resetCD() {
        this.cd = new Timestamp(System.currentTimeMillis());
        return true;
    }

    public long AbilityCD() {
        Timestamp temp = new Timestamp(System.currentTimeMillis());
        return temp.getTime() - this.cd.getTime();
    }

    public boolean useAbility() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if (this.cd.getTime() - now.getTime() <= 0) {
            this.p.addPotionEffect(new PotionEffect(buffs[this.career - 1], buffTime[this.career - 1], 1));
            this.cd=new Timestamp(System.currentTimeMillis()+cds[this.career-1]* 1000L);
            return true;
        }
        return false;
    }
}
