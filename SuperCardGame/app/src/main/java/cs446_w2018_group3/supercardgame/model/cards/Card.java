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

    public CardInfo getCardInfo () {
        return new CardInfo(cardId, cardType);
    }
}


