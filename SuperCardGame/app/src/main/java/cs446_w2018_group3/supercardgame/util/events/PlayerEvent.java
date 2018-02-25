package cs446_w2018_group3.supercardgame.util.events;

import cs446_w2018_group3.supercardgame.model.cards.Card;
import cs446_w2018_group3.supercardgame.model.Player;

import java.util.List;

/**
 * Created by JarvieK on 2018/2/23.
 */

public class PlayerEvent extends GameEvent {
    private final BaseAction action;

    public PlayerEvent(BaseAction action) {
        this.action = action;
    }

    public BaseAction getAction() {
        return action;
    }

    abstract class BaseAction {
        Player subject;

        BaseAction(Player player) {
            this.subject = player;
        }

        Player getSubject() {
            return this.subject;
        }
    }

    public class CombineAction extends BaseAction {
        List<Card.ElementCard> cards;

        public CombineAction(Player player, List<Card.ElementCard> cards) {
            super(player);
            this.cards = cards;
        }

        public List<Card.ElementCard> getCards() {
            return this.cards;
        }
    }

    public class UseElementAction extends BaseAction {
        public Player object;
        Card.ElementCard card;

        public UseElementAction(Player subject, Player object, Card.ElementCard card) {
            super(subject);
            this.object = object;
            this.card = card;
        }
    }

    public class UseItemAction extends BaseAction {
        Card.ItemCard card;

        public UseItemAction(Player player, Card.ItemCard card) {
            super(player);
            this.card = card;
        }
    }
}
