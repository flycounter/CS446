package cs446_w2018_group3.supercardgame.util.events.playerevent;

/**
 * Created by JarvieK on 2018/2/26.
 */

public class PlayerEndTurnEvent extends BasePlayerEvent {
    public PlayerEndTurnEvent(int playerId) {
        super(playerId);
    }
}
