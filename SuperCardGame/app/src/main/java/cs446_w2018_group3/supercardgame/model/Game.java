package cs446_w2018_group3.supercardgame.model;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cs446_w2018_group3.supercardgame.Exception.PlayerActionException.CardNotFoundException;
import cs446_w2018_group3.supercardgame.Exception.PlayerActionException.ElementCardsCanNotCombineException;
import cs446_w2018_group3.supercardgame.Exception.PlayerActionException.PlayerInsufficientApException;
import cs446_w2018_group3.supercardgame.Exception.PlayerActionException.PlayerNotFoundException;
import cs446_w2018_group3.supercardgame.Exception.PlayerActionException.PlayerCanNotEnterTurnException;
import cs446_w2018_group3.supercardgame.model.cards.element.ElementCard;
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
    public static final int PLAYER_COMBINE_ELEMENT_COST = 1;

    private GameRuntime gameRuntime;
    private Random rng = new Random(System.currentTimeMillis()); // seed = curr unix time

    // Pseudo game constructor. Create a game with a sandbag opponent, and with only water cards in deck. Sunny weather
    public Game() {
    }

    public void bind(GameRuntime gameRuntime) {
        this.gameRuntime = gameRuntime;
    }

    public void init() throws PlayerNotFoundException {
        // for each player
        for (LiveData<Player> playerHolder : gameRuntime.getPlayers()) {
            Player player = playerHolder.getValue();
            player.setHP(10);
            player.setAP(0);

            // update liveData
            gameRuntime.updatePlayer(player);
        }

        // game field setup
        GameField gameField = new GameField();
        // set weather, etc.

        // update LiveData
        gameRuntime.updateGameField(gameField);
        String msg = "Game Started";
        gameRuntime.updateLogInfo(msg);
        Log.i(TAG, "game ready");
    }

    public static Card getCardInHand(Player player, int cardId) throws CardNotFoundException {
        for (Card c : player.getHand()) {
            if (c.getId() == cardId) {
                return c;
            }
        }
        // card not found
        throw new CardNotFoundException();
    }

    private void beforePlayerTurnStart(Player player) throws PlayerCanNotEnterTurnException, PlayerNotFoundException {
        // apply buff first
        player.applyBuff();
        gameRuntime.getGameField().getValue().getWeather().apply(player);
        // update LiveData
        gameRuntime.updatePlayer(player);

        if (player.getHP() <= 0) {
            throw new PlayerCanNotEnterTurnException();
        }
        // pass
    }

    public void playerTurnStart(Player player) throws PlayerCanNotEnterTurnException, PlayerNotFoundException {
        String msg;
        msg = player.getName() + "'s turn starts";
        gameRuntime.updateLogInfo(msg);
        Log.i(TAG, "game model: playerTurnStart");
        beforePlayerTurnStart(player);
        msg = player.getName() + " regain AP";
        gameRuntime.updateLogInfo(msg);
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
        String msg = player.getName() + "'s turn ends";
        gameRuntime.updateLogInfo(msg);
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

    public void useCard(Player subject, Player target, Card card) throws PlayerInsufficientApException, PlayerNotFoundException, CardNotFoundException {
        // check whether subject player has enough AP
        if (subject.getAP() < card.getCost()) {
            throw new PlayerInsufficientApException();
        }
        subject.setAP(subject.getAP() - card.getCost());

        // take card from subject's hand
        subject.removeCardFromHand(card);

        // apply card to target
        card.apply(subject, target);
        String msg = subject.getName() + " use a " + card.getCardType().toString() + " card to " + target.getName();

        // update LiveData
        gameRuntime.updatePlayer(subject);
        gameRuntime.updatePlayer(target);
        gameRuntime.updateLogInfo(msg);
    }

    public void combineCard(Player player, List<ElementCard> cards)
            throws PlayerInsufficientApException, PlayerNotFoundException, CardNotFoundException, ElementCardsCanNotCombineException {

        // validation
        if (!ElementCard.canCombine(cards.get(0), cards.get(1))) {
            throw new ElementCardsCanNotCombineException(cards);
        }
        if (player.getAP() < PLAYER_COMBINE_ELEMENT_COST) {
            throw new PlayerInsufficientApException();
        }

        // do combination
        // TODO: update the method to support arbitrary number of cards
        Translate.CardType cardType = ElementCard.combine(cards.get(0).getCardType(), cards.get(1).getCardType());
        ElementCard newCard = (ElementCard) Card.createNewCard(cardType);

        if (newCard == null) {
            throw new ElementCardsCanNotCombineException(cards);
        }

        Log.i(TAG, String.format("card combination: %s + %s -> %s", cards.get(0).getCardType(), cards.get(1).getCardType(), cardType));

        player.setAP(player.getAP() - PLAYER_COMBINE_ELEMENT_COST);
        String msg = player.getName() + " combine ";
        msg = msg + cards.get(0).getCardType().toString() + " card and " + cards.get(1).getCardType().toString();
        msg = msg + " card get a " + newCard.getCardType().toString() + " card";
        player.removeCardFromHand(cards.get(0));
        player.removeCardFromHand(cards.get(1));
        player.addCardToHand(newCard);

        // debug log
        String cardIdsInHand = "";
        for (Card card: player.getHand()) {
            cardIdsInHand += String.format("[%s: %s], ", card.getCardType(), card.getId());
        }
        Log.i(TAG, "cards in hand: " + cardIdsInHand);

        // update LiveData
        gameRuntime.updatePlayer(player);
        gameRuntime.updateLogInfo(msg);
    }
}
