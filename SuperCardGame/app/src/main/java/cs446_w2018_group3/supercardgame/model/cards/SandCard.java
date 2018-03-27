package cs446_w2018_group3.supercardgame.model.cards;

import cs446_w2018_group3.supercardgame.model.Effect;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.model.Translate;

/**
 * Created by yandong on 2018-03-01.
 */

public class SandCard extends ElementCard {
    public SandCard () {
//        super(Translate.CardType.Sand, 2, 0);
    }

    public void apply(Player subject, Player object) {
        Effect.addShield(subject, object, 3);
    }
}
