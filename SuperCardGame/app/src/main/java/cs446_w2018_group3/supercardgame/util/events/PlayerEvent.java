package cs446_w2018_group3.supercardgame.util.events;

import cs446_w2018_group3.supercardgame.util.events.actions.IAction;

/**
 * Created by JarvieK on 2018/2/23.
 */

public class PlayerEvent extends GameEvent {
    private final IAction action;

    public PlayerEvent(IAction action) {
        this.action = action;
    }

    public IAction getAction() {
        return action;
    }
}
