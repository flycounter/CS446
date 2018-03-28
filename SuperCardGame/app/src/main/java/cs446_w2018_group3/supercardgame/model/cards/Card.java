package cs446_w2018_group3.supercardgame.model.cards;

import android.util.Log;

import cs446_w2018_group3.supercardgame.model.cards.element.AirCard;
import cs446_w2018_group3.supercardgame.model.cards.element.AquaCard;
import cs446_w2018_group3.supercardgame.model.cards.element.BlastCard;
import cs446_w2018_group3.supercardgame.model.cards.element.DirtCard;
import cs446_w2018_group3.supercardgame.model.cards.element.ElementCard;
import cs446_w2018_group3.supercardgame.model.cards.element.FireCard;
import cs446_w2018_group3.supercardgame.model.cards.element.FlameCard;
import cs446_w2018_group3.supercardgame.model.cards.element.GaleCard;
import cs446_w2018_group3.supercardgame.model.cards.element.IceCard;
import cs446_w2018_group3.supercardgame.model.cards.element.LavaCard;
import cs446_w2018_group3.supercardgame.model.cards.element.MudCard;
import cs446_w2018_group3.supercardgame.model.cards.element.RockCard;
import cs446_w2018_group3.supercardgame.model.cards.element.SandCard;
import cs446_w2018_group3.supercardgame.model.cards.element.SteamCard;
import cs446_w2018_group3.supercardgame.model.cards.element.WaterCard;
import cs446_w2018_group3.supercardgame.util.events.payload.CardInfo;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.model.Translate;

/**
 * Created by JarvieK on 2018/2/23.
 */
//  Card Id Reference:
//  0: Water
//  1: Fire
//  2: Air
//  3: Dirt
//  4: Aqua
//  5: Steam
//  6: Ice
//  7: Mud
//  8: Flame
//  9: Blast
//  10: Lava
//  11: Gale
//  12: Sand
//  13: Rock

public abstract class Card {
    private static final String TAG = Card.class.getName();

    private static int index = 0;
    private final int id;
    private final Translate.CardType cardType;
    private static final int DEFAULT_COST = 1;

    public abstract void apply(Player subject, Player object);
    
    public Card(Translate.CardType cardType, int id) {
        this.cardType = cardType;
        this.id = id;
    }

    public Card(Translate.CardType cardType) {
        this.cardType = cardType;
        this.id = index;
        index++;
    }

    public int getCost() { return DEFAULT_COST; }

    public int getId() {
        return id;
    }

    public Translate.CardType getCardType() {
        return cardType;
    }

    public static ElementCard createNewCard (Translate.CardType cardType) {
        switch (cardType) {
            case Water: return new WaterCard();
            case Fire: return new FireCard();
            case Air: return new AirCard();
            case Dirt: return new DirtCard();
            case Aqua: return new AquaCard();
            case Flame: return new FlameCard();
            case Rock: return new RockCard();
            case Gale: return new GaleCard();
            case Lava: return new LavaCard();
            case Blast: return new BlastCard();
            case Ice: return new IceCard();
            case Steam: return new SteamCard();
            case Mud: return new MudCard();
            case Sand: return new SandCard();
        }
        Log.e(TAG, "cannot create card with CardType: " + cardType);
        return null;
    }

    public static String getInfo(Translate.CardType cardType){

        switch (cardType) {
            case Water: return "Water card.\nBase element.\nDeals 1 damage.";
            case Fire: return "Fire card.\nBase element.\nDeals 1 damage.";
            case Air: return "Air card.\nBase element.\nDeals 1 damage.";
            case Dirt: return "Dirt card.\nBase element.\nDeals 1 damage.";
            case Aqua: return "Aqua card.\nHigh level element.\nCombine by 2 water.\nDeals 3 damage.";
            case Steam: return "Steam card.\nCombined element.\nCombine by water and fire.\nYour opponent will miss attack in next turn.";
            case Ice: return "Ice card.\nCombined element.\nCombine by water and air.\nFreeze your opponent and he will lose the ability of combining elements in next turn.";
            case Mud: return "Mud card.\nCombined element.\nCombine by dirt and water.\nSo hard to move in mud!Decrease your opponent ap by 3 in next turn.";
            case Flame: return "Flame card.\nHigh level element.\nCombine by 2 fire.\nDeals 2 damage per turn to your opponent.Last 2 turns.";
            case Blast: return "Blast card.\nCombined element.\nCombine by fire and air.";
            case Lava: return "Lava card.\nCombined element.\nCombine by fire and dirt.";
            case Gale: return "Gale card.\nHigh level element.\nCombine by 2 air.\nDeals 3 damage.";
            case Sand: return "Sand card.\nCombined element.\nCombine by dirt and air.\nGain 3 shields.";
            case Rock: return "Rock card.\nHigh level element.\nCombine by 2 dirt.\nDeals 3 damage.";
        }
        return "default_card_description";
    }

    public CardInfo getCardInfo () {
        return new CardInfo(id, cardType);
    }

    public String getLabel() {
        return Translate.cardToString(cardType);
    }

    @Override
    public boolean equals(Object that) {
        return (that instanceof Card && ((Card) that).getId() == this.getId());
    }
}


