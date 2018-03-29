package cs446_w2018_group3.supercardgame.model.cards.element;

import cs446_w2018_group3.supercardgame.model.Effect;
import cs446_w2018_group3.supercardgame.model.Translate;
import cs446_w2018_group3.supercardgame.model.player.Player;

/**
 * Created by yandong on 2018-03-01.
 */

public class MudCard extends ElementCard {
    private static final int LEVEL = 2;
    private static final int DAMAGE = 0;

    public MudCard() {
        super(Translate.CardType.Mud, LEVEL, DAMAGE);
    }

    public MudCard(int id) {
        super(Translate.CardType.Mud, LEVEL, DAMAGE, id);
    }


    public void apply(Player subject, Player object) {
        Effect.decreaseAP(subject, object, 3);
    }
}