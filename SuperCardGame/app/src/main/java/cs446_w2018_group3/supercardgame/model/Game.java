package cs446_w2018_group3.supercardgame.model;

import java.util.ArrayList;
import java.util.Random;

import cs446_w2018_group3.supercardgame.info.CardInfo;
import cs446_w2018_group3.supercardgame.info.GameInfo;
import cs446_w2018_group3.supercardgame.info.PlayerInfo;
import cs446_w2018_group3.supercardgame.model.cards.*;

/**
 * Created by Yakumo on 2/25/2018.
 */

public class Game {
    private Player player1, player2;
    private Translate.Weather weather;
    private ArrayList<Card> deck1, deck2;
    private Random rng = new Random();

    private class InvalidPlayerException extends Exception {}
    private class CardNotFoundException extends  Exception {}
    public class InvalidCombineElementException extends  Exception {
        public Card c1,c2;
        InvalidCombineElementException (Card c1, Card c2) {
            this.c1 = c1;
            this.c2 = c2;
        }
    }

    private Player getPlayerByInfo ( PlayerInfo playerInfo ) throws InvalidPlayerException {
        if ( playerInfo.playerId == player1.getId() ) {
            return player1;
        } else if ( playerInfo.playerId == player2.getId() ) {
            return player2;
        } else throw new InvalidPlayerException();
    }
    private Card getCardInDeck ( CardInfo cardInfo, ArrayList<Card> deck ) throws CardNotFoundException {
        for ( Card c : deck ) {
            if ( c.getCardId() == cardInfo.cardId ) {
                return c;
            }
        }
        throw new CardNotFoundException();
    }

    // Pseudo game constructor. Create a game with a sandbag opponent, and with only water cards in deck. Sunny weather
    public Game() {
        player1 = new Player(1,"Player1");
        player2 = new Player(2,"Sandbag");
        player1.setHP(10);
        player1.setAP(0);
        player2.setHP(5);
        player2.setAP(0);

        weather = Translate.Weather.Sunny;

        for( int i = 0; i < 15; i++ ) {
            deck1.add( new WaterCard() );
            deck2.add( new WaterCard() );
        }
    }

    /*
    * Players start with empty hands and 0 AP. gameStart() places 3 cards from each player's deck into their hand
    * Random weather to be added.
    * */
    public void gameStart() {
        for( int i = 0; i < 3; i += 1 ) {
            Card temp = deck1.get( rng.nextInt( deck1.size() ) );
            player1.insertCard( temp );
            deck1.remove( temp );
            temp = deck2.get( rng.nextInt( deck1.size() ) );
            player2.insertCard( temp );
            deck2.remove( temp );
        }
    }

    public void startPlayerTurn(PlayerInfo pInfo) {
        Player p;
        try {
            p = getPlayerByInfo( pInfo );
        } catch ( InvalidPlayerException e ) {
            return;
        }
        ArrayList<Card> deck;
        if ( p == player1 ) {
            deck = deck1;
        } else if ( p == player2 ) {
            deck = deck2;
        } else {
            return;
        }
        p.setAP( p.getAP() + 6 );
        for( int i = 0; i < 2; i += 1 ) {
            Card temp = deck.get( rng.nextInt( deck.size() ) );
            p.insertCard(temp);
            deck.remove(temp);
        }
        p.applyBuff();
    }

    public void playerUseCard( PlayerInfo subjectInfo, PlayerInfo objectInfo, CardInfo cardInfo ) {
        Player subject,object;
        Card c;
        try {
            subject = getPlayerByInfo( subjectInfo );
            object = getPlayerByInfo( objectInfo );
            ArrayList<Card> deck;
            if ( subject == player1 ) {
                deck = deck1;
            } else {
                deck = deck2;
            }
            c = getCardInDeck( cardInfo,deck );
        } catch ( InvalidPlayerException|CardNotFoundException e ) {
            return;
        }
        (c).apply( subject, object );
    }

    public void combineElement( PlayerInfo pInfo, CardInfo c1Info, CardInfo c2Info ) {
        Player p;
        ElementCard c1,c2;
        try {
            p = getPlayerByInfo( pInfo );
            ArrayList<Card> deck;
            if ( p == player1 ) {
                deck = deck1;
            } else {
                deck = deck2;
            }
            c1 = (ElementCard) getCardInDeck( c1Info, deck );
            c2 = (ElementCard) getCardInDeck( c2Info, deck );
            if ( ElementCard.combineCheck( c1, c2 ) ) {
                Translate.CardType newCardType = ElementCard.combine( c1.getCardType(), c2.getCardType() );

                // This is temporary
                Card newCard = new AquaCard();

                p.insertCard( newCard );
                p.removeCard( c1 );
                p.removeCard( c2 );
            } else throw new InvalidCombineElementException(c1, c2);
        } catch ( InvalidCombineElementException e ) {
        } catch ( InvalidPlayerException|CardNotFoundException e ) {
        }
    }

    public void endTurn( PlayerInfo pInfo ) {
        Player p;
        try {
            p = getPlayerByInfo( pInfo );
        } catch ( InvalidPlayerException e ) {
            return;
        }
        p.setAP(0);
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
