package cs446_w2018_group3.supercardgame.model.network;

import cs446_w2018_group3.supercardgame.util.events.GameEvent.GameEvent.EventCode;

/**
 * Created by JarvieK on 2018/3/25.
 */

public class PayloadInfo {
    // wrapper for payload, used by gson
    public enum Type {
        GAME_EVENT, SYNC_DATA, PLAYER_DATA, FIELD_DATA,
        ACK_ADD_PLAYER, ACK_GAME_SYNC
    }
    private final Type type;
    private final EventCode eventCode;
    private final String payload;

    public Type getType() {
        return type;
    }

    public String getPayload() {
        return payload;
    }

    public PayloadInfo(Type type, String payload) {
        this.type = type;
        this.eventCode = null;
        this.payload = payload;
    }

    public PayloadInfo(Type type, EventCode eventCode, String payload) {
        this.type = type;
        this.eventCode = eventCode;
        this.payload = payload;
    }

    public EventCode getEventCode() {
        return eventCode;
    }
}
