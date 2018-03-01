package cs446_w2018_group3.supercardgame.model.field;

import cs446_w2018_group3.supercardgame.model.Player;

/**
 * Created by JarvieK on 2018/2/26.
 */

public class GameField {
    private Weather weather;
    private Player nextPlayer;

    public GameField() {
        // create field with random weather
        weather = new Weather();
    }

    public Weather getWeather() { return weather; }

    public Player getNextPlayer() {
        return nextPlayer;
    }

    public void setNextPlayer(Player nextPlayer) {
        this.nextPlayer = nextPlayer;
    }
}
