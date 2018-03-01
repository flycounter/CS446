package cs446_w2018_group3.supercardgame.model;

import java.util.ArrayList;
import java.util.List;

import cs446_w2018_group3.supercardgame.model.buffs.Buff;
import cs446_w2018_group3.supercardgame.model.buffs.BuffInfo;
import cs446_w2018_group3.supercardgame.model.cards.Card;
import cs446_w2018_group3.supercardgame.model.cards.CardInfo;

/**
 * Created by JarvieK on 2018/2/23.
 */

public class Player extends PlayerInfo {
    //private int id;
    //private String name;
    //private int health;
    //private int actionPoint;
    //private int shield;
    //private List<Card> deck;
    //public List<Card> hand;
    //private List<Buff> buffs;

    public class test {}

    public Player( int id, String name ) {
        super (name, id);
    }


    public void setHP( int newHP ) {
        HP = newHP;
    }
    public void setAP( int newAP ) {
        AP = newAP;
    }
    public void setShield( int newShield ) {
        shield = newShield;
    }
    public void addCardToHand( Card c ) {
        hands.add(c);
    }
    public void removeCardFromHand(Card c ) {
        hands.remove(c);
    }
    public void setDeck(List<Card> deck) { deck = deck; }
    public void addBuff (Buff b) {
        if(buffs.size() == 0) {
            buffs = new ArrayList<>();
        }
        buffs.add(b);
    }
    public void removeBuff (int id) {
        for ( Buff b: buffs ) {
            if (b.getBuffId() == id) {
                buffs.remove(b);
                break;
            }
        }
    }

    public void applyBuff() {
        // TODO: separate buff application and buff removal
        // function applyBuff() does not indicate side effects
        int turns;
        ArrayList<Buff> buffsToRemove = new ArrayList<>();
        for ( Buff b: buffs ) {
            turns = b.applyBuff();
            if ( turns == 0 ) {
                buffsToRemove.add(b);
            }
        }
        for ( Buff b: buffsToRemove ) {
            buffs.remove(b);
        }
    }

//    public PlayerInfo getPlayerInfo() {
//        ArrayList<CardInfo> tempCardInfoList = new ArrayList<CardInfo>();
//        ArrayList<BuffInfo> tempBuffPayloadList = new ArrayList<BuffInfo>();
//
//        for ( Card c: hand ) {
//            tempCardInfoList.add( c.getCardInfo() );
//        }
//
//        for ( Buff b: buffs ) {
//            tempBuffPayloadList.add( b.getBuffInfo() );
//        }
//
//        return new PlayerInfo(name, id, health, actionPoint, tempCardInfoList, tempBuffPayloadList);
//    }
}
