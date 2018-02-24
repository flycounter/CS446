package cs446_w2018_group3.supercardgame.ViewGameLogicConnector;

import cs446_w2018_group3.supercardgame.util.events.GameEvent;

/**
 * Created by JarvieK on 2018/2/23.
 */

interface IConnector {
    public void dispatch(GameEvent e);
}
