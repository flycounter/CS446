package cs446_w2018_group3.supercardgame.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import java.util.List;

import cs446_w2018_group3.supercardgame.model.Game;
import cs446_w2018_group3.supercardgame.model.Player;
import cs446_w2018_group3.supercardgame.runtime.GameRuntime;
import cs446_w2018_group3.supercardgame.util.events.playerevent.BasePlayerEvent;
import cs446_w2018_group3.supercardgame.util.events.playerevent.PlayerCombineElementEvent;
import cs446_w2018_group3.supercardgame.util.events.playerevent.PlayerEndTurnEvent;
import cs446_w2018_group3.supercardgame.util.events.playerevent.PlayerUseCardEvent;

/**
 * Created by JarvieK on 2018/2/25.
 */

public class GameViewModel extends AndroidViewModel implements PlayerAction {
    private final GameRuntime gameRuntime;
    private final Game game;

    public GameViewModel(Application application) {
        super(application);

        // game init begin
        gameRuntime = new GameRuntime();
        game = new Game();
        gameRuntime.init(game);
        // game init done
    }

    // used by activity / fragments to get observables
    public final GameRuntime getGameRuntime() { return gameRuntime; }

    @Override
    public void combineCards(List<Integer> cardIds) {
        // NOTE: assume combine commands always come from player (which makes sense)
        // NOTE: app may crash if vm receives command before game init completes
        // pass event to game runtime
        gameRuntime.handlePlayerCombineElementEvent(new PlayerCombineElementEvent(
                gameRuntime.getPlayer().getValue().getId(), cardIds));
    }

    @Override
    public void useElementCard(Integer cardId) {
        // TODO
        gameRuntime.handlePlayerUseCardEvent(new PlayerUseCardEvent(
                gameRuntime.getPlayer().getValue().getId(),
                gameRuntime.getOpponent().getValue().getId(),
                cardId));
    }

    @Override
    public void turnEnd() {
        // TODO
        gameRuntime.handlePlayerEndTurnEvent(new PlayerEndTurnEvent(
                gameRuntime.getPlayer().getValue().getId()));
    }
}
