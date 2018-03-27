package cs446_w2018_group3.supercardgame.model.field;

import cs446_w2018_group3.supercardgame.model.player.Player;

/**
 * Created by JarvieK on 2018/2/26.
 */

public class GameField {
    private Weather weather;

    public GameField() {
        // create field with random weather
        weather = new Weather();
    }

    public Weather getWeather() { return weather; }
}
