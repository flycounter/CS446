package cs446_w2018_group3.supercardgame.model.cards;

import cs446_w2018_group3.supercardgame.model.Effect;
import cs446_w2018_group3.supercardgame.model.Player;
import cs446_w2018_group3.supercardgame.model.Translate;

/**
 * Created by yandong on 2018-03-01.
 */

public class RockCard extends ElementCard {

    public RockCard () {
        super(Translate.CardType.Rock, 2, 3);
    }

    public void apply(Player subject, Player object) {

    }
}
