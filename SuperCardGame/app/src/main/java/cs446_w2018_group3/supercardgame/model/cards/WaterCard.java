package cs446_w2018_group3.supercardgame.model.cards;

import cs446_w2018_group3.supercardgame.model.buffs.WetBuff;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.model.Effect;
import cs446_w2018_group3.supercardgame.model.Translate;

/**
 * Created by yandong on 2018-02-25.
 */

public class WaterCard extends ElementCard {
    public WaterCard() {
        super(Translate.CardType.Water, 1, 1);
    }

    private static final int WATER_WET_DURATION = 1;

    public void apply(Player subject, Player object) {
        Effect.dealDamageEffect(subject, object, damage);
        object.addBuff(new WetBuff(WATER_WET_DURATION));
    }
}
