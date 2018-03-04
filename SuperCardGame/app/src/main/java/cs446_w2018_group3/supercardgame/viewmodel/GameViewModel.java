package cs446_w2018_group3.supercardgame.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import cs446_w2018_group3.supercardgame.Exceptions.PlayerActionException.PlayerNotFoundException;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.runtime.GameRuntime;
import cs446_w2018_group3.supercardgame.runtime.GameEventHandler;
import cs446_w2018_group3.supercardgame.util.events.StateEventAdapter;
import cs446_w2018_group3.supercardgame.util.events.playerevent.PlayerCombineElementEvent;
import cs446_w2018_group3.supercardgame.util.events.playerevent.PlayerEndTurnEvent;
import cs446_w2018_group3.supercardgame.util.events.playerevent.TurnStartEvent;
import cs446_w2018_group3.supercardgame.util.events.playerevent.PlayerUseCardEvent;

/**
 * Created by JarvieK on 2018/2/25.
 */

public class GameViewModel extends AndroidViewModel implements PlayerAction {
    private final GameRuntime gameRuntime;
    private final GameEventHandler gameEventHandler;

    private Player player;

    public GameViewModel(Application application) {
        super(application);

        gameEventHandler = new GameEventHandler();
        gameRuntime = new GameRuntime(gameEventHandler);
    }

    public void init() {
        // start after UI setup completes
        player = new Player(1, "you");
        gameRuntime.addPlayer(player);
        gameRuntime.addBot();
    }

    public void start() {
        gameRuntime.start();
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

    public void addStateEventListener(StateEventAdapter adapter) {
        gameEventHandler.addStateEventListener(adapter);
    }
}
