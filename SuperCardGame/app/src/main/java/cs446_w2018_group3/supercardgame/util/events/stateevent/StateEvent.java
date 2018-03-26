package cs446_w2018_group3.supercardgame.util.events.stateevent;

import cs446_w2018_group3.supercardgame.util.events.GameEvent;

/**
 * Created by JarvieK on 2018/3/1.
 */

public abstract class StateEvent extends GameEvent {
    private int subjectId;

    public StateEvent(EventCode eventCode, int subjectId) {
        super(eventCode);
        this.subjectId = subjectId;
    }

    public int getSubjectId() {
        return subjectId;
    }
}
