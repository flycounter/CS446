package cs446_w2018_group3.supercardgame.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import cs446_w2018_group3.supercardgame.Exception.PlayerActionException.PlayerCanNotEnterTurnException;
import cs446_w2018_group3.supercardgame.Exception.PlayerActionException.PlayerNotFoundException;
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

/**
 * Created by JarvieK on 2018/2/25.
 */

public abstract class GameViewModel extends AndroidViewModel implements PlayerAction {
    private static final String TAG = GameViewModel.class.getName();
    GameRuntime gameRuntime;
    IGameEventHandler gameEventHandler;
    GameReadyCallback mGameReadyCallback;
    StateEventListener mStateEventListener;

    Player player;

    public GameViewModel(Application application) {
        super(application);
        gameRuntime = new GameRuntime();
    }

    void addLocalPlayer(Player player) {
        if (player == null) {
            // default player
            player = new Player(1, "you");
        }

        gameEventHandler.handlePlayerAddEvent(new PlayerAddEvent(player));
        Log.i(TAG, String.format("local player added: %s", player.getName()));
    }

    public void init(Bundle bundle, GameReadyCallback gameReadyCallback, StateEventListener stateEventListener) {
        mGameReadyCallback = gameReadyCallback;
        mStateEventListener = stateEventListener;
        gameEventHandler.addStateEventListener(mStateEventListener);
    }

    public void start() {
        try {
            gameRuntime.start();
            gameRuntime.turnStart(); // starts the first player's turn
        }
        catch (PlayerCanNotEnterTurnException err) {
            // NOTE: same code as in gameEventHandler.handlePlayerEndTurnEvent(PlayerEndTurnEvent e)
            // TODO: add method gameRuntime.getWinner()
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
            gameEventHandler.handleGameEndEvent(new GameEndEvent(winner));
        }


    }

    // used by activity / fragments to get observables
    public final GameRuntime getGameRuntime() { return gameRuntime; }

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
        // TODO
        gameEventHandler.handlePlayerUseCardEvent(
                new PlayerUseCardEvent(player.getId(), targetId, cardId));
    }

    @Override
    public void turnEnd() {
        // TODO
        gameEventHandler.handlePlayerEndTurnEvent(
                new PlayerEndTurnEvent(player.getId()));
    }

    @Override
    public void onTurnStart(TurnStartEvent e) {
        // TODO: notify ui that player's turn starts
    }

    // called by view to add player to game
    public void addPlayer(int id, String name) {
        gameRuntime.addPlayer(new Player(id, name));
    }

    public LiveData<Player> getThisPlayer() throws PlayerNotFoundException {
        // returns player that belongs to app user
        return gameRuntime.getPlayer(player.getId());
    }

    public LiveData<Player> getOpponent() throws PlayerNotFoundException {
        try {
            return gameRuntime.getPlayers().get(1);
        }
        catch (ArrayIndexOutOfBoundsException err) {
            throw new PlayerNotFoundException();
        }
    }

    public interface GameReadyCallback {
        void onGameReady();
    }
}
