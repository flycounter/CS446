package cs446_w2018_group3.supercardgame.model.bot;

import cs446_w2018_group3.supercardgame.util.events.GameEvent.stateevent.StateEventListener;

/**
 * Created by JarvieK on 2018/3/3.
 */

public interface IBot {
    void setStateEventListener(StateEventListener stateEventListener);
}
