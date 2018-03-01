package cs446_w2018_group3.supercardgame.util.events.playerevent;

/**
 * Created by JarvieK on 2018/3/1.
 */

public abstract class StateEvent extends PlayerEvent {
    public StateEvent(EventCode eventCode, int subjectId) {
        super(eventCode, subjectId);
    }
}
