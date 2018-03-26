package cs446_w2018_group3.supercardgame.runtime;

import cs446_w2018_group3.supercardgame.util.events.GameEvent.stateevent.GameEndEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.playerevent.PlayerAddEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.playerevent.actionevent.PlayerCombineElementEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.playerevent.actionevent.PlayerEndTurnEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.playerevent.actionevent.PlayerUseCardEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.stateevent.StateEventListener;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.stateevent.TurnStartEvent;

/**
 * Created by JarvieK on 2018/2/26.
 */

public interface IGameEventHandler {
    void handlePlayerUseCardEvent(PlayerUseCardEvent e);
    void handlePlayerCombineElementEvent(PlayerCombineElementEvent e);
    void handlePlayerEndTurnEvent(PlayerEndTurnEvent e);
    void handleTurnStartEvent(TurnStartEvent e);
    void handleGameEndEvent(GameEndEvent e);
    void handlePlayerAddEvent(PlayerAddEvent e);
    void bind(GameRuntime gameRuntime);
    void addStateEventListener(StateEventListener adapter);
}
