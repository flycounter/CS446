package cs446_w2018_group3.supercardgame.model.buffs;

import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.model.Translate;

/**
 * Created by yandong on 2018-03-01.
 */

public class DodgeBuff extends Buff {
    private static final Translate.BuffType TYPE = Translate.BuffType.Dodge;
    public DodgeBuff(int turns) {
        super(TYPE, turns);
    }

    public DodgeBuff(int id, Translate.BuffType type, int remainingTurns) {
        super(id, type, remainingTurns);
    }

    @Override
    public void applyBuff(Player player) {
        super.applyBuff(player);
    }
}
