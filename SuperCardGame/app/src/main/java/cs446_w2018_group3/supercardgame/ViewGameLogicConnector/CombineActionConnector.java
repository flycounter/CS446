package cs446_w2018_group3.supercardgame.ViewGameLogicConnector;


import cs446_w2018_group3.supercardgame.gamelogic.GameLogic;

/**
 * Created by JarvieK on 2018/2/23.
 */

public class CombineActionConnector implements IConnector {
    GameLogic gameLogic;

    public CombineActionConnector(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    @Override
    public void dispatch(GameEvent e) {
        gameLogic.
    }
}
