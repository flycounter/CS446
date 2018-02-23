package cs446_w2018_group3.supercardgame.util.events;

import java.util.List;

import cs446_w2018_group3.supercardgame.model.Card;
import cs446_w2018_group3.supercardgame.model.Player;

/**
 * Created by JarvieK on 2018/2/23.
 */

public class PlayerEvent {
    public abstract class BaseAction {
        Player subject;
    }
    public class CombineAction extends BaseAction {
        List<Card.ElementCard> cards;
    }
}
