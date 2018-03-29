package cs446_w2018_group3.supercardgame.util.events.GameEvent.playerevent.actionevent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yakumo on 2/26/2018.
 */

public class PlayerCombineElementEvent extends ActionEvent {
    private final List<Integer> cardIds;

    public PlayerCombineElementEvent(int playerId, List<Integer> cardIds) {
        super(EventCode.PLAYER_COMBINE_ELEMENT, playerId);
        this.cardIds = new ArrayList<>();
        this.cardIds.addAll(cardIds);
    }

    public List<Integer> getCardIds() { return cardIds; }
}
