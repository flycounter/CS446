package cs446_w2018_group3.supercardgame.model.cards.element;

import cs446_w2018_group3.supercardgame.model.Effect;
import cs446_w2018_group3.supercardgame.model.Translate;
import cs446_w2018_group3.supercardgame.model.player.Player;

/**
 * Created by yandong on 2018-03-01.
 */

public class SandCard extends ElementCard {
    private static final int LEVEL = 2;
    private static final int DAMAGE = 0;

    public SandCard() {
        super(Translate.CardType.Sand, LEVEL, DAMAGE);
    }

    public SandCard(int id) {
        super(Translate.CardType.Sand, LEVEL, DAMAGE, id);
    }

    public void apply(Player subject, Player object) {
        Effect.addShield(subject, object, 3);
    }
}
