package cs446_w2018_group3.supercardgame.model;

import android.util.Log;

/**
 * Created by yandong on 2018-02-25.
 */

public class Translate {
    private static final String TAG = Translate.class.getName();

    public static enum CardType {
        Water, Fire, Air, Dirt, Aqua, Steam, Ice, Mud, Flame, Blast, Lava, Gale, Sand, Rock, Excalibar, Error
    }

    public static enum Weather {
        Sunny, Rainy, Cloudy
    }

    public static enum BuffType {
        Burn, Dodge, Wet, Freeze
    }

    public static int cardToInt(CardType card) {
        switch (card) {
            case Water:
                return 0;
            case Fire:
                return 1;
            case Air:
                return 2;
            case Dirt:
                return 3;
            default:
                return -1;
        }
    }

    public static String cardToString(CardType card) {
        switch (card) {
            case Water:
                return "Water";
            case Fire:
                return "Fire";
            case Air:
                return "Air";
            case Dirt:
                return "Dirt";
            case Aqua:
                return "Aqua";
            case Mud:
                return "Mud";
            case Steam:
                return "Steam";
            case Sand:
                return "Sand";
            case Ice:
                return "Ice";
            case Flame:
                return "Flame";
            case Blast:
                return "Blast";
            case Lava:
                return "Lava";
            case Gale:
                return "Gale";
            case Rock:
                return "Rock";
            default: {
                Log.e(TAG, "cannot convert CardType to string " + card);
                return "Error";
            }
        }
    }

    public static CardType stringToCard(String s) {
        switch (s) {
            case "Water":
                return CardType.Water;
            case "Fire":
                return CardType.Fire;
            case "Air":
                return CardType.Air;
            case "Dirt":
                return CardType.Dirt;
            case "Aqua":
                return CardType.Aqua;
            case "Mud":
                return CardType.Mud;
            case "Steam":
                return CardType.Steam;
            case "Sand":
                return CardType.Sand;
            case "Ice":
                return CardType.Ice;
            case "Flame":
                return CardType.Flame;
            case "Blast":
                return CardType.Blast;
            case "Lava":
                return CardType.Lava;
            case "Gale":
                return CardType.Gale;
            case "Rock":
                return CardType.Rock;
            default:
                Log.e(TAG, "cannot parse into CardType: " + s);
                return CardType.Error;
        }
    }

    public static CardType intToCard(int card) {
        switch (card) {
            case 0:
                return CardType.Water;
            case 1:
                return CardType.Fire;
            case 2:
                return CardType.Air;
            case 3:
                return CardType.Dirt;
            default:
                return CardType.Error;
        }
    }
}
