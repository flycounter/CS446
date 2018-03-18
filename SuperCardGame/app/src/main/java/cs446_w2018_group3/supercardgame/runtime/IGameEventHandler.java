package cs446_w2018_group3.supercardgame.runtime;

import cs446_w2018_group3.supercardgame.util.events.GameEndEvent;
import cs446_w2018_group3.supercardgame.util.events.stateevent.StateEventAdapter;
import cs446_w2018_group3.supercardgame.util.events.playerevent.PlayerCombineElementEvent;
import cs446_w2018_group3.supercardgame.util.events.playerevent.PlayerEndTurnEvent;
import cs446_w2018_group3.supercardgame.util.events.playerevent.PlayerUseCardEvent;

/**
 * Created by JarvieK on 2018/2/26.
 */

public interface IGameEventHandler {
    void handlePlayerUseCardEvent(PlayerUseCardEvent e);
    void handlePlayerCombineElementEvent(PlayerCombineElementEvent e);
    void handlePlayerEndTurnEvent(PlayerEndTurnEvent e);
    void handleGameEndEvent(GameEndEvent e);
    void addStateEventListener(StateEventAdapter adapter);
}
