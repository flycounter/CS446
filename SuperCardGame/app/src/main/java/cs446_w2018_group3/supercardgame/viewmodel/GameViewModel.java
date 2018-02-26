package cs446_w2018_group3.supercardgame.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import cs446_w2018_group3.supercardgame.gamelogic.GameLogic;
import cs446_w2018_group3.supercardgame.gamelogic.model.Player;
import cs446_w2018_group3.supercardgame.gamelogic.repository.PlayerDataRepository;
import cs446_w2018_group3.supercardgame.util.events.PlayerEvent;
import cs446_w2018_group3.supercardgame.util.events.actions.*;

/**
 * Created by JarvieK on 2018/2/25.
 */

public class GameViewModel extends AndroidViewModel implements PlayerAction {
    private final GameLogic gameLogic = GameLogic.getInstance();
    private final LiveData<Player> playerObservable;

    public GameViewModel(Application application) {
        super(application);

        playerObservable = PlayerDataRepository.getInstance().getPlayer();
    }

    public LiveData<Player> getPlayerObservable() {
        return playerObservable;
    }

    @Override
    public void combineCards(List<Integer> cardIndices) {
        // create event then pass to gameLogic
        PlayerEvent e = new PlayerEvent(new CombineAction(cardIndices));
        // pass event to gameLogic
        gameLogic.handleEvent(e);
    }

    @Override
    public void useElementCard(Integer cardIndex) {
        // TODO
    }

    @Override
    public void turnEnd() {
        // TODO
    }
}
