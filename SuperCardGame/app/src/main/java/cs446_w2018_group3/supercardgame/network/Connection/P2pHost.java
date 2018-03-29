package cs446_w2018_group3.supercardgame.network.Connection;

import android.util.Log;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import cs446_w2018_group3.supercardgame.model.network.ConnInfo;

/**
 * Created by JarvieK on 2018/3/23.
 */

public class P2pHost implements IHost {
    private static final String TAG = P2pHost.class.getName();
    private ConnInfo connInfo;
    private HostStartedListener hostStartedListener;
    private ConnectionListener mConnectionListener;
    private IMessageConnector mMessageConnector;
    private WebSocketServer ws;


    // TODO: singleton

    public P2pHost() {
        // create websocket
    }

    @Override
    public void start(String gameName, HostStartedListener hostStartedListener) {
        this.hostStartedListener = hostStartedListener;
        // listen on websocket
        if (ws != null) {
            try {
                ws.stop();
            } catch (Exception e) {
                // supressed
            }

            ws = null;
        }
        ws = new WebSocketServer(new InetSocketAddress(0)) {
            @Override
            public void onOpen(WebSocket conn, ClientHandshake handshake) {
                Log.i(TAG, "onOpen: " + conn + " " + handshake);
                mConnectionListener.onConnected();
            }

            @Override
            public void onClose(WebSocket conn, int code, String reason, boolean remote) {
                Log.i(TAG, "onClose: " + conn);
                mConnectionListener.onDisconnected();
            }

            @Override
            public void onMessage(WebSocket conn, String message) {
                Log.i(TAG, "onErrorMessage: " + conn + " " + message);

                if (mMessageConnector != null) {
                    mMessageConnector.onMessage(conn, message);
                }
            }

            @Override
            public void onError(WebSocket conn, Exception ex) {
                Log.i(TAG, "onError: " + conn + " " + ex, ex);
            }

            @Override
            public void onStart() {
                connInfo = new ConnInfo(gameName, getAddress().getAddress(), getPort());
                hostStartedListener.onStarted(connInfo);
            }
        };
        ws.start();
    }

    @Override
    public void stop() {
        try {
            ws.stop();
        } catch (InterruptedException | IOException err) {
            Log.w(TAG, "exception in stop(): " + err);
        }
    }

    @Override
    public void sendMessage(String message) {
        broadcast(message);
    }

    @Override
    public void broadcast(String message) {
        ws.broadcast(message);
    }

    @Override
    public void sendConfirmationMessage() {
        broadcast(GAME_JOIN_CONFIRMATION);
    }

    @Override
    public ConnInfo getConnInfo() {
        return connInfo;
    }

    @Override
    public void addConnectionListener(ConnectionListener connectionListener) {
        this.mConnectionListener = connectionListener;
    }

    @Override
    public void removeConnectionListener(ConnectionListener connectionListener) {
        this.mConnectionListener = null;
    }

    @Override
    public void setMessageConnector(IMessageConnector messageConnector) {
        this.mMessageConnector = messageConnector;
    }
}
