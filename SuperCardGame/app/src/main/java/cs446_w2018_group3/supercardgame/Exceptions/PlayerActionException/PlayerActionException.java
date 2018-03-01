package cs446_w2018_group3.supercardgame.Exceptions.PlayerActionException;

/**
 * Created by JarvieK on 2018/3/1.
 */

public abstract class PlayerActionException extends Exception {
    PlayerActionException() {super(); }
    PlayerActionException(String message) {
        super(message);
    }
}
