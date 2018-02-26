package cs446_w2018_group3.supercardgame.viewmodel;

import java.util.List;

/**
 * Created by JarvieK on 2018/2/25.
 */

public interface PlayerAction {
    void combineCards(List<Integer> cardIndices);
    void useElementCard(Integer cardIndex);
    void turnEnd();
}
