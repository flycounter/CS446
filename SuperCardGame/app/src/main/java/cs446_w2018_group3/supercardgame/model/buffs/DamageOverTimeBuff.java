package cs446_w2018_group3.supercardgame.model.buffs;

import cs446_w2018_group3.supercardgame.gamelogic.Effect;
import cs446_w2018_group3.supercardgame.model.Player;

/**
 * Created by Yakumo on 2/25/2018.
 */

public class DamageOverTimeBuff extends Buff {
    private int damage;
    public DamageOverTimeBuff(int id, String label, Player subject, Player object, Effect.BaseEffect eff, int turns, int dmg) {
        super(id,label,subject,object,eff,turns);
        damage = dmg;
    }
    public int applyBuff() {
        remainingTurns -= 1;
        buffEffect.applyEffect(subject, object);
//        Effect.dealDamageEffect(object, damage);
        return remainingTurns;
    }
}
