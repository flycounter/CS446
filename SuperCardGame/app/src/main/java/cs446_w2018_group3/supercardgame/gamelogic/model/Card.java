package cs446_w2018_group3.supercardgame.gamelogic.model;

/**
 * Created by JarvieK on 2018/2/23.
 */

public class Card {
    abstract class BaseCard {
        String id;
        String label;

        private BaseCard() {}

        public abstract void apply();
    }

    public class ElementCard extends BaseCard {
        @Override
        public void apply() {}
    }

    public class ItemCard extends BaseCard {
        @Override
        public void apply() {}
    }
}
