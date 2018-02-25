package cs446_w2018_group3.supercardgame.gamelogic;

import cs446_w2018_group3.supercardgame.model.Player;
import cs446_w2018_group3.supercardgame.util.events.GameEvent;
import cs446_w2018_group3.supercardgame.util.events.PlayerEvent;
import cs446_w2018_group3.supercardgame.model.Card;

/**
 * Created by JarvieK on 2018/2/24.
 */

public class Effect {

    public abstract class BaseEffect {
        GameLogic gameLogic;
        int actionCost;

        BaseEffect(GameLogic gameLogic) {
            this.gameLogic = gameLogic;
        }

        public abstract void applyEffect(Player subject, Player object);
    }

    public class UseElementEffect extends BaseEffect {
        int damage;
        public UseElementEffect(GameLogic gameLogic) {
            super(gameLogic);
        }

        @Override
        public void applyEffect(Player subject, Player object) {
            object.setHP ( object.getHP() - 1 );
        }
    }
}
