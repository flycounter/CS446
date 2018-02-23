package cs446_w2018_group3.supercardgame.model;

/**
 * Created by JarvieK on 2018/2/23.
 */

public class Card {
    abstract class BaseCard {
        String id;
        String label;
    }

    public class ElementCard extends BaseCard {
        public void apply() {}
    }

    public class ItemCard extends BaseCard {
        public void apply() {}
    }
}
