package cs446_w2018_group3.supercardgame.runtime;


import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cs446_w2018_group3.supercardgame.Exception.PlayerActionException.PlayerActionException;
import cs446_w2018_group3.supercardgame.Exception.PlayerActionException.PlayerActionNotAllowed;
import cs446_w2018_group3.supercardgame.Exception.PlayerActionException.PlayerCanNotEnterTurnException;
import cs446_w2018_group3.supercardgame.Exception.PlayerActionException.PlayerNotFoundException;
import cs446_w2018_group3.supercardgame.Exception.PlayerStateException.InvalidStateException;
import cs446_w2018_group3.supercardgame.Exception.PlayerStateException.GameStateException;
import cs446_w2018_group3.supercardgame.model.Game;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.model.cards.element.ElementCard;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.stateevent.GameEndEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.playerevent.PlayerAddEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.stateevent.StateEventListener;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.playerevent.actionevent.PlayerCombineElementEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.playerevent.actionevent.PlayerEndTurnEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.playerevent.actionevent.PlayerUseCardEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.stateevent.TurnStartEvent;
import cs446_w2018_group3.supercardgame.util.listeners.ErrorMessageListener;

/**
 * Created by JarvieK on 2018/2/24.
 */

public class GameEventHandler implements IGameEventHandler {
    private static final String TAG = GameEventHandler.class.getName();
    private Game game;
    private GameRuntime gameRuntime;

    // event listener
    private List<StateEventListener> stateEventListeners;
    private ErrorMessageListener mErrorMessageListener;

    public GameEventHandler() {
        stateEventListeners = new ArrayList<>();
    }

    private void deliverErrorMessage(Exception err) {

        if (mErrorMessageListener != null) {
            Log.i(TAG, "error message delivery to " + mErrorMessageListener);
            mErrorMessageListener.onErrorMessage(err.getMessage());
        }
    }

    @Override
    public void handlePlayerUseCardEvent(PlayerUseCardEvent e) {
        try {
            gameRuntime.checkPlayerEventState(e);

            Player subject = gameRuntime.getPlayer(e.getSubjectId()).getValue();
            Player target = gameRuntime.getPlayer(e.getTargetId()).getValue();
            if (subject == null || target == null) {
                throw new PlayerNotFoundException();
            }

            ElementCard card = (ElementCard) Game.getCardInHand(subject, e.getCardId());

            game.useCard(subject, target, card);
        }
        catch (GameStateException | PlayerActionException err) {
            deliverErrorMessage(err);
        }
    }

    @Override
    public void handlePlayerCombineElementEvent(PlayerCombineElementEvent e) {
        try {
            gameRuntime.checkPlayerEventState(e);

            // get cards to be combined
            // NOTE: maybe replace with lambda map?
            Player player = gameRuntime.getPlayer(e.getSubjectId()).getValue();
            if (player == null) {
                throw new PlayerNotFoundException();
            }

            List<ElementCard> cards = new ArrayList<>();
            for (int cardId: e.getCardIds()) {
                cards.add((ElementCard) Game.getCardInHand(player, cardId));
            }

            game.combineCard(player, cards);
        }
        catch (GameStateException | PlayerActionException err) {
            deliverErrorMessage(err);
        }
    }

    @Override
    public void handlePlayerEndTurnEvent(PlayerEndTurnEvent _e) {
        try {
            gameRuntime.checkPlayerEventState(_e);
            gameRuntime.turnEnd();
            gameRuntime.turnStart();
            handleTurnStartEvent(new TurnStartEvent(gameRuntime.getCurrPlayer().getId()));
        }
        catch (InvalidStateException | PlayerActionException err) {
            if (err instanceof PlayerCanNotEnterTurnException) {
                Player winner = null;
                for (LiveData<Player> playerHolder: gameRuntime.getPlayers()) {
                    if (playerHolder.getValue().getHP() > 0) {
                        winner = playerHolder.getValue();
                    }
                }

                if (winner == null) {
                    Log.w(TAG, "all players' HP reaches zero");
                    return;
                }

                // game end
                handleGameEndEvent(new GameEndEvent(winner));
                return;
            }
            deliverErrorMessage(err);
        }
    }

    @Override
    public void handleGameEndEvent(GameEndEvent e) {
        for (StateEventListener listener: stateEventListeners) {
            listener.onGameEnd(e);
        }
    }

    @Override
    public void handleTurnStartEvent(TurnStartEvent e) {
        for (StateEventListener listener: stateEventListeners) {
            listener.onTurnStart(e);
        }
    }

    @Override
    public void handlePlayerAddEvent(PlayerAddEvent e) {
        try {
            gameRuntime.checkPlayerEventState(e);
            if (gameRuntime.getLocalPlayer().getValue() == null) {
                gameRuntime.updateLocalPlayer(e.getPlayer());
            }
            else if (gameRuntime.getOtherPlayer().getValue() == null) {
                gameRuntime.updateOtherPlayer(e.getPlayer());
            }
            else {
                Log.w(TAG, "cannot add more players: " + e.getPlayer());
            }
        }
        catch (InvalidStateException | PlayerActionNotAllowed err) {
            deliverErrorMessage(err);
        }
    }

    @Override
    public void bind(GameRuntime gameRuntime) {
        this.gameRuntime = gameRuntime;
        this.game = gameRuntime.getGameModel();
    }

    protected GameRuntime getGameRuntime() {
        return this.gameRuntime;
    }

    @Override
    public void addStateEventListener(StateEventListener listener) {
        stateEventListeners.add(listener);
        Log.i(TAG, "state event listener added");
    }

    @Override
    public void setErrorMessageListener(ErrorMessageListener errorMessageListener) {
        Log.i(TAG, "ErrorMessageListener set");
        mErrorMessageListener = errorMessageListener;
    }
}
