package cs446_w2018_group3.supercardgame.model.buffs;

import cs446_w2018_group3.supercardgame.model.Effect;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.model.Translate;

/**
 * Created by Yakumo on 2/25/2018.
 */

public class DamageOverTimeBuff extends Buff {
    private int damage;
    public DamageOverTimeBuff(Translate.BuffType label, Player subject, Player object, int turns, int dmg) {
        // TODO the buffType is fixed
        super( label, subject ,object ,turns );
        damage = dmg;
    }
    public int applyBuff() {
        remainingTurns -= 1;
        Effect.dealDamageEffect(subject, object, damage);
        return remainingTurns;
    }
}
