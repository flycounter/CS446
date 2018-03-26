package cs446_w2018_group3.supercardgame.model.cards;

import cs446_w2018_group3.supercardgame.util.events.payload.CardInfo;
import cs446_w2018_group3.supercardgame.model.Player;
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
    static int id = 0;
    int cardId;
    Translate.CardType cardType;

    public abstract void apply(Player subject, Player object);

    public Card(Translate.CardType cardType) {
        this.cardType = cardType;
        this.cardId = id;
        id++;
    }

    public int getCardId() {
        return this.cardId;
    }

    public Translate.CardType getCardType() {
        return cardType;
    }

    public static Card createNewCard (Translate.CardType cardType) {
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
        return null;
    }
    public static String getInfo(Translate.CardType cardType){

        switch (cardType){
            case  Water: return "Water card.Base element.Deal 1 damage.";
            case  Fire: return "Fire card.Base element.Deal 1 damage.";
            case  Air: return "Air card.Base element.Deal 1 damage.";
            case  Dirt: return "Dirt card.Base element.Deal 1 damage.";
            case  Aqua: return "Aqua card.High level element.Combine by 2 water.Deal 3 damage.";
            case  Steam: return "Steam card.Combined element.Combine by water and fire.Your opponent might miss attack in next 3 turns.";
            case  Ice: return "Ice card.Combined element.Combine by water and air.Freeze your opponent and he will lose the ability of combining elements in next turn.";
            case  Mud: return "Mud card.Combined element.Combine by dirt and water.So hard to move in mud!Decrease your opponent ap by 3 in next turn.";
            case  Flame: return "Flame card.High level element.Combine by 2 fire.Deal 2 damage per turn to your opponent.Last 2 turns.";
            case  Blast: return "Blast card.Combined element.Combine by fire and air.";
            case Lava: return "Lava card.Combined element.Combine by fire and dirt.";
            case Gale: return "Gale card.High level element.Combine by 2 air.Deal 3 damage.";
            case Sand: return "Sand card.Combined element.Combine by dirt and air.Gain 3 shields.";
            case Rock: return "Rock card.High level element.Combine by 2 dirt.Deal 3 damage.";
        }
        return "unkonwed card.";
    }

    public CardInfo getCardInfo(){return new CardInfo(cardId,cardType);}

}


