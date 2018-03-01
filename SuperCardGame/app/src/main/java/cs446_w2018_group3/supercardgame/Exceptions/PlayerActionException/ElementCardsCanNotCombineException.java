package cs446_w2018_group3.supercardgame.Exceptions.PlayerActionException;

import java.util.List;
import java.util.stream.Collectors;

import cs446_w2018_group3.supercardgame.model.cards.Card;
import cs446_w2018_group3.supercardgame.model.cards.ElementCard;

/**
 * Created by JarvieK on 2018/2/26.
 */

public class ElementCardsCanNotCombineException extends PlayerActionException {
    private List<ElementCard> cards;

    public ElementCardsCanNotCombineException(List<ElementCard> cards) {
        super(String.format("cannot combine cards: %s",
                cards.stream()
                        .map(Card::getLabel)
                        .collect(Collectors.toList())
                ));
        this.cards = cards;
    }

    public List<ElementCard> getCards() { return cards; }
}
