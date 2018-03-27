package cs446_w2018_group3.supercardgame.model.field.Weather;

import cs446_w2018_group3.supercardgame.model.Translate;
import cs446_w2018_group3.supercardgame.model.buffs.WetBuff;
import cs446_w2018_group3.supercardgame.model.player.Player;

/**
 * Created by Yakumo on 3/27/2018.
 */

public class RainyWeather extends Weather {
    private static final int RAINY_WET_DURATION = 1;

    public RainyWeather() {
        this.label = "Rainy";
    }

    @Override
    public void apply(Player player) {
        // Applies wet buff to a player
        player.addBuff(new WetBuff(RAINY_WET_DURATION));
    }
}
