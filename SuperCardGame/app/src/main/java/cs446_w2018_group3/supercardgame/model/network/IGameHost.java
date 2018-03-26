package cs446_w2018_group3.supercardgame.model.network;

import android.arch.lifecycle.LiveData;

import java.util.List;

import cs446_w2018_group3.supercardgame.util.events.GameEvent.GameEvent;

/**
 * Created by JarvieK on 2018/3/23.
 */

public interface IGameHost {
    void start();
    void stop();
    ConnInfo getHostInfo();
    LiveData<List<ConnInfo>> getConnections();
    void onGameEventReceived(GameEvent e);
}
