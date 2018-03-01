package cs446_w2018_group3.supercardgame.model.buffs;

import cs446_w2018_group3.supercardgame.model.Player;
import cs446_w2018_group3.supercardgame.model.Translate;

/**
 * Created by Yakumo on 2/24/2018.
 */

public abstract class Buff extends BuffInfo {

    protected int remainingTurns;
    protected Translate.BuffType buffType;
    protected Player subject, object;

    public Buff(Translate.BuffType label, Player subject, Player object, int turns) {
        super (label);
        this.subject = subject;
        this.object = object;
        remainingTurns = turns;
        this.id++;
    }

    public int getRemainingTurns() {
        return remainingTurns;
    }

//    public BuffInfo getBuffInfo() {
//        return new BuffInfo(buffId, buffType);
//    }

    public abstract int applyBuff();

}
