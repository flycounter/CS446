package cs446_w2018_group3.supercardgame.model.buffs;

import cs446_w2018_group3.supercardgame.util.events.payload.BuffPayload;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.model.Translate;

/**
 * Created by Yakumo on 2/24/2018.
 */

public abstract class Buff {
    static int id = 0;
    protected int remainingTurns;
    protected int buffId;
    protected Translate.BuffType buffType;
    protected Player subject, object;

    public Buff(Translate.BuffType label, Player subject, Player object, int turns) {
        this.buffId = id;
        this.buffType = label;
        this.subject = subject;
        this.object = object;
        remainingTurns = turns;
        this.id++;
    }

    public int getBuffId() {
        return buffId;
    }

    public Translate.BuffType getLabel() {
        return buffType;
    }

    public int getRemainingTurns() {
        return remainingTurns;
    }

    public BuffPayload getBuffInfo() {
        return new BuffPayload(buffId, buffType);
    }

    public abstract int applyBuff();

    @Override
    public boolean equals(Object that) {
        return (that instanceof Buff && ((Buff) that).getBuffId() == this.getBuffId());
    }

}
