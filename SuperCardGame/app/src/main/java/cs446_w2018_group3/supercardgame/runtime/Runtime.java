package cs446_w2018_group3.supercardgame.runtime;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import cs446_w2018_group3.supercardgame.model.Player;
import cs446_w2018_group3.supercardgame.model.cards.Card;
import cs446_w2018_group3.supercardgame.model.cards.FireCard;
import cs446_w2018_group3.supercardgame.model.cards.WaterCard;

/**
 * Created by JarvieK on 2018/2/24.
 */

public class Runtime {
    private final MutableLiveData<Player> player = new MutableLiveData<>();
    private final MutableLiveData<Player> opponent = new MutableLiveData<>();
//    private Game game;

    public Runtime() {
        init();
    }

    private void init() {
        Player playerData = new Player(1, "you");
        for (int i = 0; i < 3; i++) {
            playerData.insertCard(new WaterCard());
        }

        Player opponentData = new Player(2, "opponent");
        for (int i = 0; i < 3; i++) {
            playerData.insertCard(new FireCard());
        }

        player.setValue(playerData);
        opponent.setValue(opponentData);
    }

    public LiveData<Player> getPlayer() {
        return player;
    }

    public LiveData<Player> getOpponent() {
        return opponent;
    }

    public MutableLiveData<Player> getMutablePlayer() {
        return player;
    }

    public MutableLiveData<Player> getMutableOpponent() {
        return opponent;
    }
}
