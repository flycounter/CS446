package cs446_w2018_group3.supercardgame.model;

import cs446_w2018_group3.supercardgame.model.Player;

/**
 * Created by JarvieK on 2018/2/24.
 */

public class Effect {
    //private static Effect instance;

    //public static Effect getInstance() {
    //    if (instance == null) {
    //        instance = new Effect();
    //    }
    //    return instance;
    //}

    public static void dealDamageEffect ( Player subject, Player object, int damage) {
        object.setHP(object.getHP() - damage);
    }

    public static void decreaseAP ( Player subject, Player object, int num) {
        object.setAP(object.getAP() - num);
    }

    public static void addShield ( Player subject, Player object, int num) {
        subject.setShield( subject.getShield() + num );
    }

//    public static void dealDamageEffect ( Player subject, Player object, int damage) {
//        object.setHP(object.getHP() - damage);
//    }


//    public abstract static class BaseEffect {
//        GameLogic gameLogic;
//        int actionCost;
//
//        BaseEffect() {
//            //this.gameLogic = gameLogic;
//        }
//
//        public abstract void applyEffect(Player subject, Player object);
//    }

//    public static class DealDamageEffect extends BaseEffect {
//        int damage;
//
//        public DealDamageEffect(int damage) {
//            this.damage = damage;
//            //super(gameLogic);
//        }
//
//        @Override
//        public void applyEffect(Player subject, Player object) {
//            object.setHP ( object.getHP() - damage );
//        }
//    }
}
