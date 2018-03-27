package cs446_w2018_group3.supercardgame.model.buffs;

import cs446_w2018_group3.supercardgame.model.Translate;
import cs446_w2018_group3.supercardgame.model.player.Player;

/**
 * Created by Yakumo on 3/27/2018.
 */

public class WetBuff extends Buff {
    public WetBuff(Translate.BuffType label, Player subject, Player object, int turns) {
        super(label, subject, object, turns);
    }
}
