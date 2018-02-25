package cs446_w2018_group3.supercardgame.ViewGameLogicConnector;


import cs446_w2018_group3.supercardgame.gamelogic.GameLogic;
import cs446_w2018_group3.supercardgame.util.events.GameEvent;
import cs446_w2018_group3.supercardgame.util.events.PlayerEvent;

/**
 * Created by JarvieK on 2018/2/23.
 */

public class CombineActionConnector implements IConnector {
    GameLogic gameLogic;

    public CombineActionConnector(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    @Override
    public void dispatch(GameEvent _e) {
//        PlayerEvent.CombineAction e = (PlayerEvent.CombineAction) _e;
//        gameLogic.combineAction.performAction((PlayerEvent.CombineAction).getSubject(), e.)
    }
}
