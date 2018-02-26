package cs446_w2018_group3.supercardgame.model.buffs;

import android.widget.TextView;

import cs446_w2018_group3.supercardgame.info.BuffInfo;
import cs446_w2018_group3.supercardgame.model.IModel;
import cs446_w2018_group3.supercardgame.model.Player;
import cs446_w2018_group3.supercardgame.model.Translate;

/**
 * Created by Yakumo on 2/24/2018.
 */

public abstract class Buff implements IModel {
    protected int remainingTurns;
    protected int buffId;
    protected Translate.BuffType buffType;
    protected Player subject, object;

    public Buff(int id, Translate.BuffType label, Player subject, Player object, int turns) {
        this.buffId = id;
        this.buffType = label;
        this.subject = subject;
        this.object = object;
        remainingTurns = turns;
    }

    public int getId() {
        return buffId;
    }

    public Translate.BuffType getLabel() {
        return buffType;
    }

    public int getRemainingTurns() {
        return remainingTurns;
    }

    public BuffInfo getBuffInfo() {
        return new BuffInfo(buffId, buffType);
    }

    public abstract int applyBuff();

}
