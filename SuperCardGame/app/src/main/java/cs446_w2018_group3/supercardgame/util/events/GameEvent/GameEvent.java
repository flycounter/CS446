package cs446_w2018_group3.supercardgame.util.events.GameEvent;

/**
 * Created by JarvieK on 2018/2/23.
 */

public class GameEvent {
    public enum EventCode {
        PLAYER_COMBINE_ELEMENT, PLAYER_USE_CARD, PLAYER_START_TURN, PLAYER_END_TURN,
        PLAYER_ADD,
        GAME_END
    }

    private final EventCode eventCode;

    public GameEvent(EventCode eventCode) {
        this.eventCode = eventCode;
    }

    public EventCode getEventCode() {
        return eventCode;
    }
}
