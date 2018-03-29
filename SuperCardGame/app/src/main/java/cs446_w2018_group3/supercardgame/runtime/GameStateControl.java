package cs446_w2018_group3.supercardgame.runtime;

import cs446_w2018_group3.supercardgame.Exception.PlayerActionException.PlayerCanNotEnterTurnException;

/**
 * Created by JarvieK on 2018/3/1.
 */

public interface GameStateControl {
    void start();
    void turnStart() throws PlayerCanNotEnterTurnException;
    void turnEnd();
    GameFSM.State getState();


}
