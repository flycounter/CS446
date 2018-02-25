package cs446_w2018_group3.supercardgame.gamelogic;

import cs446_w2018_group3.supercardgame.util.events.GameEvent;
import cs446_w2018_group3.supercardgame.util.events.PlayerEvent;

/**
 * Created by JarvieK on 2018/2/24.
 */

public class Handler {
    abstract class BaseHandler {
        GameLogic gameLogic;
        int actionCost;

        BaseHandler(GameLogic gameLogic) {
            this.gameLogic = gameLogic;
        }

        public abstract void handleAction(GameEvent e);
    }

    public class CombineHandler extends BaseHandler {
        public CombineHandler(GameLogic gameLogic) { super(gameLogic); }

        @Override
        public void handleAction(GameEvent _e) {
            PlayerEvent e = (PlayerEvent) _e;


            // ...

        }
    }

    public class UseElementHandler extends BaseHandler {
        public UseElementHandler(GameLogic gameLogic) {
            super(gameLogic);
        }

        @Override
        public void handleAction(GameEvent _e) {
            PlayerEvent e = (PlayerEvent) _e;
            // ElementCard card = e.action.card;
            // ...
        }
    }
}
