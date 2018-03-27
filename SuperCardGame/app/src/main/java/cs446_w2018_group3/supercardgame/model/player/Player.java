package cs446_w2018_group3.supercardgame.model.player;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cs446_w2018_group3.supercardgame.model.Translate;
import cs446_w2018_group3.supercardgame.model.buffs.Buff;
import cs446_w2018_group3.supercardgame.model.cards.Card;
import cs446_w2018_group3.supercardgame.model.cards.ElementCard;
import cs446_w2018_group3.supercardgame.model.dao.DaoSession;
import cs446_w2018_group3.supercardgame.model.dao.User;

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
    private List<Integer> collection;// 0:water,1:fire,2:air,3:dirt
    private  List<ElementCard> collectionDeck;

    int gold;

    public class test {}
    public List<ElementCard> getCollectionDeck(){return collectionDeck;}
    public List<ElementCard> createCollectionDeck(List<Integer> mCollection){
            List<ElementCard> collectionDeck=new ArrayList<>();
        for(int i=0;i<mCollection.get(0);i++){
            ElementCard newCard = Card.createNewCard(Translate.CardType.Water);
            collectionDeck.add(newCard);
        }
        for(int i=0;i<mCollection.get(1);i++){
            ElementCard newCard = Card.createNewCard(Translate.CardType.Fire);
            collectionDeck.add(newCard);
        }
        for(int i=0;i<mCollection.get(2);i++){
            ElementCard newCard = Card.createNewCard(Translate.CardType.Air);
            collectionDeck.add(newCard);
        }
        for(int i=0;i<mCollection.get(3);i++){
            ElementCard newCard = Card.createNewCard(Translate.CardType.Dirt);
            collectionDeck.add(newCard);
        }
        return collectionDeck;
    }
    public void setCollectionDeck(List<ElementCard> aDeck){
        this.collectionDeck.clear();
        this.collectionDeck.addAll(aDeck);
    }

    public void setDefault(){
        for(int i=0;i<5;i++){
            ElementCard water =Card.createNewCard(Translate.CardType.Water);
            ElementCard fire = Card.createNewCard(Translate.CardType.Fire);
            ElementCard air =Card.createNewCard(Translate.CardType.Air);
            ElementCard dirt = Card.createNewCard(Translate.CardType.Dirt);
            deck.add(water);
            deck.add(fire);
            deck.add(air);
            deck.add(dirt);
        }
        for(int i=0;i<4;i++){
            collection.add(5);
        }
        setCollectionDeck(createCollectionDeck(collection));
        gold=500;
    }
    public Player( int id, String name ) {
        this.id = id;
        this.name = name;
        shield = 0;
        deck = new ArrayList<ElementCard>();
        hand = new ArrayList<ElementCard>();
        buffs = new ArrayList<>();
        collection = new ArrayList<Integer>();
        collectionDeck = new ArrayList<ElementCard>();
    }
    public void addCardToCollection(ElementCard newCard,int num){
        for(int i=0;i<num;i++) {
            collectionDeck.add(newCard);
        }
        int old=0;
        switch (newCard.getCardType()){
            case Water:
                old = collection.get(0);
                collection.set(0,old+num);
                break;
            case Fire:
                old = collection.get(1);
                collection.set(1,old+num);
                break;
            case Air:
                old = collection.get(2);
                collection.set(2,old+num);
                break;
            case Dirt:
                old = collection.get(3);
                collection.set(3,old+num);
                break;
        }
    }
    public void setCollection(List<Integer> newCollection){
       collection.clear();
       collection.addAll(newCollection);
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
    public List<Integer> getCollection(){return collection;}
    public int getGold(){return gold;}
    public void setGold(int newGold){gold=newGold;}


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
    public void addBuff (Buff b) {
        buffs.add(b);
    }
    public void removeBuff(Buff b) { buffs.remove(b); }
    public void removeBuff (int id) {
        for ( Buff b: buffs ) {
            if (b.getId() == id) {
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
            b.applyBuff(this);
            if (b.getRemainingTurns() <= 0) {
                buffsToRemove.add(b);
            }
        }
        buffs.removeAll(buffsToRemove);
    }

    @Override
    public boolean equals(Object that) {
        return (that instanceof Player && ((Player) that).getId() == this.getId());
    }

    public static Player getLocalPlayer(DaoSession session) {
        return new Gson().fromJson(User.getLocalUser(session.getUserDao()).getPlayerData(), Player.class);
    }
}
