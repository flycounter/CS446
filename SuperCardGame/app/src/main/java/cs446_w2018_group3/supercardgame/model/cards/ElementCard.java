package cs446_w2018_group3.supercardgame.model.cards;

import cs446_w2018_group3.supercardgame.info.CardInfo;
import cs446_w2018_group3.supercardgame.model.Player;
import cs446_w2018_group3.supercardgame.model.Translate;
import cs446_w2018_group3.supercardgame.model.cards.Card;

/**
 * Created by yandong on 2018-02-25.
 */

public class ElementCard extends Card {
    int cardTypeId;
    int level;
    int damage;
    @Override
    public void apply(Player subject, Player object) {}

    public ElementCard(Translate.CardType cardType, int level, int damage) {
        super(cardType);
        this.level = level;
        this.damage = damage;
        this.cardTypeId = Translate.cardToInt(cardType);
    }

    public int getCardTypeId(){
        return cardTypeId;
    }

}
