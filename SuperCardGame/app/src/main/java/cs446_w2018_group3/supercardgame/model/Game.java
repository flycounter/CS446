package cs446_w2018_group3.supercardgame.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cs446_w2018_group3.supercardgame.Exceptions.CardNotFoundException;
import cs446_w2018_group3.supercardgame.Exceptions.ElementCardsCanNotCombineException;
import cs446_w2018_group3.supercardgame.Exceptions.PlayerNotFoundException;
import cs446_w2018_group3.supercardgame.Exceptions.PlayerCanNotEnterTurnException;
import cs446_w2018_group3.supercardgame.model.field.GameField;
import cs446_w2018_group3.supercardgame.runtime.GameRuntime;
import cs446_w2018_group3.supercardgame.model.cards.*;
import cs446_w2018_group3.supercardgame.util.events.playerevent.PlayerCombineElementEvent;
import cs446_w2018_group3.supercardgame.util.events.playerevent.PlayerEndTurnEvent;
import cs446_w2018_group3.supercardgame.util.events.playerevent.PlayerUseCardEvent;

/**
 * Created by Yakumo on 2/25/2018.
 */

public class Game {
    // configurations
    public static final int PLAYER_MAX_AP = 10;
    public static final int PLAYER_AP_REGEN_PER_TURN = 3;
    public static final int PLAYER_CARD_DRAW_PER_TURN = 3;

    private GameRuntime gameRuntime;
    private Random rng = new Random();

    // Pseudo game constructor. Create a game with a sandbag opponent, and with only water cards in deck. Sunny weather
    public Game() { }

    public void bindRuntime(GameRuntime runtime) {
        this.gameRuntime = runtime;
        init();
        Log.i("Game", "calling gameStart()");
        gameStart();
    }

    private void init() {
        Player player1 = new Player(1,"You");
        Player player2 = new Player(2,"Sandbag");
        player1.setHP(10);
        player1.setAP(0);
        player2.setHP(5);
        player2.setAP(0);

        GameField gameField = new GameField();

        List<Card> deck; // temp variable

        deck = new ArrayList<>();
        for( int i = 0; i < 15; i++ ) {
            deck.add( new WaterCard() );
        }
        player1.setDeck(deck);

        deck = new ArrayList<>();
        for( int i = 0; i < 15; i++ ) {
            deck.add( new FireCard() );
        }
        player2.setDeck(deck);

        gameRuntime.setNextPlayer(player1);

        // update liveData
        gameRuntime.getMutablePlayer().setValue(player1);
        gameRuntime.getMutableOpponent().setValue(player2);
        gameRuntime.getMutableField().setValue(gameField);
        Log.i("Game", "game init done");
    }

    private Player getPlayer(int playerId) throws PlayerNotFoundException {
        Player currPlayer;

        currPlayer = gameRuntime.getPlayer().getValue();
        if ( playerId == currPlayer.getId() ) {
            return currPlayer;
        }

        currPlayer = gameRuntime.getOpponent().getValue();
        if ( playerId == currPlayer.getId() ) {
            return currPlayer;
        }

        // player not found
        throw new PlayerNotFoundException();
    }

    private Card getCardInHand(Player player, int cardId) throws CardNotFoundException {
        for ( Card c : player.getHand() ) {
            if ( c.getCardId() == cardId ) {
                return c;
            }
        }
        // card not found
        throw new CardNotFoundException();
    }

    /*
    * Players start with empty hands and 0 AP. gameStart() places 3 cards from each player's deck into their hand
    * Random weather to be added.
    * */
    private void gameStart() {
        Log.i("game", "game start");
        try {
            Log.i("game", "before player turn start");
            beforePlayerTurnStart(gameRuntime.getNextPlayer());
            Log.i("game", "player turn start");
            playerTurnStart(gameRuntime.getNextPlayer());
        }
        catch (PlayerCanNotEnterTurnException err) {
            Log.i("Game", err.toString());
            // ...
        }
    }

    private void beforePlayerTurnStart(Player player) throws PlayerCanNotEnterTurnException {
        // check if next player can enter the turn
        if (player == null) {
            throw new PlayerCanNotEnterTurnException();
        }

        // apply buff first
        player.applyBuff();

        // update LiveData
        gameRuntime.getMutablePlayer(player.getId()).setValue(player);

        if (player.getHP() <= 0) {
            throw new PlayerCanNotEnterTurnException();
        }

        // pass
    }

    private void playerTurnStart(Player player) {
        // update player's AP
        player.setAP(Math.min(PLAYER_MAX_AP, PLAYER_AP_REGEN_PER_TURN + player.getAP()));

        // draw cards from player's deck
        List<Card> deck = player.getDeck();
        Log.i("Game", String.format("deck size: %d", player.getDeck().size()));
        for (int i = 0; i < PLAYER_CARD_DRAW_PER_TURN && deck.size() > 0; i++) {
            Log.i("Game", "drawing card from deck");
            player.addCardToHand(deck.remove(0));
        }

        // update LiveData
        gameRuntime.getMutablePlayer(player.getId()).setValue(player);
    }

    private void PlayerTurnEnd(Player player) {
        // nothing to do at this moment?
    }

    private void AfterPlayerTurnEnd(Player player) {
        // set next player
        gameRuntime.changePlayer();
    }

    public void playerUseCardEventHandler(PlayerUseCardEvent e) {
        try {
            Player subject = getPlayer(e.getSubjectId());
            Player target = getPlayer(e.getTargetId());

            // take card from subject's hand
            Card card = getCardInHand(subject, e.getCardId());
            subject.removeCardFromHand(card);

            // apply card to target
            card.apply(subject, target);

            // update LiveData
            gameRuntime.getMutablePlayer(subject.getId()).setValue(subject);
            gameRuntime.getMutablePlayer(target.getId()).setValue(target);

        } catch (PlayerNotFoundException | CardNotFoundException err) {
            Log.i("Game", err.toString());
            // ...
        }
    }

    public void playerCombineElementsEventHandler(PlayerCombineElementEvent e) {
        try {
            Player player = getPlayer(e.getSubjectId());

            // get cards to be combined
            // NOTE: maybe replace with lambda map?
            List<ElementCard> cards = new ArrayList<>();

            for (int cardId: e.getCardIds()) {
                cards.add((ElementCard) getCardInHand(player, cardId));
            }

            Log.i("Game", String.format("cards found in hand: %s", cards.toString()));

            // validation
            if (!ElementCard.canCombine(cards.get(0), cards.get(1))) {
                throw new ElementCardsCanNotCombineException(cards);
            }

            // do combination
            // TODO: update the method to support arbitrary number of cards
//            Translate.CardType cardType = ElementCard.combine(cards.get(0).getCardType(), cards.get(1).getCardType());
            ElementCard newCard = new FireCard();

            player.addCardToHand(newCard);
            player.removeCardFromHand(cards.get(0));
            player.removeCardFromHand(cards.get(1));

            // update LiveData
            gameRuntime.getMutablePlayer(player.getId()).setValue(player);
        }
        catch ( PlayerNotFoundException | CardNotFoundException | ElementCardsCanNotCombineException err ) {
            Log.i("Game", err.toString());
            // ...
        }
    }

    public void playerEndTurnEventHandler(PlayerEndTurnEvent e) {
        try {
            Player player = getPlayer(e.getSubjectId());

            // update player AP???
            player.setAP(0);

            // update LiveData
            gameRuntime.getMutablePlayer(player.getId()).setValue(player);

        } catch (PlayerNotFoundException err) {
            Log.i("Game", err.toString());
            // ...
        }
    }
}
