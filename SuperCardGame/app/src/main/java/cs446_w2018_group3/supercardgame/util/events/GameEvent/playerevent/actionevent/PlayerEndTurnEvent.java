package cs446_w2018_group3.supercardgame.util.events.GameEvent.playerevent.actionevent;

/**
 * Created by JarvieK on 2018/2/26.
 */

public class PlayerEndTurnEvent extends ActionEvent {
    public PlayerEndTurnEvent(int subjectId) {
        super(EventCode.PLAYER_END_TURN, subjectId);
    }
}
