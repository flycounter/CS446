package cs446_w2018_group3.supercardgame.network.Connection;

import android.util.Log;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import cs446_w2018_group3.supercardgame.model.network.ConnInfo;

/**
 * Created by JarvieK on 2018/3/23.
 */

public class P2pClient implements IClient {
    private static final String TAG = P2pClient.class.getName();
    private final List<ConnectionListener> mConnectionListeners = new ArrayList<>();
    private final ConnInfo mConnInfo;
    private final WebSocketClient ws;
    private IMessageConnector mMessageConnetor;

    // TODO: singleton?

    public P2pClient(ConnInfo connInfo) {
        URI uri = URI.create(String.format("ws://%s:%s", connInfo.getHost().getHostAddress(), connInfo.getPort()));
        Log.i(TAG, "connection string: " + uri);
        mConnInfo = connInfo;
        ws = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                Log.i(TAG, "connected to host: " + handshakedata);

                try {
                    Thread.sleep(1000); /* delay simulation */
                } catch (Exception e) {
                }

                for (ConnectionListener listener : mConnectionListeners) {
                    listener.onConnected();
                }
            }

            @Override
            public void onMessage(String message) {
                Log.i(TAG, "message: " + message);

                if (mMessageConnetor != null) {
                    mMessageConnetor.onMessage(ws, message);
                }

                for (ConnectionListener listener : mConnectionListeners) {
                    listener.onMessage(message);
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.i(TAG, "onClose: " + code + " " + reason + " " + remote);

                for (ConnectionListener listener : mConnectionListeners) {
                    listener.onDisconnected();
                }
            }

            @Override
            public void onError(Exception ex) {
                Log.w(TAG, "", ex);
            }
        };
    }

    @Override
    public void sendMessage(String message) {
        ws.send(message);
    }

    @Override
    public ConnInfo getConnInfo() {
        return mConnInfo;
    }

    @Override
    public void connect() {
        ws.connect();
    }

    @Override
    public void addConnectionListener(ConnectionListener connectionListener) {
        mConnectionListeners.add(connectionListener);
    }

    @Override
    public void removeConnectionListener(ConnectionListener connectionListener) {
        mConnectionListeners.remove(connectionListener);
    }

    @Override
    public void setMessageConnector(IMessageConnector messageConnector) {
        mMessageConnetor = messageConnector;
    }
}
