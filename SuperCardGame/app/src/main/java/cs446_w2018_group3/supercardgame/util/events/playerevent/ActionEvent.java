package cs446_w2018_group3.supercardgame.util.events.playerevent;

/**
 * Created by JarvieK on 2018/3/1.
 */

public abstract class ActionEvent extends PlayerEvent {
    public ActionEvent(EventCode eventCode, int subjectId) {
        super(eventCode, subjectId);
    }
}