package cs446_w2018_group3.supercardgame.runtime;

import cs446_w2018_group3.supercardgame.Exceptions.PlayerStateException.InvalidStateException;

/**
 * Created by JarvieK on 2018/3/1.
 */

public class GameFSM {
    public enum State {
        IDLE, BEFORE_TURN_START, PLAYER_TURN, BEFORE_TURN_END, PLAYER_TURN_END
    }

    private final State DEFAULT_STATE = State.IDLE;

    private State state;

    public void GameState() {
        this.state = DEFAULT_STATE;
    }

    public void nextState() throws InvalidStateException {
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
                return State.BEFORE_TURN_START;
            case BEFORE_TURN_START:
                return State.PLAYER_TURN;
            case PLAYER_TURN:
                return State.BEFORE_TURN_END;
            case BEFORE_TURN_END:
                return State.BEFORE_TURN_START;
            default:
                throw new InvalidStateException(state);
        }
    }
}
