package cs446_w2018_group3.supercardgame.model;

/**
 * Created by yandong on 2018-02-25.
 */

public class Translate {
     public static enum CardType {
        Water, Fire, Air, Dirt, Aqua, Steam, Ice, Mud, Flame, Blast, Lava, Gale, Sand, Rock, Excalibar, Error
    }

    public static enum Weather {
         Sunny, Rain, Cloudy
    }

    public static enum BuffType {
        Burn
    }

    public static int cardToInt (CardType card) {
         switch (card) {
             case Water: return 0;
             case Fire: return 1;
             case Air: return 2;
             case Dirt: return 3;
             default: return -1;
         }
    }

    public static String cardToString (CardType card) {
        switch (card) {
            case Water: return "Water";
            case Fire: return "Fire";
            case Air: return "Air";
            case Dirt: return "Dirt";
            default: return "Error";
        }
    }

    public static CardType intToCard (int card) {
        switch (card) {
            case 0: return CardType.Water;
            case 1: return CardType.Fire;
            case 2: return CardType.Air;
            case 3: return CardType.Dirt;
            default: return CardType.Error;
        }
    }
}
