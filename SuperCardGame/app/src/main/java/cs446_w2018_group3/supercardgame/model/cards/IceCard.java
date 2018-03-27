package cs446_w2018_group3.supercardgame.model.cards;

import cs446_w2018_group3.supercardgame.model.Effect;
import cs446_w2018_group3.supercardgame.model.buffs.Buff;
import cs446_w2018_group3.supercardgame.model.buffs.FreezeBuff;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.model.Translate;

/**
 * Created by yandong on 2018-03-01.
 */

public class IceCard extends ElementCard {

    public IceCard() {
        super(Translate.CardType.Ice, 2, 2);
    }

    private static final int ICE_FREEZE_DURATION = 1;

    public void apply(Player subject, Player object) {
        Effect.dealDamageEffect(subject, object, damage);
        for (Buff b : object.getBuffs()) {
            if (b.getBuffType() == Translate.BuffType.Wet) {
                object.addBuff(new FreezeBuff(ICE_FREEZE_DURATION));
                break;
            }
        }
    }
}
