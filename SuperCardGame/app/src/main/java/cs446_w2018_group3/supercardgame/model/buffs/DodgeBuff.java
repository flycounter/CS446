package cs446_w2018_group3.supercardgame.model.buffs;

import cs446_w2018_group3.supercardgame.model.Effect;
import cs446_w2018_group3.supercardgame.model.Player;
import cs446_w2018_group3.supercardgame.model.Translate;

/**
 * Created by yandong on 2018-03-01.
 */

public class DodgeBuff extends Buff {
    public DodgeBuff(Translate.BuffType label, Player subject, Player object) {
        super( label, subject ,object ,1 );
    }

    public int applyBuff() {
        remainingTurns -= 1;
        return remainingTurns;
    }
}
