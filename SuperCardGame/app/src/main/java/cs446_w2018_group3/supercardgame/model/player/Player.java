package cs446_w2018_group3.supercardgame.model.player;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import cs446_w2018_group3.supercardgame.util.events.payload.*;
import cs446_w2018_group3.supercardgame.model.buffs.Buff;
import cs446_w2018_group3.supercardgame.model.cards.Card;

/**
 * Created by JarvieK on 2018/2/23.
 */

public class Player {
    public final static int PLAYER_MAX_AP = 10;
    private int id;
    private String name;
    private int health;
    private int actionPoint;
    private int shield;
    private List<Card> deck;
    public List<Card> hand;
    private List<Buff> buffs;

    public class test {}

    public Player( int id, String name ) {
        this.id = id;
        this.name = name;
        shield = 0;
        hand = new ArrayList<Card>();
        buffs = new ArrayList<Buff>();
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getHP() {
        return health;
    }
    public int getAP() {
        return actionPoint;
    }
    public int getShield() {
        return shield;
    }
    public List<Card> getHand() {
        return hand;
    }
    public List<Card> getDeck() { return deck; }
    public List<Buff> getBuffs() { return buffs; }

    public void setHP( int newHP ) {
        health = newHP;
    }
    public void setAP( int newAP ) {
        actionPoint = newAP;
    }
    public void setShield( int newShield ) {
        shield = newShield;
    }
    public void addCardToHand( Card c ) {
        hand.add(c);
    }
    public void removeCardFromHand(Card c ) {
        hand.remove(c);
    }
    public void setDeck(List<Card> deck) { this.deck = deck; }
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

    public void addAP(int amount) {
        setAP(Math.min(PLAYER_MAX_AP, getAP() + amount));
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

    public PlayerInfo getPlayerInfo() {
        ArrayList<CardInfo> tempCardInfoList = new ArrayList<CardInfo>();
        ArrayList<BuffPayload> tempBuffPayloadList = new ArrayList<BuffPayload>();

        for ( Card c: hand ) {
            tempCardInfoList.add( c.getCardInfo() );
        }

        for ( Buff b: buffs ) {
            tempBuffPayloadList.add( b.getBuffInfo() );
        }

        return new PlayerInfo(name, id, health, actionPoint, tempCardInfoList, tempBuffPayloadList);
    }

    boolean equals(@Nullable Player that) {
        return that != null && this.getId() == that.getId();
    }
}
