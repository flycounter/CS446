package cs446_w2018_group3.supercardgame.model.bot;

import cs446_w2018_group3.supercardgame.util.events.playerevent.TurnStartEvent;

/**
 * Created by JarvieK on 2018/3/3.
 */

public interface IBot {
    public void onTurnStart(TurnStartEvent e);
}
