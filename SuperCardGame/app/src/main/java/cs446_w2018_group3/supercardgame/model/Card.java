package cs446_w2018_group3.supercardgame.model;

/**
 * Created by JarvieK on 2018/2/23.
 */

public class Card {
    abstract class BaseCard implements IModel {
        String id;
        String label;

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
