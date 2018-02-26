package cs446_w2018_group3.supercardgame.model.field;

import cs446_w2018_group3.supercardgame.model.BaseModel;
import cs446_w2018_group3.supercardgame.model.TurnRelated;

/**
 * Created by JarvieK on 2018/2/26.
 */

public class Weather extends BaseModel implements TurnRelated {
    private int turnsLeft;

    public Weather() {
        super();
        // return random weather
        turnsLeft = 3; // test value
    }

    public Weather(Weather weather) {
        id = weather.getId();
        label = weather.getLabel();
    }


    @Override
    public void nextTurn() {
        turnsLeft--;
    }

    @Override
    public boolean isApplicable() {
        return turnsLeft > 0;
    }
}
