package cs446_w2018_group3.supercardgame.util.events.playerevent;
import cs446_w2018_group3.supercardgame.util.events.payload.PlayerInfo;
import cs446_w2018_group3.supercardgame.util.events.GameEvent;

/**
 * Created by JarvieK on 2018/2/23.
 */

public abstract class BasePlayerEvent extends GameEvent {
    private final int subjectId; // reference to Player model

    public BasePlayerEvent(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getSubjectId() { return subjectId; }
}
