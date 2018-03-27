package cs446_w2018_group3.supercardgame.util.events.GameEvent.stateevent;

/**
 * Created by JarvieK on 2018/2/26.
 */

// TurnStartEvent is sent to either UI or AI player
public class TurnStartEvent extends StateEvent {
    public TurnStartEvent(int playerId) {
        super(EventCode.PLAYER_START_TURN, playerId);
    }
}
