package cs446_w2018_group3.supercardgame.model.cards;

import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.model.Translate;

/**
 * Created by yandong on 2018-02-25.
 */

public class ItemCard extends Card {
    int damage;
    public ItemCard (Translate.CardType cardType, int damage) {
        super(cardType);
        this.damage = damage;
    }

    @Override
    public void apply(Player subject, Player object) {}
}

