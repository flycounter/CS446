package cs446_w2018_group3.supercardgame.gamelogic.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import cs446_w2018_group3.supercardgame.gamelogic.model.CustomMutableLiveData;
import cs446_w2018_group3.supercardgame.gamelogic.model.Player;

/**
 * Created by JarvieK on 2018/2/25.
 */

public class PlayerDataRepository {
    private static PlayerDataRepository playerDataRepository;

    private PlayerDataRepository() {
        Player player = new Player();
    }

    public static PlayerDataRepository getInstance() {
        if (playerDataRepository == null) {
            playerDataRepository = new PlayerDataRepository();
        }

        return playerDataRepository;
    }

    public LiveData<Player> getPlayer() {
        final CustomMutableLiveData<Player> data = new CustomMutableLiveData<>();
        return data;
    }
}
