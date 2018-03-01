package cs446_w2018_group3.supercardgame.util.events;

/**
 * Created by JarvieK on 2018/2/23.
 */

public class GameEvent {
    public enum EventCode {
        PLAYER_COMBINE_ELEMENT, PLAYER_USE_CARD, PLAYER_START_TURN, PLAYER_END_TURN
    }

    private final EventCode eventCode;

    public GameEvent(EventCode eventCode) {
        this.eventCode = eventCode;
    }

    public EventCode getEventCode() {
        return eventCode;
    }
}
