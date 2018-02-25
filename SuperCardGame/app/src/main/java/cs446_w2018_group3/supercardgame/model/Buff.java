package cs446_w2018_group3.supercardgame.model;

import cs446_w2018_group3.supercardgame.gamelogic.Handler;

/**
 * Created by Yakumo on 2/24/2018.
 */

public class Buff implements IModel {
    private int remainingTurns;
    private Handler buffHandler;
    private int id;
    private String label;
    private Player subject, object;

    public Buff(int id, String label,Player subject, Player object, Handler h, int turns) {
        this.id = id;
        this.label = label;
        this.subject = subject;
        this.object = object;
        remainingTurns = turns;
        buffHandler = h;
    }

    public int getId() { return id; }
    public String getLabel() { return label; }
    public int getRemainingTurns() { return remainingTurns; }
    public Handler getBuffHandler() { return buffHandler; }

    /*
    * applyBuff applies the buff by one turn and returns the number of remaining turns
    * */
    public int applyBuff() {
        remainingTurns -= 1;
        return remainingTurns;
    }

}
