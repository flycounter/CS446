package cs446_w2018_group3.supercardgame.model.cards.element;

import cs446_w2018_group3.supercardgame.model.Translate;
import cs446_w2018_group3.supercardgame.model.buffs.WetBuff;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.model.Effect;

/**
 * Created by yandong on 2018-02-25.
 */

public class WaterCard extends ElementCard {
    private static final int LEVEL = 1;
    private static final int DAMAGE = 1;

    public WaterCard() {
        super(Translate.CardType.Water, LEVEL, DAMAGE);
    }

    public WaterCard(int id) {
        super(Translate.CardType.Water, LEVEL, DAMAGE, id);
    }

    private static final int WATER_WET_DURATION = 1;

    public void apply(Player subject, Player object) {
        Effect.dealDamageEffect(subject, object, getDamage());
        object.addBuff(new WetBuff(WATER_WET_DURATION));
    }
}
