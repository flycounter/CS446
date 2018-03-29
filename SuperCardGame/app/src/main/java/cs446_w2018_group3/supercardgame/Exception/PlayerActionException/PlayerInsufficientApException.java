package cs446_w2018_group3.supercardgame.Exception.PlayerActionException;

import cs446_w2018_group3.supercardgame.model.player.Player;

/**
 * Created by Yakumo on 3/27/2018.
 */

public class PlayerInsufficientApException extends PlayerActionException {
    private String message;
    public PlayerInsufficientApException(String msg) {
        message = msg;
    }
    public PlayerInsufficientApException() {
        message = "Insufficient AP";
    }
    @Override
    public String getMessage() {
        return message;
    }
}
