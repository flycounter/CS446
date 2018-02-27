package cs446_w2018_group3.supercardgame.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cs446_w2018_group3.supercardgame.Exceptions.CardNotFoundException;
import cs446_w2018_group3.supercardgame.Exceptions.ElementCardsCanNotCombineException;
import cs446_w2018_group3.supercardgame.Exceptions.PlayerNotFoundException;
import cs446_w2018_group3.supercardgame.Exceptions.PlayerCanNotEnterTurnException;
import cs446_w2018_group3.supercardgame.model.field.GameField;
import cs446_w2018_group3.supercardgame.runtime.GameRuntime;
import cs446_w2018_group3.supercardgame.util.events.payload.CardInfo;
import cs446_w2018_group3.supercardgame.util.events.payload.GameInfo;
import cs446_w2018_group3.supercardgame.model.cards.*;
import cs446_w2018_group3.supercardgame.util.events.playerevent.PlayerCombineElementEvent;
import cs446_w2018_group3.supercardgame.util.events.playerevent.PlayerEndTurnEvent;
import cs446_w2018_group3.supercardgame.util.events.playerevent.PlayerUseCardEvent;

/**
 * Created by Yakumo on 2/25/2018.
 */

public class Game {
    // confugurations
    public static final int PLAYER_MAX_AP = 10;
    public static final int PLAYER_AP_REGEN_PER_TURN = 3;
    public static final int PLAYER_CARD_DRAW_PER_TURN = 3;

    private GameRuntime gameRuntime;
    private Translate.Weather weather;
    private Random rng = new Random();

    // Pseudo game constructor. Create a game with a sandbag opponent, and with only water cards in deck. Sunny weather
    public Game() { }

    public void bindRuntime(GameRuntime runtime) {
        this.gameRuntime = runtime;

        init();
    }

    private void init() {
        Player player1 = new Player(1,"Player1");
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

        // update liveData
        gameRuntime.getMutablePlayer().setValue(player1);
        gameRuntime.getMutableOpponent().setValue(player2);
        gameRuntime.getMutableField().setValue(gameField);
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

    private Card getCardInDeck(Player player, int cardId) throws CardNotFoundException {
        for ( Card c : player.getDeck() ) {
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
    public void gameStart() {
        try {
            beforePlayerTurnStart(gameRuntime.getNextPlayer());
            playerTurnStart(gameRuntime.getNextPlayer());
        }
        catch (PlayerCanNotEnterTurnException e) {
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
        for (int i = 0; i < 3 && deck.size() > 0; i++) {
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
            Card card = getCardInDeck(subject, e.getCardId());
            subject.removeCardFromHand(card);

            // apply card to target
            card.apply(subject, target);

            // update LiveData
            gameRuntime.getMutablePlayer(subject.getId()).setValue(subject);
            gameRuntime.getMutablePlayer(target.getId()).setValue(target);

        } catch (PlayerNotFoundException | CardNotFoundException err) {
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
                cards.add((ElementCard) getCardInDeck(player, cardId));
            }

            // validation
            if (!ElementCard.canCombine(cards.get(0), cards.get(1))) {
                throw new ElementCardsCanNotCombineException(cards);
            }

            // do combination
            // TODO: update the method to support arbitrary number of cards
            Translate.CardType cardType = ElementCard.combine(cards.get(0).getCardType(), cards.get(1).getCardType());
            ElementCard newCard = new AquaCard();

            player.addCardToHand(newCard);
            player.removeCardFromHand(cards.get(0));
            player.removeCardFromHand(cards.get(1));
        }
        catch ( PlayerNotFoundException | CardNotFoundException | ElementCardsCanNotCombineException err ) {
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

        } catch (PlayerNotFoundException e) {
            // ...
        }
    }

    public GameInfo getGameInfo() {
        ArrayList<CardInfo> deck1Info = new ArrayList<CardInfo>();
        ArrayList<CardInfo> deck2Info = new ArrayList<CardInfo>();
        for ( Card c : deck1 ) {
            deck1Info.add( c.getCardInfo() );
        }
        for ( Card c: deck2 ) {
            deck2Info.add( c.getCardInfo() );
        }
        return new GameInfo( player1.getPlayerInfo(), player2.getPlayerInfo(), weather, deck1Info, deck2Info );
    }
}
