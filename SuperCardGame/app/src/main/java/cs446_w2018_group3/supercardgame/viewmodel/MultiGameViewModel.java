package cs446_w2018_group3.supercardgame.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import cs446_w2018_group3.supercardgame.model.NetworkConnector;
import cs446_w2018_group3.supercardgame.model.network.INetworkConnector;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.network.Connection.ClientHandler;
import cs446_w2018_group3.supercardgame.network.Connection.HostHandler;
import cs446_w2018_group3.supercardgame.network.Connection.IClient;
import cs446_w2018_group3.supercardgame.network.Connection.ConnectionListener;
import cs446_w2018_group3.supercardgame.runtime.GameEventHandlerProxy;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.stateevent.StateEventListener;

/**
 * Created by JarvieK on 2018/3/25.
 */

public class MultiGameViewModel extends GameViewModel implements ConnectionListener {
    private final static String TAG = MultiGameViewModel.class.getName();

    private boolean isHost;

    public MultiGameViewModel(Application application) {
        super(application);
        gameEventHandler = new GameEventHandlerProxy();
        gameRuntime.bind(gameEventHandler);
    }

    @Override
    public void init(Bundle bundle, GameReadyCallback gameReadyCallback, StateEventListener stateEventListener) {
        isHost = bundle.getBoolean("isHost");
        Log.i(TAG, "isHost: " + isHost());
        super.init(bundle, gameReadyCallback, stateEventListener);

        if (!isHost()) {
            player = new Player(3, "remote player");
        }

        addLocalPlayer(player);

        INetworkConnector mNetworkConnector;

        if (isHost()) {
            mNetworkConnector = new NetworkConnector(HostHandler.getHost(), (GameEventHandlerProxy) gameEventHandler, this);
//            mNetworkConnector.sendSyncData(gameRuntime.getSyncData());
        } else {
            mNetworkConnector = new NetworkConnector(ClientHandler.getClient(), (GameEventHandlerProxy) gameEventHandler, this);
            // TODO: load user from real source


            // wait for 2 seconds to simulate network delay
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mNetworkConnector.sendPlayerData(player);
                }
            }, 2000);


        }
    }

    public void onRemoteReady() {
        mGameReadyCallback.onGameReady();
    }

    public boolean isHost() {
        return isHost;
    }

    public void addClient(IClient client) {
        // TODO: for now don't worry about the case that remote user leaves
//        INetworkConnector networkConnector = new NetworkConnector(client, (GameEventHandlerProxy) gameEventHandler, this);
//        ClientPair pair = new ClientPair(client, networkConnector);
//        clients.add(pair);
//        ((GameEventHandlerProxy) gameEventHandler).setLocalGameEventListener((NetworkConnector) networkConnector);
    }

    @Override
    public void onMessage(String message) {
        // nothing
    }

    @Override
    public void onConnected() {
        // nothing
    }

    @Override
    public void onDisconnected() {
        // notify UI that client has disconnected
    }
}
