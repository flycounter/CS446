package cs446_w2018_group3.supercardgame.network.Connection;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

import cs446_w2018_group3.supercardgame.model.network.ConnInfo;

/**
 * Created by JarvieK on 2018/3/23.
 */

public class P2pClient implements Connectable {
    private static final String TAG = P2pClient.class.getName();
    private ConnectionListener mConnectionListener;
    private final WebSocketClient ws;

    // TODO: singleton

    public P2pClient(ConnInfo connInfo) {
        URI uri = URI.create(String.format("ws://%s:%s", connInfo.getHost().getHostAddress(), connInfo.getPort()));
        Log.i(TAG, "connection string: " + uri);
        ws = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                Log.i(TAG, "connected to host: " + handshakedata);
                try { Thread.sleep(1000); /* delay simulation */ }
                catch (Exception e) {}

                mConnectionListener.onConnected();
            }

            @Override
            public void onMessage(String message) {
                Log.i(TAG, "message: " + message);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.i(TAG, "onClose: " + code + " " + reason + " " + remote);
            }

            @Override
            public void onError(Exception ex) {
                Log.w(TAG, "", ex);
            }
        };
    }

    @Override
    public void connect(ConnectionListener connectionListener) {
        mConnectionListener = connectionListener;
        ws.connect();
    }
}
