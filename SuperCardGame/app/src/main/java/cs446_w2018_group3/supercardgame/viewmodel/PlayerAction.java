package cs446_w2018_group3.supercardgame.viewmodel;

import java.util.List;

import cs446_w2018_group3.supercardgame.util.events.playerevent.TurnStartEvent;

/**
 * Created by JarvieK on 2018/2/25.
 */

public interface PlayerAction {
    void combineCards(List<Integer> cardIds);
    void useElementCard(int targetId, int cardId);
    void turnEnd();
    void onTurnStart(TurnStartEvent e);
}
