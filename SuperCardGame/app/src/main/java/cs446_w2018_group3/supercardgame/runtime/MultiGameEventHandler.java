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
import cs446_w2018_group3.supercardgame.util.events.GameEvent.stateevent.TurnStartEvent;
import cs446_w2018_group3.supercardgame.viewmodel.MultiGameViewModel;

/**
 * Created by JarvieK on 2018/3/25.
 */

public class MultiGameEventHandler extends GameEventHandler implements RemoteGameEventListener {
    private static final String TAG = MultiGameEventHandler.class.getName();

    private LocalGameEventListener mLocalEventListener;
    private MultiGameViewModel mViewModel;
    private boolean onGameReadyNotified = false;

    public MultiGameEventHandler() {

    }

    private void dispatchGameEvent(GameEvent e) {
        if (mLocalEventListener == null) {
            return;
        }
        mLocalEventListener.onGameEvent(e);
    }

    @Override
    public void handlePlayerUseCardEvent(PlayerUseCardEvent e) {
        super.handlePlayerUseCardEvent(e);
        dispatchGameEvent(e);
    }

    @Override
    public void handlePlayerCombineElementEvent(PlayerCombineElementEvent e) {
        super.handlePlayerCombineElementEvent(e);
        dispatchGameEvent(e);
    }

    @Override
    public void handlePlayerEndTurnEvent(PlayerEndTurnEvent e) {
        super.handlePlayerEndTurnEvent(e);
        dispatchGameEvent(e);
    }

    @Override
    public void handleTurnStartEvent(TurnStartEvent e) {
        super.handleTurnStartEvent(e);
        dispatchGameEvent(e);
    }

    @Override
    public void handleGameEndEvent(GameEndEvent e) {
        super.handleGameEndEvent(e);
        dispatchGameEvent(e);
    }

    @Override
    public void handlePlayerAddEvent(PlayerAddEvent e) {
        super.handlePlayerAddEvent(e);
        dispatchGameEvent(e);
    }

    public void setLocalGameEventListener(LocalGameEventListener listener) {
        mLocalEventListener = listener;
    }

    @Override
    public void onGameEventReceived(GameEvent e) {
        switch (e.getEventCode()) {
            // TODO: if GameEventHandler can handle GameEvent instead of the concrete events,
            // this code can be simpler and less error prone
            case PLAYER_COMBINE_ELEMENT:
                super.handlePlayerCombineElementEvent((PlayerCombineElementEvent) e);
                break;
            case PLAYER_USE_CARD:
                super.handlePlayerUseCardEvent((PlayerUseCardEvent) e);
                break;
            case PLAYER_START_TURN:
                super.handleTurnStartEvent((TurnStartEvent) e);
                break;
            case PLAYER_END_TURN:
                super.handlePlayerEndTurnEvent((PlayerEndTurnEvent) e);
                break;
            case PLAYER_ADD:
                super.handlePlayerAddEvent((PlayerAddEvent) e);
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
//        if (!onGameReadyNotified && getGameRuntime().getState() == GameFSM.State.TURN_START) {
//            onGameReadyNotified = true;
//            mViewModel.onRemoteReady();
//        }
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
