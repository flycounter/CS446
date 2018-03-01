package cs446_w2018_group3.supercardgame.model;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.List;
import java.util.Random;

import cs446_w2018_group3.supercardgame.Exceptions.PlayerActionException.CardNotFoundException;
import cs446_w2018_group3.supercardgame.Exceptions.PlayerActionException.ElementCardsCanNotCombineException;
import cs446_w2018_group3.supercardgame.Exceptions.PlayerActionException.PlayerNotFoundException;
import cs446_w2018_group3.supercardgame.Exceptions.PlayerActionException.PlayerCanNotEnterTurnException;
import cs446_w2018_group3.supercardgame.model.field.GameField;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.runtime.GameController;
import cs446_w2018_group3.supercardgame.model.cards.*;

/**
 * Created by Yakumo on 2/25/2018.
 */

public class Game {
    // configurations
    public static final int PLAYER_MAX_AP = 10;
    public static final int PLAYER_AP_REGEN_PER_TURN = 3;
    public static final int PLAYER_CARD_DRAW_PER_TURN = 5;

    private GameController gameController;
    private Random rng = new Random(System.currentTimeMillis()); // seed = curr unix time

    // Pseudo game constructor. Create a game with a sandbag opponent, and with only water cards in deck. Sunny weather
    public Game() { }

    public void bind(GameController gameController) {
        this.gameController = gameController;
    }

    public void init(Player nextPlayer) throws PlayerNotFoundException {
        // for each player
        for (LiveData<Player> playerHolder: gameController.getPlayers()) {
            Player player = playerHolder.getValue();
            player.setHP(10);
            player.setAP(0);
            List<Card> deck = player.getDeck();
            for( int i = 0; i < 6; i++ ) {
                deck.add( Card.createNewCard( Translate.CardType.Water ) );
                deck.add( Card.createNewCard( Translate.CardType.Water ) );
                deck.add( Card.createNewCard( Translate.CardType.Water ) );
                deck.add( Card.createNewCard( Translate.CardType.Fire ) );
                deck.add( Card.createNewCard( Translate.CardType.Air ) );
                deck.add( Card.createNewCard( Translate.CardType.Dirt ) );
            }

            // update liveData
            gameController.updatePlayer(player);
        }

        // game field setup
        GameField gameField = new GameField();
        // set weather, etc.

        // update LiveData
        gameController.updateGameField(gameField);

        // next player
        gameController.setNextPlayer(nextPlayer);

        Log.i("Game", "game start done");
    }
//
//    private Player getPlayer(int playerId) throws PlayerNotFoundException {
//        Player result = gameController.getPlayer(playerId).getValue();
//        if (result == null) {
//            throw new PlayerNotFoundException();
//        }
//
//        return result;
//    }

    public static Card getCardInHand(Player player, int cardId) throws CardNotFoundException {
        for ( Card c : player.getHand() ) {
            if ( c.getCardId() == cardId ) {
                return c;
            }
        }
        // card not found
        throw new CardNotFoundException();
    }

    public void beforePlayerTurnStart(Player player) throws PlayerCanNotEnterTurnException, PlayerNotFoundException {
        // apply buff first
        player.applyBuff();

        // update LiveData
        gameController.updatePlayer(player);

        if (player.getHP() <= 0) {
            throw new PlayerCanNotEnterTurnException();
        }

        // pass
    }

    public void playerTurnStart(Player player) throws PlayerNotFoundException {
        // update player's AP
        player.addAP(PLAYER_AP_REGEN_PER_TURN);

        // draw cards from player's deck
        List<Card> deck = player.getDeck();
        Log.i("Game", String.format("deck size: %d", player.getDeck().size()));
        for (int i = 0; i < PLAYER_CARD_DRAW_PER_TURN && deck.size() > 0; i++) {
//            Log.i("Game", "drawing card from deck");
            player.addCardToHand(deck.remove(rng.nextInt(deck.size())));
        }

        // update LiveData
        gameController.updatePlayer(player);
    }

    public void playerTurnEnd(Player player) throws PlayerNotFoundException {
        // nothing to do at this moment?
        // update player AP??? isn't the game supposed to carry on AP to the next turn?
        player.setAP(0);

        // update LiveData
        gameController.updatePlayer(player);
    }

    public void afterPlayerTurnEnd(Player player) {
        // set next player
        gameController.setNextPlayer();
    }

    public void useCard(Player subject, Player target, Card card) throws PlayerNotFoundException, CardNotFoundException {
        // take card from subject's hand
        subject.removeCardFromHand(card);

        // apply card to target
        card.apply(subject, target);

        // update LiveData
        gameController.updatePlayer(subject);
        gameController.updatePlayer(target);
    }

    public void playerCombineElementsEventHandler(Player player, List<ElementCard> cards)
            throws PlayerNotFoundException, CardNotFoundException, ElementCardsCanNotCombineException {

        // validation
        if (!ElementCard.canCombine(cards.get(0), cards.get(1))) {
            throw new ElementCardsCanNotCombineException(cards);
        }

        // do combination
        // TODO: update the method to support arbitrary number of cards
        Translate.CardType cardType = ElementCard.combine(cards.get(0).getCardType(), cards.get(1).getCardType());
        ElementCard newCard = (ElementCard) Card.createNewCard(cardType);

        if (newCard == null) {
            throw new ElementCardsCanNotCombineException(cards);
        }

        player.addCardToHand(newCard);
        player.removeCardFromHand(cards.get(0));
        player.removeCardFromHand(cards.get(1));

        // update LiveData
        gameController.updatePlayer(player);
    }
}
