package cs446_w2018_group3.supercardgame.viewmodel;

import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import cs446_w2018_group3.supercardgame.model.NetworkConnector;
import cs446_w2018_group3.supercardgame.model.cards.AirCard;
import cs446_w2018_group3.supercardgame.model.cards.ElementCard;
import cs446_w2018_group3.supercardgame.model.cards.FireCard;
import cs446_w2018_group3.supercardgame.model.cards.WaterCard;
import cs446_w2018_group3.supercardgame.model.dto.GameRuntimeData;
import cs446_w2018_group3.supercardgame.model.network.INetworkConnector;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.network.Connection.ClientHandler;
import cs446_w2018_group3.supercardgame.network.Connection.HostHandler;
import cs446_w2018_group3.supercardgame.network.Connection.ConnectionListener;
import cs446_w2018_group3.supercardgame.runtime.GameEventHandlerProxy;
import cs446_w2018_group3.supercardgame.runtime.GameFSM;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.stateevent.StateEventListener;

/**
 * Created by JarvieK on 2018/3/25.
 */

public class MultiGameViewModel extends GameViewModel implements ConnectionListener, GameFSM.GameStateChangeListener {
    private final static String TAG = MultiGameViewModel.class.getName();
    private INetworkConnector mNetworkConnector;
    private boolean isHost;
    private boolean isRemoteStartGameNotified = false;

    public MultiGameViewModel(Application application) {
        super(application);
        gameEventHandler = new GameEventHandlerProxy();
        gameRuntime.bind(gameEventHandler);
        gameRuntime.setGameStateChangeListener(this);
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

        if (isHost()) {
            mNetworkConnector = new NetworkConnector(HostHandler.getHost(), (GameEventHandlerProxy) gameEventHandler, this);
        } else {
            mNetworkConnector = new NetworkConnector(ClientHandler.getClient(), (GameEventHandlerProxy) gameEventHandler, this);
            // TODO: load user from real source


            // wait for 2 seconds to simulate network delay
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mNetworkConnector.sendPlayerData(player);
                }
            }, 1000);
        }
    }

    @Override
    public void start() {
        if (isHost()) {
            super.start();
        }
        else {
            // game already started

        }
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

            gameRuntimeData.getOtherPlayer().addCardToHand(new WaterCard());
            gameRuntimeData.getOtherPlayer().addCardToHand(new FireCard());
            gameRuntimeData.getOtherPlayer().addCardToHand(new AirCard());
            try {
                gameRuntime.updatePlayer(gameRuntimeData.getOtherPlayer());
            }catch (Exception e){}

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
        if (state == GameFSM.State.PLAYER_TURN) {
            Log.i(TAG, "onPlayerTurn: isGameReady: " + isRemoteStartGameNotified);
            // trigger once
            if (!isRemoteStartGameNotified) {
                isRemoteStartGameNotified = true;
                dataSync();
            }
        }
    }
}
