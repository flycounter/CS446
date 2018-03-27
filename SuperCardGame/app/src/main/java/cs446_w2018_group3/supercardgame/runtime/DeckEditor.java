package cs446_w2018_group3.supercardgame.runtime;

import cs446_w2018_group3.supercardgame.Exception.DeckEditException.DeckEditException;
import cs446_w2018_group3.supercardgame.Exception.DeckEditException.NotEnoughDeckException;
import cs446_w2018_group3.supercardgame.Exception.DeckEditException.NotEnoughMoneyException;
import cs446_w2018_group3.supercardgame.model.DBHelper;
import cs446_w2018_group3.supercardgame.model.Translate;

import android.database.Cursor;
import android.util.Log;
import cs446_w2018_group3.supercardgame.runtime.IDeckEditor;
/**
 * Created by yandong on 2018-03-25.
 */

public class DeckEditor implements IDeckEditor {
    private int CARD_COST = 5;
    private int DECK_MIN = 20;
    //private int gold;
    //private int water;
    //private int fire;
    //private int air;
    //private int dirt;
    private DBHelper deck;

    public DeckEditor(DBHelper deck) {
        this.deck = deck;
    }

    public void addCard(Translate.CardType cardType) {
        Cursor ret = deck.getData(1);
        int gold = ret.getInt(ret.getColumnIndex(DBHelper.DECK_COLUMN_GOLD));
        int maxType;
        try {
            if (gold < CARD_COST) {
                throw new NotEnoughMoneyException();
            }
            switch (cardType) {
                case Water: {
                    maxType = ret.getInt(ret.getColumnIndex(DBHelper.DECK_COLUMN_MAX_WATER));
                    deck.updateMaxCard("max_water", gold-CARD_COST, maxType+1);
                }
                case Fire: {
                    maxType = ret.getInt(ret.getColumnIndex(DBHelper.DECK_COLUMN_MAX_FIRE));
                    deck.updateMaxCard("max_fire", gold-CARD_COST, maxType+1);
                }
                case Air: {
                    maxType = ret.getInt(ret.getColumnIndex(DBHelper.DECK_COLUMN_MAX_AIR));
                    deck.updateMaxCard("max_air", gold-CARD_COST, maxType+1);
                }
                case Dirt: {
                    maxType = ret.getInt(ret.getColumnIndex(DBHelper.DECK_COLUMN_MAX_DIRT));
                    deck.updateMaxCard("max_dirt", gold-CARD_COST, maxType+1);
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
