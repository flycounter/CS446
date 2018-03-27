package cs446_w2018_group3.supercardgame.model;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.List;
import java.util.Random;

import cs446_w2018_group3.supercardgame.Exception.PlayerActionException.CardNotFoundException;
import cs446_w2018_group3.supercardgame.Exception.PlayerActionException.ElementCardsCanNotCombineException;
import cs446_w2018_group3.supercardgame.Exception.PlayerActionException.PlayerNotFoundException;
import cs446_w2018_group3.supercardgame.Exception.PlayerActionException.PlayerCanNotEnterTurnException;
import cs446_w2018_group3.supercardgame.model.field.GameField;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.runtime.GameRuntime;
import cs446_w2018_group3.supercardgame.model.cards.*;

/**
 * Created by Yakumo on 2/25/2018.
 */

public class Game {
    // configurations
    private static final String TAG = Game.class.getName();
    public static final int PLAYER_AP_REGEN_PER_TURN = 3;
    public static final int PLAYER_CARD_DRAW_PER_TURN = 5;

    private GameRuntime gameRuntime;
    private Random rng = new Random(System.currentTimeMillis()); // seed = curr unix time

    // Pseudo game constructor. Create a game with a sandbag opponent, and with only water cards in deck. Sunny weather
    public Game() { }

    public void bind(GameRuntime gameRuntime) {
        this.gameRuntime = gameRuntime;
    }

    public void init() throws PlayerNotFoundException {
        // for each player
        for (LiveData<Player> playerHolder: gameRuntime.getPlayers()) {
            Player player = playerHolder.getValue();
            player.setHP(10);
            player.setAP(0);
            List<ElementCard> deck = player.getDeck();
            for( int i = 0; i < 6; i++ ) {
                deck.add( ElementCard.createNewCard( Translate.CardType.Water ) );
                deck.add( ElementCard.createNewCard( Translate.CardType.Water ) );
                deck.add( ElementCard.createNewCard( Translate.CardType.Water ) );
                deck.add( ElementCard.createNewCard( Translate.CardType.Fire ) );
                deck.add( ElementCard.createNewCard( Translate.CardType.Air ) );
                deck.add( ElementCard.createNewCard( Translate.CardType.Dirt ) );
            }

            // update liveData
            gameRuntime.updatePlayer(player);
        }

        // game field setup
        GameField gameField = new GameField();
        // set weather, etc.

        // update LiveData
        gameRuntime.updateGameField(gameField);

        Log.i(TAG, "game ready");
    }

    public static Card getCardInHand(Player player, int cardId) throws CardNotFoundException {
        for ( Card c : player.getHand() ) {
            if ( c.getCardId() == cardId ) {
                return c;
            }
        }
        // card not found
        throw new CardNotFoundException();
    }

    private void beforePlayerTurnStart(Player player) throws PlayerCanNotEnterTurnException, PlayerNotFoundException {
        // apply buff first
        player.applyBuff();

        // update LiveData
        gameRuntime.updatePlayer(player);

        if (player.getHP() <= 0) {
            throw new PlayerCanNotEnterTurnException();
        }
        // pass
    }

    public void playerTurnStart(Player player) throws PlayerCanNotEnterTurnException, PlayerNotFoundException {
        Log.i(TAG, "game model: playerTurnStart");
        beforePlayerTurnStart(player);
        // update player's AP
        player.addAP(PLAYER_AP_REGEN_PER_TURN);

        // draw cards from player's deck
        List<ElementCard> deck = player.getDeck();
        Log.i(TAG, String.format("deck size: %d", player.getDeck().size()));
        for (int i = 0; i < PLAYER_CARD_DRAW_PER_TURN && deck.size() > 0; i++) {
//            Log.i(TAG, "drawing card from deck");
            player.addCardToHand(deck.remove(rng.nextInt(deck.size())));
        }

        // update LiveData
        gameRuntime.updatePlayer(player);
    }

    public void playerTurnEnd(Player player) throws PlayerNotFoundException {
        Log.i(TAG, "game model: playerTurnEnd");
        // nothing to do at this moment?
        // update player AP??? isn't the game supposed to carry on AP to the next turn?
        player.setAP(0);

        // update LiveData
        gameRuntime.updatePlayer(player);

        afterPlayerTurnEnd(player);
    }

    private void afterPlayerTurnEnd(Player player) {
        // set next player
        gameRuntime.setNextPlayer();
    }

    public void useCard(Player subject, Player target, Card card) throws PlayerNotFoundException, CardNotFoundException {
        // take card from subject's hand
        subject.removeCardFromHand(card);

        // apply card to target
        card.apply(subject, target);

        // update LiveData
        gameRuntime.updatePlayer(subject);
        gameRuntime.updatePlayer(target);
    }

    public void playerCombineElementsEventHandler(Player player, List<ElementCard> cards)
            throws PlayerNotFoundException, CardNotFoundException, ElementCardsCanNotCombineException {

        // validation
        if (!ElementCard.canCombine(cards.get(0), cards.get(1))) {
            throw new ElementCardsCanNotCombineException(cards);
        }

        // do combination
        // TODO: update the method to support arbitrary number of cards
        // TODO: check player's AP
        Translate.CardType cardType = ElementCard.combine(cards.get(0).getCardType(), cards.get(1).getCardType());
        ElementCard newCard = (ElementCard) Card.createNewCard(cardType);

        if (newCard == null) {
            throw new ElementCardsCanNotCombineException(cards);
        }

        player.addCardToHand(newCard);
        player.removeCardFromHand(cards.get(0));
        player.removeCardFromHand(cards.get(1));

        // update LiveData
        gameRuntime.updatePlayer(player);
    }
}
