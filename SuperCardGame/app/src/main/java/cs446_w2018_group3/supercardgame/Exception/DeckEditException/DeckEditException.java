package cs446_w2018_group3.supercardgame.Exception.DeckEditException;

/**
 * Created by yandong on 2018-03-18.
 */

public abstract class DeckEditException extends Exception {
    DeckEditException() {super(); }
    DeckEditException(String message) {
        super(message);
    }
}
