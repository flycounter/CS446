package cs446_w2018_group3.supercardgame.model.cards;

import cs446_w2018_group3.supercardgame.model.Player;
import cs446_w2018_group3.supercardgame.gamelogic.Effect;

/**
 * Created by yandong on 2018-02-25.
 */

public class WaterCard extends ElementCard {
    public WaterCard () {
        this.id = 0;
        this.label = "Water";
        this.level = 1;
        this.damage = 1;
    }

    public void apply(Player subject, Player object) {
        Effect.dealDamageEffect(subject, object, damage);
    }
}
