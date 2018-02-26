package cs446_w2018_group3.supercardgame.runtime;

import cs446_w2018_group3.supercardgame.gamelogic.GameLogic;
import cs446_w2018_group3.supercardgame.gamelogic.model.Player;

/**
 * Created by JarvieK on 2018/2/24.
 */

public class Runtime {
    private Player player;
    private GameLogic gameLogic;

    public Runtime() {}

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public GameLogic getGameLogic() {
        return gameLogic;
    }

    public void setGameLogic(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }
}
