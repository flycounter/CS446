package cs446_w2018_group3.supercardgame.Exception.PlayerStateException;

/**
 * Created by JarvieK on 2018/3/1.
 */

public abstract class GameStateException extends Exception {
    GameStateException() {super(); }
    GameStateException(String message) { super(message); }
}
