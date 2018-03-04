package cs446_w2018_group3.supercardgame.runtime;

import android.util.Log;

import cs446_w2018_group3.supercardgame.Exceptions.PlayerStateException.InvalidStateException;

/**
 * Created by JarvieK on 2018/3/1.
 */

public class GameFSM {
    public enum State {
        IDLE, TURN_START, PLAYER_TURN, TURN_END, PLAYER_TURN_END
    }

    private final State DEFAULT_STATE = State.IDLE;

    private State state;

    public GameFSM() {
        this.state = DEFAULT_STATE;
    }

    public void nextState() throws InvalidStateException {
        Log.i("FSM", String.format("curr state: %s; next state: %s", state, getNextState(state)));
        setState(getNextState(this.state));
    }

    public State getState() {
        return state;
    }

    private void setState(State state) {
        this.state = state;
    }

    private State getNextState(State state) throws InvalidStateException {
        switch (state) {
            case IDLE:
                return State.TURN_START;
            case TURN_START:
                return State.PLAYER_TURN;
            case PLAYER_TURN:
                return State.TURN_END;
            case TURN_END:
                return State.TURN_START;
            default:
                throw new InvalidStateException(state);
        }
    }
}
