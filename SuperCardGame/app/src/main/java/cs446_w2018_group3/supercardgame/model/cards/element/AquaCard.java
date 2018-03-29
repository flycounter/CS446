package cs446_w2018_group3.supercardgame.model.cards.element;

import cs446_w2018_group3.supercardgame.model.Effect;
import cs446_w2018_group3.supercardgame.model.Translate;
import cs446_w2018_group3.supercardgame.model.buffs.WetBuff;
import cs446_w2018_group3.supercardgame.model.player.Player;

/**
 * Created by yandong on 2018-02-25.
 */

public class AquaCard extends ElementCard {
    private static final int LEVEL = 2;
    private static final int DAMAGE = 3;
    private static final int AQUA_WET_DURATION = 2;

    public AquaCard() {
        super(Translate.CardType.Aqua, LEVEL, DAMAGE);
    }

    public AquaCard(int id) {
        super(Translate.CardType.Aqua, LEVEL, DAMAGE, id);
    }

    public void apply(Player subject, Player object) {
        Effect.dealDamageEffect(subject, object, getDamage());
        object.addBuff(new WetBuff(AQUA_WET_DURATION));
    }
}
