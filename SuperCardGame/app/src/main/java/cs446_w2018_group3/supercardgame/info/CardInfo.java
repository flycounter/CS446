package cs446_w2018_group3.supercardgame.info;

import cs446_w2018_group3.supercardgame.model.Translate;

/**
 * Created by yandong on 2018-02-25.
 */

public class CardInfo {
    public int cardId;
    public Translate.CardType cardType;
    public String cardTypeStr;

    public CardInfo (int cardId, Translate.CardType cardType) {
        this.cardId = cardId;
        this.cardType = cardType;
        this.cardTypeStr = Translate.cardToString(cardType);
    }
}
