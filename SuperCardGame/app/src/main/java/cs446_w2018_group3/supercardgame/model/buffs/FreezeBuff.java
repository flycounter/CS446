package cs446_w2018_group3.supercardgame.model.buffs;

import cs446_w2018_group3.supercardgame.model.Effect;
import cs446_w2018_group3.supercardgame.model.Translate;
import cs446_w2018_group3.supercardgame.model.player.Player;

/**
 * Created by Yakumo on 3/27/2018.
 */

public class FreezeBuff extends Buff {
    private static final int FREEZE_BUFF_AP_DECREASE = 2;
    public FreezeBuff(Translate.BuffType label, Player subject, Player object, int turns) {
        super( label, subject ,object ,turns );
    }
    public int applyBuff() {
        Effect.decreaseAP(subject,object,FREEZE_BUFF_AP_DECREASE);
        return super.applyBuff();
    }
}
