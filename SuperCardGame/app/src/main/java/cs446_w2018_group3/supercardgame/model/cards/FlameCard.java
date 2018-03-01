package cs446_w2018_group3.supercardgame.model.cards;

import cs446_w2018_group3.supercardgame.model.Player;
import cs446_w2018_group3.supercardgame.model.Translate;
import cs446_w2018_group3.supercardgame.model.buffs.Buff;
import cs446_w2018_group3.supercardgame.model.buffs.DamageOverTimeBuff;

/**
 * Created by yandong on 2018-03-01.
 */

public class FlameCard extends ElementCard {

    public FlameCard () {
        super(Translate.CardType.Flame, 2, 2);
    }

    public void apply(Player subject, Player object) {
        Buff newBuff = new DamageOverTimeBuff(Translate.BuffType.Burn, subject, object, 2, damage);
        object.addBuff(newBuff);
    }
}
