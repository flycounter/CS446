package cs446_w2018_group3.supercardgame.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import cs446_w2018_group3.supercardgame.Exception.PlayerActionException.PlayerCanNotEnterTurnException;
import cs446_w2018_group3.supercardgame.model.dao.DaoSession;
import cs446_w2018_group3.supercardgame.model.field.GameField;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.runtime.GameRuntime;
import cs446_w2018_group3.supercardgame.runtime.IGameEventHandler;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.stateevent.GameEndEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.playerevent.PlayerAddEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.playerevent.actionevent.PlayerCombineElementEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.playerevent.actionevent.PlayerEndTurnEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.stateevent.StateEventListener;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.stateevent.TurnStartEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.playerevent.actionevent.PlayerUseCardEvent;
import cs446_w2018_group3.supercardgame.util.listeners.ErrorMessageListener;

/**
 * Created by JarvieK on 2018/2/25.
 */

public abstract class GameViewModel extends AndroidViewModel implements PlayerAction {
    private static final String TAG = GameViewModel.class.getName();
    GameRuntime gameRuntime;
    IGameEventHandler gameEventHandler;
    GameReadyCallback mGameReadyCallback;
    StateEventListener mStateEventListener;
    ErrorMessageListener mErrorMessageListener;
    DaoSession mSession;

    Player player;

    public GameViewModel(Application application) {
        super(application);
    }

    void addLocalPlayer(Player player) {
        if (player == null) {
            // default player
            player = new Player(1, "you");
        }
        this.player = player;

        gameEventHandler.handlePlayerAddEvent(new PlayerAddEvent(player));
        Log.i(TAG, String.format("local player added: %s", player.getName()));
    }

    public void init(Bundle bundle, GameReadyCallback gameReadyCallback, StateEventListener stateEventListener) {
        mGameReadyCallback = gameReadyCallback;
        mStateEventListener = stateEventListener;
        gameEventHandler.addStateEventListener(mStateEventListener);
        gameEventHandler.setErrorMessageListener(mErrorMessageListener);
    }

    public void start() {
        try {
            gameRuntime.start();
            gameRuntime.turnStart(); // starts the first player's turn
        } catch (PlayerCanNotEnterTurnException err) {
            // NOTE: same code as in gameEventHandler.handlePlayerEndTurnEvent(PlayerEndTurnEvent e)
            Player winner = null;
            for (LiveData<Player> playerHolder : gameRuntime.getPlayers()) {
                if (playerHolder.getValue().getHP() > 0) {
                    winner = playerHolder.getValue();
                }
            }

            if (winner == null) {
                Log.w(TAG, "all players' HP reaches zero");
                return;
            }

            // game end
            gameEventHandler.handleGameEndEvent(new GameEndEvent(winner));
        }
    }

    // used by activity / fragments to get observables
    public final GameRuntime getGameRuntime() {
        return gameRuntime;
    }

    @Override
    public void combineCards(List<Integer> cardIds) {
        // NOTE: assume combine commands always come from player (which makes sense)
        // NOTE: app may crash if vm receives command before game start completes
        // pass event to game runtime
        gameEventHandler.handlePlayerCombineElementEvent(
                new PlayerCombineElementEvent(player.getId(), cardIds));
    }

    @Override
    public void useElementCard(int targetId, int cardId) {
        gameEventHandler.handlePlayerUseCardEvent(
                new PlayerUseCardEvent(player.getId(), targetId, cardId));
    }

    @Override
    public void turnEnd() {
        gameEventHandler.handlePlayerEndTurnEvent(
                new PlayerEndTurnEvent(player.getId()));
    }

    @Override
    public void onTurnStart(TurnStartEvent e) {
        // TODO: notify ui that player's turn starts
    }

    public LiveData<Player> getThisPlayer() {
        // returns player that belongs to app user
        return gameRuntime.getLocalPlayer();
    }

    public LiveData<Player> getOpponent() {
        return gameRuntime.getOtherPlayer();
    }

    public Player getCurrPlayer() {
        return gameRuntime.getCurrPlayer();
    }

    public LiveData<GameField> getGameField() { return gameRuntime.getGameField(); }

    public void deliverErrorMessage(String message) {
        gameRuntime.updateLogInfo(message);
    }

    public LiveData<String> getActionLogMessage() { return gameRuntime.getLogInfo(); }

    public interface GameReadyCallback {
        void onGameReady();
    }

    public void setmErrorMessageListener(ErrorMessageListener mErrorMessageListener) {
        this.mErrorMessageListener = mErrorMessageListener;
    }


    public void setSession(DaoSession session) {
        Log.i(TAG, "session set: " + session);
        mSession = session;
    }
}

