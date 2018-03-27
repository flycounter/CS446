package cs446_w2018_group3.supercardgame.util.events.GameEvent.stateevent;


import cs446_w2018_group3.supercardgame.model.player.Player;

/**
 * Created by JarvieK on 2018/3/3.
 */

public abstract class AbsStateEventAdapter implements StateEventListener {
    public abstract void onTurnStart(TurnStartEvent e);
    public abstract void onGameEnd(GameEndEvent e);
}
