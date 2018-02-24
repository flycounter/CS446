package cs446_w2018_group3.supercardgame.model;

/**
 * Created by JarvieK on 2018/2/23.
 */

public class Card {
    abstract class BaseCard implements IModel {
        int id;
        String label;
        public abstract void apply();
    }

    public class ElementCard extends BaseCard {
        int level;
        @Override
        public void apply() {}
    }

    public class ItemCard extends BaseCard {
        @Override
        public void apply() {}
    }

    public class WaterCard extends ElementCard {
        public WaterCard () {
            this.id = 0;
            this.label = "Water";
            this.level = 1;
        }
    }

    public class FireCard extends ElementCard {
        public FireCard () {
            this.id = 1;
            this.label = "Fire";
            this.level = 1;
        }
    }

    public class AirCard extends ElementCard {
        public AirCard () {
            this.id = 2;
            this.label = "Air";
            this.level = 1;
        }
    }

    public class DirtCard extends ElementCard {
        public DirtCard () {
            this.id = 3;
            this.label = "Dirt";
            this.level = 1;
        }
    }

}
