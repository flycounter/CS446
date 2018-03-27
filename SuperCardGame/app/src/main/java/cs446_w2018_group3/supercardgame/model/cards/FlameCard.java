package cs446_w2018_group3.supercardgame.model.cards;

import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.model.Translate;
import cs446_w2018_group3.supercardgame.model.buffs.Buff;
import cs446_w2018_group3.supercardgame.model.buffs.BurningBuff;

/**
 * Created by yandong on 2018-03-01.
 */

public class FlameCard extends ElementCard {
    private static final int FLAME_BURN_DURATION = 2;

    public FlameCard () {
//        super(Translate.CardType.Flame, 2, 2);
    }

    public void apply(Player subject, Player object) {
        Buff newBuff = new BurningBuff(FLAME_BURN_DURATION);
        object.addBuff(newBuff);
    }
}
