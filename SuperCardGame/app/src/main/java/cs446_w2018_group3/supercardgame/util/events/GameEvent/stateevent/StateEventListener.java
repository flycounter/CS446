package cs446_w2018_group3.supercardgame.util.events.GameEvent.stateevent;

import cs446_w2018_group3.supercardgame.model.player.Player;

/**
 * Created by JarvieK on 2018/3/25.
 */

public interface StateEventListener {
    void onTurnStart(TurnStartEvent e);
    void onGameEnd(GameEndEvent e);
}
