package cs446_w2018_group3.supercardgame.model.cards.element;

import cs446_w2018_group3.supercardgame.model.Effect;
import cs446_w2018_group3.supercardgame.model.Translate;
import cs446_w2018_group3.supercardgame.model.player.Player;

/**
 * Created by yandong on 2018-02-25.
 */

public class AirCard extends ElementCard {
    private static final int LEVEL = 1;
    private static final int DAMAGE = 2;

    public AirCard() {
        super(Translate.CardType.Air, LEVEL, DAMAGE);
    }

    public AirCard(int id) {
        super(Translate.CardType.Air, LEVEL, DAMAGE, id);
    }

    public void apply(Player subject, Player object) {
        Effect.dealDamageEffect(subject, object, getDamage());
    }
}