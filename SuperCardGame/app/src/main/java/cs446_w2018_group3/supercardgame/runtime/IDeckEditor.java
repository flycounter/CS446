package cs446_w2018_group3.supercardgame.runtime;

/**
 * Created by yandong on 2018-03-18.
 */
import cs446_w2018_group3.supercardgame.model.Translate;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.model.cards.Card;
import cs446_w2018_group3.supercardgame.Exception.DeckEditException.*;


interface IDeckEditor {
    //void setPlayer(Player player);
    void addCard(Translate.CardType cardType) throws NotEnoughMoneyException;
    //void removeCard(Card card) throws CannotRemoveCardException;
    void editDeck(int water, int fire, int air, int dirt) throws NotEnoughDeckException;
    //Player getEditedPlayer();
}
