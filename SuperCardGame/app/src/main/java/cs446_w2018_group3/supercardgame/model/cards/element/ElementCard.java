package cs446_w2018_group3.supercardgame.model.cards.element;

import android.util.Log;

import cs446_w2018_group3.supercardgame.model.cards.Card;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.model.Translate;

/**
 * Created by yandong on 2018-02-25.
 */

public abstract class ElementCard extends Card {
    private static final String TAG = ElementCard.class.getName();

    public static final Translate.CardType[][] combineRule = {
            {Translate.CardType.Aqua, Translate.CardType.Steam, Translate.CardType.Ice, Translate.CardType.Mud},
            {Translate.CardType.Steam, Translate.CardType.Flame, Translate.CardType.Blast, Translate.CardType.Lava},
            {Translate.CardType.Ice, Translate.CardType.Blast, Translate.CardType.Gale, Translate.CardType.Sand},
            {Translate.CardType.Mud, Translate.CardType.Lava, Translate.CardType.Sand, Translate.CardType.Rock}};

    private final int level;
    private final int damage;

    @Override
    public void apply(Player subject, Player object) {
        Log.e(TAG, "stub!");
    }

    public static Boolean canCombine(ElementCard card1, ElementCard card2) {
        return card1.getLevel() == 1 && card2.getLevel() == 1;
    }

    public static Translate.CardType combine(Translate.CardType cardType1, Translate.CardType cardType2) {
        int cardIntType1 = Translate.cardToInt(cardType1);
        int cardIntType2 = Translate.cardToInt(cardType2);

        return combineRule[cardIntType1][cardIntType2];
    }

    ElementCard(Translate.CardType cardType, int level, int damage) {
        super(cardType);

        this.level = level;
        this.damage = damage;
    }

    ElementCard(Translate.CardType cardType, int level, int damage, int id) {
        super(cardType, id);
        this.level = level;
        this.damage = damage;
    }

//    public ElementCardFactory getElementCardFactory(Translate.CardType cardType) {
//        // TODO: implement using factory pattern to properly create cards
//        return null;
//    }

    public int getLevel() {
        return level;
    }

    public int getDamage() {
        return damage;
    }
}
