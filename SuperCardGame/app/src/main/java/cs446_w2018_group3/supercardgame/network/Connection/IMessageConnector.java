package cs446_w2018_group3.supercardgame.network.Connection;

import org.java_websocket.WebSocket;

/**
 * Created by JarvieK on 2018/3/25.
 */

public interface IMessageConnector {
    void onMessage(WebSocket conn, String message);
}
