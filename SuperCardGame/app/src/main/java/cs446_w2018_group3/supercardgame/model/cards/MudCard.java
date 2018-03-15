package cs446_w2018_group3.supercardgame.model.cards;

import cs446_w2018_group3.supercardgame.model.Effect;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.model.Translate;

/**
 * Created by yandong on 2018-03-01.
 */

public class MudCard extends ElementCard {
    public MudCard () {
        super(Translate.CardType.Mud, 2, 0);
    }

    public void apply(Player subject, Player object) {
        Effect.decreaseAP(subject, object, 3);
    }
}