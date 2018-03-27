package cs446_w2018_group3.supercardgame.model.field;

import cs446_w2018_group3.supercardgame.model.field.Weather.RainyWeather;
import cs446_w2018_group3.supercardgame.model.field.Weather.Weather;

/**
 * Created by JarvieK on 2018/2/26.
 */

public class GameField {
    private Weather weather;

    public GameField() {
        // create field with random weather
        weather = new RainyWeather();
    }

    public Weather getWeather() { return weather; }
}
