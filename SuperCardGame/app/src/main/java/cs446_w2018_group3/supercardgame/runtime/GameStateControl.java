package cs446_w2018_group3.supercardgame.runtime;

import cs446_w2018_group3.supercardgame.Exceptions.PlayerActionException.PlayerCanNotEnterTurnException;
import cs446_w2018_group3.supercardgame.util.events.StateEventAdapter;

/**
 * Created by JarvieK on 2018/3/1.
 */

public interface GameStateControl {
    void start();
    void turnStart() throws PlayerCanNotEnterTurnException;
    void turnEnd();
}
