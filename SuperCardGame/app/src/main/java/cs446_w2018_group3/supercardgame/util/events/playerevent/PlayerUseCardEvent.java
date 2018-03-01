package cs446_w2018_group3.supercardgame.util.events.playerevent;

/**
 * Created by Yakumo on 2/26/2018.
 */

public class PlayerUseCardEvent extends BasePlayerEvent {
    private final int targetId;
    private final int cardId;

    public PlayerUseCardEvent (int playerId, int targetId, int cardId) {
        super(playerId);
        this.targetId = targetId;
        this.cardId = cardId;
    }

    public int getCardId() {
        return cardId;
    }

    public int getTargetId() { return targetId; }
}
