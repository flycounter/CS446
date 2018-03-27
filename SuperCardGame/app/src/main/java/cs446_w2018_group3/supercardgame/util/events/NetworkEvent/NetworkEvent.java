package cs446_w2018_group3.supercardgame.util.events.NetworkEvent;

import cs446_w2018_group3.supercardgame.model.network.INetworkConnector;

/**
 * Created by JarvieK on 2018/3/23.
 */

public class NetworkEvent {
    public enum EventCode {
        USER_JOINED, USER_LOST, USER_LEFT,
        GAME_SYNC
    }

    private final EventCode eventCode;
    private final INetworkConnector mNetworkConnector;

    public NetworkEvent(EventCode eventCode, INetworkConnector mNetworkConnector) {
        this.eventCode = eventCode;
        this.mNetworkConnector = mNetworkConnector;
    }

    public EventCode getEventCode() {
        return eventCode;
    }
    public INetworkConnector getNetworkConnector() {
        return mNetworkConnector;
    }
}
