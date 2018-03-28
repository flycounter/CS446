package cs446_w2018_group3.supercardgame.model.cards.element;

import cs446_w2018_group3.supercardgame.model.Effect;
import cs446_w2018_group3.supercardgame.model.Translate;
import cs446_w2018_group3.supercardgame.model.buffs.BurningBuff;
import cs446_w2018_group3.supercardgame.model.player.Player;

/**
 * Created by yandong on 2018-03-01.
 */

public class LavaCard extends ElementCard {
    private static final int LEVEL = 2;
    private static final int DAMAGE = 3;

    private static final int LAVA_BURN_DURATION = 2;

    public LavaCard() {
        super(Translate.CardType.Lava, LEVEL, DAMAGE);
    }

    public LavaCard(int id) {
        super(Translate.CardType.Lava, LEVEL, DAMAGE, id);
    }


    public void apply(Player subject, Player object) {
        Effect.dealDamageEffect(subject, object, getDamage());
        object.addBuff(new BurningBuff(LAVA_BURN_DURATION));
    }
}
