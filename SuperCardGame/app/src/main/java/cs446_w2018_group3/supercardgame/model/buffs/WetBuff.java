package cs446_w2018_group3.supercardgame.model.buffs;

import cs446_w2018_group3.supercardgame.model.Translate;
import cs446_w2018_group3.supercardgame.model.player.Player;

/**
 * Created by Yakumo on 3/27/2018.
 */

public class WetBuff extends Buff {
    private static final Translate.BuffType TYPE = Translate.BuffType.Wet;

    public WetBuff(int turns) {
        super(TYPE, turns);
    }

    public WetBuff(int id, Translate.BuffType type, int remainingTurns) {
        super(id, type, remainingTurns);
    }
}
