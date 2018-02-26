package cs446_w2018_group3.supercardgame.model;

import java.util.ArrayList;
import cs446_w2018_group3.supercardgame.model.cards.*;

/**
 * Created by Yakumo on 2/25/2018.
 */

public class Game {
    private Player player1, player2;
    private String weather;
    private ArrayList<Card> deck1, deck2;

    public Game() {
        player1 = new Player(1,"Player1");
        player2 = new Player(2,"Sandbag");
        player1.setHP(10);
        player1.setAP(6);
        player2.setHP(5);
        player2.setAP(6);

        weather = "Sunny";

        for( int i = 0; i <15; i++ ) {
        }
    }
}
