package cs446_w2018_group3.supercardgame.model.buffs;

import cs446_w2018_group3.supercardgame.gamelogic.Effect;
import cs446_w2018_group3.supercardgame.model.IModel;
import cs446_w2018_group3.supercardgame.model.Player;

/**
 * Created by Yakumo on 2/24/2018.
 */

public abstract class Buff implements IModel {
    protected int remainingTurns;
    protected int id;
    protected String label;
    protected Player subject, object;

    public Buff(int id, String label,Player subject, Player object, int turns) {
        this.id = id;
        this.label = label;
        this.subject = subject;
        this.object = object;
        remainingTurns = turns;
    }

    public int getId() { return id; }
    public String getLabel() { return label; }
    public int getRemainingTurns() { return remainingTurns; }

    /*
    * applyBuff applies the buff by one turn and returns the number of remaining turns
    * */
    public abstract int applyBuff();

}
