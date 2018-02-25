package cs446_w2018_group3.supercardgame.model;

import java.util.ArrayList;
import cs446_w2018_group3.supercardgame.model.buffs.Buff;
import cs446_w2018_group3.supercardgame.model.cards.Card;

/**
 * Created by JarvieK on 2018/2/23.
 */

public class Player implements IModel {
    private int id;
    private String name;
    private int health;
    private int actionPoint;
    private ArrayList<Card> hand;
    private ArrayList<Buff> buffs;

    public Player( int id, String name ) {
        this.id = id;
        this.name = name;
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
    public void setHP( int newHP ) {
        health = newHP;
    }

    public int getAP() {
        return actionPoint;
    }
    public void setAP( int newAP ) {
        actionPoint = newAP;
    }

    public void insertCard( Card c ) {
        hand.add(c);
    }
    public void removeCard( Card c ) {
        hand.remove(c);
    }
    public ArrayList<Card> getHand() {
        return hand;
    }

    public void insertHandler( Buff h ) { buffs.add(h); }
    public void applyBuff() {
        int turns;
        for ( Buff b: buffs ) {
            turns = b.applyBuff();
        }
    }
}
