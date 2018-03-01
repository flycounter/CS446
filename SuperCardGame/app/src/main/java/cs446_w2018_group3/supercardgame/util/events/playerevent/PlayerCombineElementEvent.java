package cs446_w2018_group3.supercardgame.util.events.playerevent;

import java.util.List;

/**
 * Created by Yakumo on 2/26/2018.
 */

public class PlayerCombineElementEvent extends BasePlayerEvent {
    private final List<Integer> cardIds;

    public PlayerCombineElementEvent(int playerId, List<Integer> cardIds) {
        super(playerId);
        this.cardIds = cardIds;
    }

    public List<Integer> getCardIds() { return cardIds; }
}
