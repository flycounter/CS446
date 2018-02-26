package cs446_w2018_group3.supercardgame.info;

import java.util.ArrayList;

import cs446_w2018_group3.supercardgame.model.Translate;
import cs446_w2018_group3.supercardgame.model.cards.Card;

/**
 * Created by yandong on 2018-02-25.
 */

public class GameInfo {
    public PlayerInfo player1, player2;
    public Translate.Weather weather;
    public ArrayList<CardInfo> deck1, deck2;

    public GameInfo(PlayerInfo player1, PlayerInfo player2, Translate.Weather weather, ArrayList<CardInfo> deck1, ArrayList<CardInfo> deck2) {
        this.player1 = player1;
        this.player2 = player2;
        this.weather = weather;
        this.deck1 = deck1;
        this.deck2 = deck2;
    }
}
