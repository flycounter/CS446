package cs446_w2018_group3.supercardgame.model.cards.element;

import cs446_w2018_group3.supercardgame.model.Effect;
import cs446_w2018_group3.supercardgame.model.Translate;
import cs446_w2018_group3.supercardgame.model.player.Player;

/**
 * Created by yandong on 2018-02-25.
 */

public class FireCard extends ElementCard {
    private static final int LEVEL = 1;
    private static final int DAMAGE = 1;

    public FireCard() {
        super(Translate.CardType.Fire, LEVEL, DAMAGE);
    }

    public FireCard(int id) {
        super(Translate.CardType.Fire, LEVEL, DAMAGE, id);
    }

    public void apply(Player subject, Player object) {
        Effect.dealDamageEffect(subject, object, getDamage());
    }
}
