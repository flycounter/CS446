package cs446_w2018_group3.supercardgame.model.buffs;

import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.util.events.payload.BuffPayload;
import cs446_w2018_group3.supercardgame.model.Translate;

/**
 * Created by Yakumo on 2/24/2018.
 */

// TODO: factory pattern

public abstract class Buff {
    private static int index = 0;

    private int id;
    private Translate.BuffType buffType;
    private int remainingTurns;

    public Buff(int id, Translate.BuffType label, int turns) {
        this.id = id;
        this.buffType = label;
        remainingTurns = turns;
    }

    public Buff(Translate.BuffType label, int turns) {
        this.id = index;
        this.buffType = label;
        remainingTurns = turns;
        Buff.index++;
    }

    public int getId() {
        return id;
    }

    public Translate.BuffType getBuffType() {
        return buffType;
    }

    public int getRemainingTurns() {
        return remainingTurns;
    }

    public BuffPayload getBuffInfo() {
        return new BuffPayload(id, buffType);
    }

    public void applyBuff(Player player) {
        remainingTurns -= 1;
    }

    @Override
    public boolean equals(Object that) {
        return (that instanceof Buff && ((Buff) that).getId() == this.getId());
    }
}
