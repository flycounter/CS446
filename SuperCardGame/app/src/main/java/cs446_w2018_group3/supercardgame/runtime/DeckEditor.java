package cs446_w2018_group3.supercardgame.runtime;

import cs446_w2018_group3.supercardgame.Exception.DeckEditException.DeckEditException;
import cs446_w2018_group3.supercardgame.Exception.DeckEditException.NotEnoughDeckException;
import cs446_w2018_group3.supercardgame.Exception.DeckEditException.NotEnoughMoneyException;
import cs446_w2018_group3.supercardgame.model.DBHelper;
import cs446_w2018_group3.supercardgame.model.Translate;
import android.util.Log;
import cs446_w2018_group3.supercardgame.runtime.IDeckEditor;
/**
 * Created by yandong on 2018-03-25.
 */

public class DeckEditor implements IDeckEditor {
    private int CARD_COST = 50;
    private int DECK_MIN = 20;
    private int gold;
    //private int water;
    //private int fire;
    //private int air;
    //private int dirt;
    private int maxWater;
    private int maxFire;
    private int maxAir;
    private int maxDirt;
    private DBHelper deck;

    public DeckEditor( int gold, int maxWater, int maxFire, int maxAir, int maxDirt, DBHelper deck) {

        this.gold = gold;
        this.maxWater = maxWater;
        this.maxFire = maxFire;
        this.maxAir = maxAir;
        this.maxDirt = maxDirt;
        this.deck = deck;
    }

    public void addCard(Translate.CardType cardType) {
        try {
            if (gold < CARD_COST) {
                throw new NotEnoughMoneyException();
            }
            gold = gold - CARD_COST;
            switch (cardType) {
                case Water: {
                    maxWater++;
                    deck.updateMaxCard("max_water", gold, maxWater);
                }
                case Fire: {
                    maxFire++;
                    deck.updateMaxCard("max_fire", gold, maxFire);
                }
                case Air: {
                    maxAir++;
                    deck.updateMaxCard("max_air", gold, maxAir);
                }
                case Dirt: {
                    maxDirt++;
                    deck.updateMaxCard("max_dirt", gold, maxDirt);
                }
                default: {
                    throw new NotEnoughMoneyException();
                }
            }
        } catch (DeckEditException err) {
            Log.w("main", err);
            // TODO: send err to UI
        }
    }

    public void editDeck (int water, int fire, int air, int dirt) {
        try {
            if (water + fire + air + dirt < DECK_MIN){
                throw new NotEnoughDeckException();
            }
            deck.updateDeck(water, fire, air, dirt);
        }
        catch (DeckEditException err) {
            Log.w("main", err);
            // TODO: send err to UI
        }
    }
}
