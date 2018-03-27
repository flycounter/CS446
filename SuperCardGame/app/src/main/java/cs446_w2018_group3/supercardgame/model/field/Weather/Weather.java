package cs446_w2018_group3.supercardgame.model.field.Weather;

import cs446_w2018_group3.supercardgame.model.player.Player;

/**
 * Created by JarvieK on 2018/2/26.
 */

public class Weather {
    String label;

    public Weather() { label = "default_weather"; }

    public String getLabel() { return label; }

    public void apply(Player player) {
        // Default weather does nothing to a player
    }
}
