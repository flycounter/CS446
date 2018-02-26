package cs446_w2018_group3.supercardgame.gamelogic;

import cs446_w2018_group3.supercardgame.runtime.Runtime;
import cs446_w2018_group3.supercardgame.util.events.GameEvent;

/**
 * Created by JarvieK on 2018/2/23.
 */

public class GameLogic {
    private Runtime runtime;
    private static GameLogic gameLogic;

    private GameLogic() { } // disallow public instantiation

    public static GameLogic getInstance() {
        if (gameLogic == null) {
            gameLogic = new GameLogic();
        }

        return gameLogic;
    }

    public void setRuntime(Runtime runtime) {
        this.runtime = runtime;
    }

    public void handleEvent(GameEvent e) {}
}
