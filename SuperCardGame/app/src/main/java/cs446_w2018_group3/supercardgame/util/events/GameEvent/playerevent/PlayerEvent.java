package cs446_w2018_group3.supercardgame.util.events.GameEvent.playerevent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.GameEvent;

/**
 * Created by JarvieK on 2018/2/23.
 */

public abstract class PlayerEvent extends GameEvent {
    private final int subjectId; // reference to Player model

    public PlayerEvent(EventCode eventCode, int subjectId) {
        super(eventCode);
        this.subjectId = subjectId;
    }

    public int getSubjectId() { return subjectId; }
}
