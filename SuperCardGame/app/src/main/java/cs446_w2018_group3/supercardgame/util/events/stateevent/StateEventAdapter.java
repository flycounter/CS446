package cs446_w2018_group3.supercardgame.util.events.stateevent;


import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.util.events.stateevent.TurnStartEvent;

/**
 * Created by JarvieK on 2018/3/3.
 */

public interface StateEventAdapter {
    void onTurnStart(TurnStartEvent e);
    void onGameEnd(Player winner);
}
