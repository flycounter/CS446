package cs446_w2018_group3.supercardgame.model.cards;

import cs446_w2018_group3.supercardgame.model.Player;
import cs446_w2018_group3.supercardgame.model.Translate;

/**
 * Created by yandong on 2018-02-25.
 */

public class ItemCard extends Card {
    public ItemCard (Translate.CardType cardType) {
        super(cardType);
    }

    @Override
    public void apply(Player subject, Player object) {}
}

