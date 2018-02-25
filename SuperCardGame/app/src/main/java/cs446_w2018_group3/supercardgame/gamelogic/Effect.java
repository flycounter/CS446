package cs446_w2018_group3.supercardgame.gamelogic;

import cs446_w2018_group3.supercardgame.util.events.GameEvent;
import cs446_w2018_group3.supercardgame.util.events.PlayerEvent;
import cs446_w2018_group3.supercardgame.model.Card;

/**
 * Created by JarvieK on 2018/2/24.
 */

public class Effect {

    abstract class BaseEffect {
        GameLogic gameLogic;
        int actionCost;

        BaseEffect(GameLogic gameLogic) {
            this.gameLogic = gameLogic;
        }

        public abstract void useEffect(GameEvent e);
    }

    public class UseElementEffect extends BaseEffect {
        public UseElementEffect(GameLogic gameLogic) {
            super(gameLogic);
        }

        @Override
        public void useEffect(GameEvent _e) {
            PlayerEvent e = (PlayerEvent) _e;
            e.UseElementAction.object.setHP (e.UseElementAction.getHP() - 1);
        }
    }
}
