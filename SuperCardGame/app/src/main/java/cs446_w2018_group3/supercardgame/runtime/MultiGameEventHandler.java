package cs446_w2018_group3.supercardgame.runtime;

import android.util.Log;

import cs446_w2018_group3.supercardgame.Exception.PlayerActionException.PlayerNotFoundException;
import cs446_w2018_group3.supercardgame.model.dto.GameRuntimeData;
import cs446_w2018_group3.supercardgame.model.field.GameField;
import cs446_w2018_group3.supercardgame.model.network.RemoteGameEventListener;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.LocalGameEventListener;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.stateevent.GameEndEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.GameEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.playerevent.PlayerAddEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.playerevent.actionevent.PlayerCombineElementEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.playerevent.actionevent.PlayerEndTurnEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.playerevent.actionevent.PlayerUseCardEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.stateevent.StateEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.stateevent.TurnStartEvent;
import cs446_w2018_group3.supercardgame.viewmodel.MultiGameViewModel;

/**
 * Created by JarvieK on 2018/3/25.
 */

public class MultiGameEventHandler extends GameEventHandler implements RemoteGameEventListener {
    private static final String TAG = MultiGameEventHandler.class.getName();

    private final boolean isHost;
    private LocalGameEventListener mLocalEventListener;

    public MultiGameEventHandler(boolean isHost) {
        this.isHost = isHost;
    }

    private void dispatchGameEvent(GameEvent e) {
        if (mLocalEventListener != null) {
            mLocalEventListener.onGameEvent(e);
        }
    }

    @Override
    public void handlePlayerUseCardEvent(PlayerUseCardEvent e) {
        if (isHost) super.handlePlayerUseCardEvent(e);
        dispatchGameEvent(e);
    }

    @Override
    public void handlePlayerCombineElementEvent(PlayerCombineElementEvent e) {
        if (isHost) super.handlePlayerCombineElementEvent(e);
        dispatchGameEvent(e);
    }

    @Override
    public void handlePlayerEndTurnEvent(PlayerEndTurnEvent e) {
        if (isHost) super.handlePlayerEndTurnEvent(e);
        dispatchGameEvent(e);
    }

    @Override
    public void handleTurnStartEvent(TurnStartEvent e) {
        if (isHost) super.handleTurnStartEvent(e);
        dispatchGameEvent(e);
    }

    @Override
    public void handleGameEndEvent(GameEndEvent e) {
        if (isHost) super.handleGameEndEvent(e);
        dispatchGameEvent(e);
    }

    @Override
    public void handlePlayerAddEvent(PlayerAddEvent e) {
        if (isHost) super.handlePlayerAddEvent(e);
        dispatchGameEvent(e);
    }

    public void setLocalGameEventListener(LocalGameEventListener listener) {
        mLocalEventListener = listener;
    }

    @Override
    public void onRemoteGameEventReceived(GameEvent e) {
        Log.i(TAG, "remote game event received: " + e.getEventCode());

        switch (e.getEventCode()) {
            // TODO: if GameEventHandler can handle GameEvent instead of the concrete events,
            // this code can be simpler and less error prone
            case PLAYER_COMBINE_ELEMENT:
                if (isHost) handlePlayerCombineElementEvent((PlayerCombineElementEvent) e);
                break;
            case PLAYER_USE_CARD:
                if (isHost) handlePlayerUseCardEvent((PlayerUseCardEvent) e);
                break;
            case PLAYER_START_TURN:
                super.handleTurnStartEvent((TurnStartEvent) e);
                break;
            case PLAYER_END_TURN:
                if (isHost) handlePlayerEndTurnEvent((PlayerEndTurnEvent) e);
                break;
            case PLAYER_ADD:
                if (isHost) handlePlayerAddEvent((PlayerAddEvent) e);
                break;
            case GAME_END:
                super.handleGameEndEvent((GameEndEvent) e);
                break;
            default:
                // unknown event
                Log.w(TAG, "unknown game event: " + e);
        }
    }

    @Override
    public void onSyncDataReceived(GameRuntimeData gameRuntimeData) {
        // used by client only
        getGameRuntime().replaceGameData(gameRuntimeData);
    }

    @Override
    public void onPlayerDataReceived(Player player) {
        try {
            getGameRuntime().updatePlayer(player);
        }
        catch(PlayerNotFoundException err) {
            Log.i(TAG, "player not found, adding as new player");
            handlePlayerAddEvent(new PlayerAddEvent(player));
        }
    }

    @Override
    public void onFieldDataReceived(GameField gameField) {
        Log.e(TAG, "not implemented!");
//        getGameRuntime().updateGameField(gameField);
    }
}
