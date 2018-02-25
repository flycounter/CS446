package cs446_w2018_group3.supercardgame.model;

import cs446_w2018_group3.supercardgame.gamelogic.Effect;
/**
 * Created by JarvieK on 2018/2/23.
 */
//  Card Id Reference:
//  0: Water
//  1: Fire
//  2: Air
//  3: Dirt
//  4: Aqua
//  5: Steam
//  6: Ice
//  7: Mud
//  8: Flame
//  9: Blast
//  10: Lava
//  11: Gale
//  12: Sand
//  13: Rock

public class Card {
    abstract class BaseCard implements IModel {
        int id;
        String label;

        public abstract void apply();
        public int getId() {
            return this.id;
        }
        public String getLabel() {
            return this.label;
        }
    }

    public class ElementCard extends BaseCard {
        int level;
        int damage;
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
            this.damage = 1;
        }

        public void apply(Player subject, Player object) {
            Effect.dealDamageEffect(subject, object, damage);
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
