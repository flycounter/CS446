package cs446_w2018_group3.supercardgame.model.cards;

import cs446_w2018_group3.supercardgame.model.Effect;
import cs446_w2018_group3.supercardgame.model.Player;
import cs446_w2018_group3.supercardgame.model.Translate;
import cs446_w2018_group3.supercardgame.model.buffs.Buff;
import cs446_w2018_group3.supercardgame.model.buffs.DamageOverTimeBuff;

/**
 * Created by yandong on 2018-02-25.
 */

public class FireCard extends ElementCard {
    public FireCard () {
        super(Translate.CardType.Fire, 1, 1);
    }

    public void apply(Player subject, Player object) {
        Buff newBuff = new DamageOverTimeBuff(Translate.BuffType.Burn, subject, object, 2, 1);
        object.addBuff(newBuff);
    }
}
