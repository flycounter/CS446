package cs446_w2018_group3.supercardgame.model.cards;

import cs446_w2018_group3.supercardgame.model.Translate;

/**
 * Created by yandong on 2018-02-25.
 */

public class CardInfo {
    static int id = 0;
    int cardId;
    Translate.CardType cardType;
    //public String cardTypeStr;

    public CardInfo (Translate.CardType cardType) {
        cardId = id;
        this.cardType = cardType;
        id++;
        //this.cardTypeStr = Translate.cardToString(cardType);
    }

    public int getCardId() {
        return this.cardId;
    }

    public Translate.CardType getCardType() {
        return cardType;
    }
}
