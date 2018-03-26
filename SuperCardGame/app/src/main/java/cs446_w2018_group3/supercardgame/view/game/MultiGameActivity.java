package cs446_w2018_group3.supercardgame.view.game;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import cs446_w2018_group3.supercardgame.model.field.GameField;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.stateevent.TurnStartEvent;
import cs446_w2018_group3.supercardgame.viewmodel.MultiGameViewModel;

/**
 * Created by JarvieK on 2018/3/25.
 */

public class MultiGameActivity extends GameActivity {
    private static final String TAG = MultiGameActivity.class.getName();

    private boolean remotePlayerAdded = false;
    private boolean currPlayerAdded = false;
    private boolean gameStarted = false;

    private boolean isGameReady() {
        if (((MultiGameViewModel) viewModel).isHost()) {
            return remotePlayerAdded;
        }
        else {
            return remotePlayerAdded && currPlayerAdded;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(MultiGameViewModel.class);

        viewModel.getOpponent().observe(this, new Observer<Player>() {
            @Override
            public void onChanged(@Nullable Player player) {
                if (!remotePlayerAdded && player != null) {
                    remotePlayerAdded = true;
                }
                if (!gameStarted && isGameReady()) {
                    gameStarted = true;
                    onGameReady();
                }
            }
        });

        viewModel.getCurrPlayer().observe(this, new Observer<Player>() {
            @Override
            public void onChanged(@Nullable Player player) {
                if (!currPlayerAdded && player != null) {
                    currPlayerAdded = true;
                }
                if (!gameStarted && isGameReady()) {
                    gameStarted = true;
                    onGameReady();
                }
            }
        });
    }
}
