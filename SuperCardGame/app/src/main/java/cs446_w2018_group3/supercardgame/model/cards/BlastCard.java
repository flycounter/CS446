package cs446_w2018_group3.supercardgame.model.cards;

import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.model.Translate;

/**
 * Created by yandong on 2018-03-01.
 */

public class BlastCard extends ElementCard {

    public BlastCard () {
        super(Translate.CardType.Blast, 2, 3);
    }

    public void apply(Player subject, Player object) {

    }
}