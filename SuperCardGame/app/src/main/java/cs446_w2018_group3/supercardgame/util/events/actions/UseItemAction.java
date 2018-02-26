package cs446_w2018_group3.supercardgame.util.events.actions;

/**
 * Created by JarvieK on 2018/2/25.
 */

public class UseItemAction implements IAction {
    private final Integer cardIndex;

    public UseItemAction(Integer cardIndex) {
        this.cardIndex = cardIndex;
    }

    public Integer getCardIndex() {
        return cardIndex;
    }
}
