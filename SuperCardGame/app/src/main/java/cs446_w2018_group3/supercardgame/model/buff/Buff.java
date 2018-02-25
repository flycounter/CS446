package cs446_w2018_group3.supercardgame.model.buff;

import cs446_w2018_group3.supercardgame.gamelogic.Effect;
import cs446_w2018_group3.supercardgame.model.IModel;
import cs446_w2018_group3.supercardgame.model.Player;

/**
 * Created by Yakumo on 2/24/2018.
 */

public class Buff implements IModel {
    private int remainingTurns;
    //private Effect.BaseEffect buffEffect;
    private int id;
    private String label;
    private Player subject, object;

    public Buff(int id, String label,Player subject, Player object, int turns) {
        this.id = id;
        this.label = label;
        this.subject = subject;
        this.object = object;
        remainingTurns = turns;
        //buffEffect = eff;
    }

    public int getId() { return id; }
    public String getLabel() { return label; }
    public int getRemainingTurns() { return remainingTurns; }
    //public Effect.BaseEffect getBuffEffect() { return buffEffect; }

    /*
    * applyBuff applies the buff by one turn and returns the number of remaining turns
    * */

    public int applyBuff() {
        remainingTurns -= 1;
        //buffEffect.applyEffect( subject, object );
        return remainingTurns;
    }

}
