package cs446_w2018_group3.supercardgame.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.util.Log;

import java.util.List;

import cs446_w2018_group3.supercardgame.model.Game;
import cs446_w2018_group3.supercardgame.runtime.GameController;
import cs446_w2018_group3.supercardgame.runtime.GameEventHandler;
import cs446_w2018_group3.supercardgame.util.events.playerevent.PlayerCombineElementEvent;
import cs446_w2018_group3.supercardgame.util.events.playerevent.PlayerEndTurnEvent;
import cs446_w2018_group3.supercardgame.util.events.playerevent.TurnStartEvent;
import cs446_w2018_group3.supercardgame.util.events.playerevent.PlayerUseCardEvent;

/**
 * Created by JarvieK on 2018/2/25.
 */

public class GameViewModel extends AndroidViewModel implements PlayerAction {
    private final GameController gameController;
    private final GameEventHandler gameEventHandler;

    public GameViewModel(Application application) {
        super(application);

        gameEventHandler = new GameEventHandler();
        gameController = new GameController(gameEventHandler);
    }

    public void start() {
        // start after UI setup completes
        Log.i("viewmodel", "game runtime start");
        gameController.start();
    }

    // used by activity / fragments to get observables
    public final GameController getGameController() { return gameController; }

    @Override
    public void combineCards(List<Integer> cardIds) {
        // NOTE: assume combine commands always come from player (which makes sense)
        // NOTE: app may crash if vm receives command before game start completes
        // pass event to game runtime
        gameController.handlePlayerCombineElementEvent(new PlayerCombineElementEvent(
                gameEventHandler.getPlayer().getValue().getId(), cardIds));
    }

    @Override
    public void useElementCard(Integer cardId) {
        // TODO
        gameEventHandler.handlePlayerUseCardEvent(new PlayerUseCardEvent(
                gameEventHandler.getPlayer().getValue().getId(),
                gameEventHandler.getOpponent().getValue().getId(),
                cardId));
    }

    @Override
    public void turnEnd() {
        // TODO
        gameEventHandler.handlePlayerEndTurnEvent(new PlayerEndTurnEvent(
                gameEventHandler.getPlayer().getValue().getId()));
        gameEventHandler.handlePlayerStartTurnEvent(new TurnStartEvent(
                gameEventHandler.getPlayer().getValue().getId()));
    }
}
