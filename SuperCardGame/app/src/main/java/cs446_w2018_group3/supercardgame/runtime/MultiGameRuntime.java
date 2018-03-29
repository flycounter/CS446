package cs446_w2018_group3.supercardgame.runtime;

import cs446_w2018_group3.supercardgame.Exception.PlayerActionException.PlayerActionNotAllowed;
import cs446_w2018_group3.supercardgame.Exception.PlayerActionException.PlayerCanNotEnterTurnException;
import cs446_w2018_group3.supercardgame.Exception.PlayerStateException.InvalidStateException;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.playerevent.PlayerEvent;

/**
 * Created by JarvieK on 2018/3/26.
 */

public class MultiGameRuntime extends GameRuntime {
    // NOTE: MultiGameRuntime is used by client, only for updating ui elmenets on sync data

    private final boolean isHost;

    public MultiGameRuntime(boolean isHost) {
        this.isHost = isHost;
    }

    @Override
    public void start() {
        if (isHost) {
            super.start();
        }
    }

    @Override
    public void turnStart() throws PlayerCanNotEnterTurnException {
        if (isHost) {
         super.turnStart();
        }
    }

    @Override
    public void turnEnd() {
        if (isHost) {
            super.turnEnd();
        }
    }

    @Override
    void checkPlayerEventState(PlayerEvent e) throws PlayerActionNotAllowed, InvalidStateException {
        if (isHost) {
            super.checkPlayerEventState(e);
        }
    }
}
