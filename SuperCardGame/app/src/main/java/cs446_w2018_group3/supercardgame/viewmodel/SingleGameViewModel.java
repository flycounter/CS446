package cs446_w2018_group3.supercardgame.viewmodel;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import cs446_w2018_group3.supercardgame.model.bot.Bot;
import cs446_w2018_group3.supercardgame.model.player.AIPlayer;
import cs446_w2018_group3.supercardgame.runtime.GameEventHandler;
import cs446_w2018_group3.supercardgame.runtime.GameRuntime;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.playerevent.PlayerAddEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.stateevent.StateEventListener;

/**
 * Created by JarvieK on 2018/3/25.
 */

public class SingleGameViewModel extends GameViewModel {
    private static final String TAG = SingleGameViewModel.class.getName();

    public SingleGameViewModel(Application application) {
        super(application);
        gameEventHandler = new GameEventHandler();
        gameEventHandler.bind(gameRuntime);
    }

    @Override
    public void init(Bundle bundle, GameReadyCallback gameReadyCallback, StateEventListener stateEventListener) {
        super.init(bundle, gameReadyCallback, stateEventListener);
        gameRuntime = new GameRuntime();
        addLocalPlayer(null);
        // start after UI setup completes
        AIPlayer botPlayer = new AIPlayer(2, "Bot");
        Bot bot = new Bot(botPlayer);
        bot.bind(gameEventHandler);
        gameEventHandler.handlePlayerAddEvent(new PlayerAddEvent(botPlayer));
        Log.i(TAG, String.format("bot player added: %s", botPlayer.getName()));
        gameReadyCallback.onGameReady();
    }
}
