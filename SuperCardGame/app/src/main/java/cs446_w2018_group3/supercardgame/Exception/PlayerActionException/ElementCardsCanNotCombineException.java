package cs446_w2018_group3.supercardgame.Exception.PlayerActionException;

import java.util.ArrayList;
import java.util.List;

import cs446_w2018_group3.supercardgame.model.cards.element.ElementCard;

/**
 * Created by JarvieK on 2018/2/26.
 */

public class ElementCardsCanNotCombineException extends PlayerActionException {
    private List<ElementCard> cards;
    private String message;

    public ElementCardsCanNotCombineException(List<ElementCard> cards) {
        super();
        this.cards = cards;

        List<String> labels = new ArrayList<>();
        for (ElementCard card: cards) {
            labels.add(card.getLabel());
        }
        message = "Cannot combine cards: " + labels.toString();
    }

    public List<ElementCard> getCards() { return cards; }

    @Override
    public String getMessage() {
        return message;
    }
}
