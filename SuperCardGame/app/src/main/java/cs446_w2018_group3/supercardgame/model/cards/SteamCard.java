package cs446_w2018_group3.supercardgame.model.cards;

import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.model.Translate;
import cs446_w2018_group3.supercardgame.model.buffs.*;

/**
 * Created by yandong on 2018-03-01.
 */

public class SteamCard extends ElementCard {
    public SteamCard () {
        super(Translate.CardType.Steam, 2, 0);
    }

    public void apply(Player subject, Player object) {
        Buff newBuff = new DodgeBuff(Translate.BuffType.Dodge, subject, object);
        subject.addBuff(newBuff);
    }
}
