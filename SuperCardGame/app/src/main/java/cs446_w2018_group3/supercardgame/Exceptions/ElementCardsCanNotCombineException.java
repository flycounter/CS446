package cs446_w2018_group3.supercardgame.Exceptions;

import android.sax.Element;

import java.util.List;

import cs446_w2018_group3.supercardgame.model.cards.ElementCard;

/**
 * Created by JarvieK on 2018/2/26.
 */

public class ElementCardsCanNotCombineException extends RuntimeException {
    private List<ElementCard> cards;

    public ElementCardsCanNotCombineException(List<ElementCard> cards) {
        super();
        this.cards = cards;
    }

    public List<ElementCard> getCards() { return cards; }
}
