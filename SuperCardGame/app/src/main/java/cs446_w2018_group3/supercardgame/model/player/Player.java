package cs446_w2018_group3.supercardgame.model.player;

import java.util.ArrayList;
import java.util.List;

import cs446_w2018_group3.supercardgame.model.buffs.Buff;
import cs446_w2018_group3.supercardgame.model.cards.Card;
import cs446_w2018_group3.supercardgame.model.cards.ElementCard;

/**
 * Created by JarvieK on 2018/2/23.
 */

public class Player {
    public final static int PLAYER_MAX_AP = 10;
    public final static String PLAYER_DEFAULT_NAME = "default_name";
    public final static String PLAYER_DEFAULT_CLASS = "default_class";
    public final static int PLAYER_DEFAULT_HP = 10;
    public final static int PLAYER_DEFAULT_AP = 10;
    public final static int PLAYER_DEFAULT_SHIELD = 0;


    private int id;
    private String name = PLAYER_DEFAULT_NAME;
    private String playerClass = PLAYER_DEFAULT_CLASS;
    private int hp = PLAYER_DEFAULT_HP;
    private int ap = PLAYER_DEFAULT_AP;
    private int shield;
    private List<ElementCard> deck;
    private List<ElementCard> hand;
    private List<Buff> buffs;

    public class test {}

    public Player( int id, String name ) {
        this.id = id;
        this.name = name;
        shield = 0;
        deck = new ArrayList<ElementCard>();
        hand = new ArrayList<ElementCard>();
        buffs = new ArrayList<>();
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getPlayerClass() { return playerClass; }
    public int getHP() {
        return hp;
    }
    public int getAP() {
        return ap;
    }
    public int getShield() {
        return shield;
    }
    public List<ElementCard> getHand() {
        return hand;
    }
    public List<ElementCard> getDeck() { return deck; }
    public List<Buff> getBuffs() { return buffs; }

    public void setHP( int newHP ) {
        hp = newHP;
    }
    public void setAP( int newAP ) {
        ap = newAP;
    }
    public void setPlayerClass (String playerClass ) { this.playerClass = playerClass; }
    public void setShield( int newShield ) {
        shield = newShield;
    }
    public void addCardToHand(ElementCard c ) {
        hand.add(c);
    }
    public void removeCardFromHand(Card c ) {
        hand.remove(c);
    }
    public void setDeck(List<ElementCard> deck) {
        this.deck.clear();
        this.deck.addAll(deck);
    }
    public void addBuff (Buff b) { buffs.add(b); }
    public void removeBuff(Buff b) { buffs.remove(b); }
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
        List<Buff> buffsToRemove = new ArrayList<>();
        for ( Buff b: buffs ) {
            turns = b.applyBuff();
            if ( turns == 0 ) {
                buffsToRemove.add(b);
            }
        }
        buffs.removeAll(buffsToRemove);
    }

    @Override
    public boolean equals(Object that) {
        return (that instanceof Player && ((Player) that).getId() == this.getId());
    }
}
