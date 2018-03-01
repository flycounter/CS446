package cs446_w2018_group3.supercardgame.runtime;

import cs446_w2018_group3.supercardgame.Exceptions.PlayerActionException.PlayerCanNotEnterTurnException;

/**
 * Created by JarvieK on 2018/3/1.
 */

public interface GameStateControl {
    void start();
    void beforeTurnStart() throws PlayerCanNotEnterTurnException;
    void turnStart();
    void afterTurnStart();
    void beforeTurnEnd();
    void turnEnd();
    void afterTurnEnd();
}
