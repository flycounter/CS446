package cs446_w2018_group3.supercardgame.model;

import java.util.ArrayList;
import java.util.Random;

import cs446_w2018_group3.supercardgame.model.cards.*;

/**
 * Created by Yakumo on 2/25/2018.
 */

public class Game {
    private Player player1, player2;
    private String weather;
    private ArrayList<Card> deck1, deck2;
    private Random rng = new Random();

    // Pseudo game constructor. Create a game with a sandbag opponent, and with only water cards in deck. Sunny weather
    public Game() {
        player1 = new Player(1,"Player1");
        player2 = new Player(2,"Sandbag");
        player1.setHP(10);
        player1.setAP(0);
        player2.setHP(5);
        player2.setAP(0);

        weather = "Sunny";

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

    public void startPlayerTurn(Player p) {
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
            Card temp = deck.get(rng.nextInt(deck.size()));
            p.insertCard(temp);
            deck.remove(temp);
        }
        p.applyBuff();
    }

    public void playerUseCard( Player subject, Player object, Card c ) {
        ((WaterCard)c).apply( subject, object );
    }

    public void combineElement( Player p, ElementCard c1, ElementCard c2 ) {
//        p.insertCard( ElementCard.combine( c1, c2 ) );
        p.removeCard( c1 );
        p.removeCard( c2 );
    }

    public void endTurn( Player p ) {
        p.setAP(0);
    }

}
