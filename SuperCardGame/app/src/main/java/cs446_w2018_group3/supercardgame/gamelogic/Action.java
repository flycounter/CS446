package cs446_w2018_group3.supercardgame.gamelogic;

import android.util.Log;

import cs446_w2018_group3.supercardgame.util.events.GameEvent;
import cs446_w2018_group3.supercardgame.util.events.PlayerEvent;

/**
 * Created by JarvieK on 2018/2/24.
 */

public class Action {
    abstract class BaseAction {
        GameLogic gameLogic;
        int actionCost;

        BaseAction(GameLogic gameLogic) {
            this.gameLogic = gameLogic;
        }

        public abstract void performAction(GameEvent e);
    }

    public class CombineAction extends BaseAction {
        public CombineAction(GameLogic gameLogic) {
            super(gameLogic);
        }

        @Override
        public void performAction(GameEvent _e) {
            PlayerEvent e = (PlayerEvent) _e;
            // ...
        }
    }

    public class UseElementAction extends BaseAction {
        public UseElementAction(GameLogic gameLogic) {
            super(gameLogic);
        }

        @Override
        public void performAction(GameEvent _e) {
            PlayerEvent e = (PlayerEvent) _e;
            // ElementCard card = e.action.card;
            // ...
        }
    }
}
