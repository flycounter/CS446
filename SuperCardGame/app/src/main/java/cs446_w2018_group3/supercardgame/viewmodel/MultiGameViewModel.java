package cs446_w2018_group3.supercardgame.viewmodel;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import cs446_w2018_group3.supercardgame.model.NetworkConnector;
import cs446_w2018_group3.supercardgame.model.dao.DaoSession;
import cs446_w2018_group3.supercardgame.model.dao.User;
import cs446_w2018_group3.supercardgame.model.dao.UserDao;
import cs446_w2018_group3.supercardgame.model.dto.GameRuntimeData;
import cs446_w2018_group3.supercardgame.model.network.INetworkConnector;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.network.Connection.ClientHandler;
import cs446_w2018_group3.supercardgame.network.Connection.HostHandler;
import cs446_w2018_group3.supercardgame.network.Connection.ConnectionListener;
import cs446_w2018_group3.supercardgame.runtime.MultiGameEventHandler;
import cs446_w2018_group3.supercardgame.runtime.GameFSM;
import cs446_w2018_group3.supercardgame.runtime.GameRuntime;
import cs446_w2018_group3.supercardgame.runtime.MultiGameRuntime;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.LocalGameEventListener;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.stateevent.StateEventListener;

/**
 * Created by JarvieK on 2018/3/25.
 */

public class MultiGameViewModel extends GameViewModel implements ConnectionListener, GameFSM.GameStateChangeListener {
    private final static String TAG = MultiGameViewModel.class.getName();
    private INetworkConnector mNetworkConnector;
    private boolean isHost;

    public MultiGameViewModel(Application application) {
        super(application);
        gameEventHandler = new MultiGameEventHandler();
    }

    @Override
    public void init(Bundle bundle, GameReadyCallback gameReadyCallback, StateEventListener stateEventListener) {
        isHost = bundle.getBoolean("isHost");
        Log.i(TAG, "isHost: " + isHost());

        gameRuntime  = new MultiGameRuntime(isHost());
        gameEventHandler.bind(gameRuntime);
        gameRuntime.setGameStateChangeListener(this);

        super.init(bundle, gameReadyCallback, stateEventListener);

        player = Player.getLocalPlayer(mSession);
        addLocalPlayer(player);

        if (isHost()) {
            mNetworkConnector = new NetworkConnector(HostHandler.getHost(), (MultiGameEventHandler) gameEventHandler, this);
        } else {
            mNetworkConnector = new NetworkConnector(ClientHandler.getClient(), (MultiGameEventHandler) gameEventHandler, this);
            mNetworkConnector.sendPlayerData(player);
        }

        ((MultiGameEventHandler) gameEventHandler).setLocalGameEventListener((LocalGameEventListener) mNetworkConnector);
    }

    public void onRemoteReady() {
        mGameReadyCallback.onGameReady();
    }

    public boolean isHost() {
        return isHost;
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

    public void dataSync() {
        if (isHost()) {
            GameRuntimeData gameRuntimeData = gameRuntime.getSyncData();

            gameRuntimeData = new GameRuntimeData(gameRuntimeData.getLocalPlayer(), gameRuntimeData.getOtherPlayer(), gameRuntimeData.getCurrPlayer(), gameRuntimeData.getGameField(), gameRuntimeData.getGameState());

            Log.i(TAG, "data sync to client");
            Log.i(TAG, "local player: " + gameRuntimeData.getLocalPlayer());
            Log.i(TAG, "other player: " + gameRuntimeData.getOtherPlayer());
            Log.i(TAG, "curr player: " + gameRuntimeData.getCurrPlayer());
            Log.i(TAG, "game state: " + gameRuntimeData.getGameState());

            mNetworkConnector.sendSyncData(gameRuntime.getSyncData());
        }
        else {
            // TODO: not implemented yet?
        }
    }

    @Override
    public void onStateChange(GameFSM.State state) {
        dataSync();
    }
}
