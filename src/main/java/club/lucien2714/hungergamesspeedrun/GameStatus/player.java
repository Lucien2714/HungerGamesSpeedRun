package club.lucien2714.hungergamesspeedrun.GameStatus;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class player {
    public PotionEffectType buffs[] = {PotionEffectType.INCREASE_DAMAGE, PotionEffectType.SPEED, PotionEffectType.FAST_DIGGING};
    private int buffTime[] = {10, 10, 15};

    public Player p;
    public int career;
    public boolean ready=false;
    public int cd=0;
    public player compassTarget=null;

}
