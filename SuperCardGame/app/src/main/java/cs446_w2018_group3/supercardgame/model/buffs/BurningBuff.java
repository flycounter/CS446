package cs446_w2018_group3.supercardgame.model.buffs;

import cs446_w2018_group3.supercardgame.model.Effect;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.model.Translate;

/**
 * Created by Yakumo on 2/25/2018.
 */

public class BurningBuff extends Buff {
    private int damage;
    public BurningBuff(Translate.BuffType label, Player subject, Player object, int turns, int dmg) {
        // TODO the buffType is fixed
        super( label, subject ,object ,turns );
        damage = dmg;
    }
    public int applyBuff() {
        Effect.dealDamageEffect(subject, object, damage);
        return super.applyBuff();
    }
}
