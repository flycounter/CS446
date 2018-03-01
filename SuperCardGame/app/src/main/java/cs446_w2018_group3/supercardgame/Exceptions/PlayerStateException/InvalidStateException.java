package cs446_w2018_group3.supercardgame.Exceptions.PlayerStateException;

import cs446_w2018_group3.supercardgame.runtime.GameFSM;

/**
 * Created by JarvieK on 2018/3/1.
 */

public class InvalidStateException extends PlayerStateException {
    private final GameFSM.State state;

    public GameFSM.State getState() {
        return state;
    }

    public InvalidStateException(GameFSM.State state) {
        super(String.format("invalid state: %s", state));
        this.state = state;
    }
}
