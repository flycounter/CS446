package cs446_w2018_group3.supercardgame.model;

import android.util.Log;

import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.model.buffs.Buff;
import cs446_w2018_group3.supercardgame.model.buffs.DodgeBuff;

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
        for ( Buff b : object.getBuffs() ) {
            if ( b instanceof DodgeBuff) {
                return;
            }
        }   // check Dodge
        int shield = object.getShield();
        int curDamage = Math.max(0, damage - shield);
        shield = Math.max(0,shield - damage);

        object.setShield(shield);
        object.setHP(object.getHP() - curDamage);
    }

    public static void decreaseAP ( Player subject, Player object, int num) {
        int newAP = object.getAP() - num;
        object.setAP( Math.max(0, newAP) );
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
