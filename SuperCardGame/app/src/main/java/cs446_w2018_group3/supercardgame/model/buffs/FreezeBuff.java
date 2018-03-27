package cs446_w2018_group3.supercardgame.model.buffs;

import cs446_w2018_group3.supercardgame.model.Effect;
import cs446_w2018_group3.supercardgame.model.Translate;
import cs446_w2018_group3.supercardgame.model.player.Player;

/**
 * Created by Yakumo on 3/27/2018.
 */

public class FreezeBuff extends Buff {
    private static final Translate.BuffType TYPE = Translate.BuffType.Freeze;
    private static final int FREEZE_BUFF_AP_DECREASE = 2;

    public FreezeBuff(int turns) {
        super(TYPE, turns);
    }

    public FreezeBuff(int id, Translate.BuffType type, int remainingTurns) {
        super(id, type, remainingTurns);
    }

    @Override
    public void applyBuff(Player player) {
        super.applyBuff(player);
        Effect.decreaseAP(player, player, FREEZE_BUFF_AP_DECREASE);
    }
}
