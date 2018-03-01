package cs446_w2018_group3.supercardgame.model;

import java.util.ArrayList;
import java.util.List;

import cs446_w2018_group3.supercardgame.model.buffs.*;
import cs446_w2018_group3.supercardgame.model.cards.*;
//import cs446_w2018_group3.supercardgame.util.events.payload.BuffInfo;
//import cs446_w2018_group3.supercardgame.util.events.payload.CardInfo;


/**
 * Created by yandong on 2018-02-25.
 */

public class PlayerInfo {
    String name;
    int playerId;
    int HP;
    int AP;
    int shield;
    List<Card> hands = new ArrayList<Card>();
    List<Card> deck = new ArrayList<Card>();
    ArrayList<Buff> buffs = new ArrayList<Buff>();

    public PlayerInfo(String name, int playerId){
        this.name = name;
        this.playerId = playerId;
    }

    public int getId() {
        return playerId;
    }
    public String getName() {
        return name;
    }
    public int getHP() {
        return HP;
    }
    public int getAP() {
        return AP;
    }
    public int getShield() {
        return shield;
    }
    public List<CardInfo> getHand() {
        List<CardInfo> result = new ArrayList<>();
        result.addAll(hands);
        return result;
    }
    //public List<Card> getDeck() { return deck; }
    public List<BuffInfo> getBuffs() {
        List<BuffInfo> result = new ArrayList<>();
        result.addAll(buffs);
        return result;
    }
}
