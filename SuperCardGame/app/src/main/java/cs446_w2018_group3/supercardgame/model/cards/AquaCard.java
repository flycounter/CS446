package cs446_w2018_group3.supercardgame.model.cards;

import cs446_w2018_group3.supercardgame.model.Effect;
import cs446_w2018_group3.supercardgame.model.Player;
import cs446_w2018_group3.supercardgame.model.Translate;

/**
 * Created by yandong on 2018-02-25.
 */

public class AquaCard extends ElementCard {

    public AquaCard () {
        super(Translate.CardType.Aqua, 2, 3);
    }

    public void apply(Player subject, Player object) {
        Effect.dealDamageEffect(subject, object, damage);
    }
}
