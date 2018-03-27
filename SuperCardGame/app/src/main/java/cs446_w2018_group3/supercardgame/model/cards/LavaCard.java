package cs446_w2018_group3.supercardgame.model.cards;

import cs446_w2018_group3.supercardgame.model.Effect;
import cs446_w2018_group3.supercardgame.model.buffs.BurningBuff;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.model.Translate;

/**
 * Created by yandong on 2018-03-01.
 */

public class LavaCard extends ElementCard {
    private static final int LAVA_BURN_DURATION = 2;

    public LavaCard() {
//        super(Translate.CardType.Lava, 2, 3);
    }

    public void apply(Player subject, Player object) {
        Effect.dealDamageEffect(subject, object, damage);
        object.addBuff(new BurningBuff(LAVA_BURN_DURATION));
    }
}
