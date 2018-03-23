package cs446_w2018_group3.supercardgame.network;

import cs446_w2018_group3.supercardgame.util.events.GameEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEventAdapter;
import cs446_w2018_group3.supercardgame.util.events.payload.GameInfo;

/**
 * Created by JarvieK on 2018/3/22.
 */

public interface IMultiPlayerManager {
    GameInfo getGameData();
    void broadcast(GameEvent e);
    void setGameEventAdapter(GameEventAdapter adapter);
}
