package cs446_w2018_group3.supercardgame.network.Connection;

import android.util.Log;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import cs446_w2018_group3.supercardgame.model.network.ConnInfo;

/**
 * Created by JarvieK on 2018/3/23.
 */

public class P2pHost implements IHost {
    private static final String TAG = P2pHost.class.getName();
    private ConnInfo connInfo;
    private HostStartedListener hostStartedListener;
    private final WebSocketServer ws;

    // TODO: singleton

    public P2pHost() {
        // create websocket
        ws = new WebSocketServer(new InetSocketAddress(0)) {
            @Override
            public void onOpen(WebSocket conn, ClientHandshake handshake) {
                Log.i(TAG, "onOpen: " + conn + " " + handshake);
            }

            @Override
            public void onClose(WebSocket conn, int code, String reason, boolean remote) {
                Log.i(TAG, "onClose: " + conn);
            }

            @Override
            public void onMessage(WebSocket conn, String message) {
                Log.i(TAG, "onMessage: " + conn + " " + message);
            }

            @Override
            public void onError(WebSocket conn, Exception ex) {
                Log.i(TAG, "onError: " + conn + " " + ex, ex);
            }

            @Override
            public void onStart() {
                // TODO: load playerName from db
                connInfo = new ConnInfo("PlayerName", getAddress().getAddress(), getPort());
                hostStartedListener.onStarted(connInfo);
            }
        };
    }

    @Override
    public void start(HostStartedListener hostStartedListener) {
        this.hostStartedListener = hostStartedListener;
        // listen on websocket
        ws.start();
    }

    @Override
    public void stop() {
        try {
            ws.stop();
        }
        catch (InterruptedException | IOException err) {
            Log.w(TAG, "exception in stop(): " + err);
        }
    }

    @Override
    public ConnInfo getConnInfo() {
        return connInfo;
    }
}
