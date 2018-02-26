package cs446_w2018_group3.supercardgame.util.events.actions;

import java.util.List;

/**
 * Created by JarvieK on 2018/2/25.
 */

public class CombineAction implements IAction {
    List<Integer> cardIndices;

    public CombineAction(List<Integer> cardIndices) {
        super();
        this.cardIndices = cardIndices;
    }

    public List<Integer> getCardIndices() {
        return this.cardIndices;
    }
}
