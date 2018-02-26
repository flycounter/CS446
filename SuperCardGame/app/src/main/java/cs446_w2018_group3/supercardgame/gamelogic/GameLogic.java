package cs446_w2018_group3.supercardgame.gamelogic;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.util.List;

import cs446_w2018_group3.supercardgame.gamelogic.model.Player;
import cs446_w2018_group3.supercardgame.runtime.Runtime;
import cs446_w2018_group3.supercardgame.util.events.GameEvent;
import cs446_w2018_group3.supercardgame.util.events.PlayerEvent;
import cs446_w2018_group3.supercardgame.util.events.actions.CombineAction;

/**
 * Created by JarvieK on 2018/2/23.
 */

public class GameLogic {
    private Runtime runtime;
    private static GameLogic gameLogic;
    private final PlayerDataRepository playerDataRepository;

    private final LiveData<Player> player;

    private GameLogic() {
        playerDataRepository = PlayerDataRepository.getInstance();
        player = playerDataRepository.getPlayer();
    } // disallow public instantiation

    public static GameLogic getInstance() {
        if (gameLogic == null) {
            gameLogic = new GameLogic();
        }

        return gameLogic;
    }

    public void setRuntime(Runtime runtime) {
        this.runtime = runtime;
    }

    public LiveData<Player> getPlayer() {
        return player;
    }

    public void handleEvent(GameEvent e) {
        // temp code for testing
        List<Integer> list = ((CombineAction) ((PlayerEvent) e).getAction()).getCardIndices();
        Log.d(this.getClass().toString(), String.format("cards: %s", list));
        // change player name to "another player"
//        ((MutableLiveData<Player>) getPlayer()).setValue(new Player("another player"));
        Player player = getPlayer().getValue();
//        player.setName("another name");
        ((MutableLiveData<Player>) getPlayer()).setValue(player);
    }
}
