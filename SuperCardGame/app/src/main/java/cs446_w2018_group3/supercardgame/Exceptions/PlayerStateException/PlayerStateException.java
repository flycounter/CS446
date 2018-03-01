package cs446_w2018_group3.supercardgame.Exceptions.PlayerStateException;

/**
 * Created by JarvieK on 2018/3/1.
 */

public abstract class PlayerStateException extends Exception {
    PlayerStateException() {super(); }
    PlayerStateException(String message) { super(message); }
}
