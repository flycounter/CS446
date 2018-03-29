package cs446_w2018_group3.supercardgame.model.cards;

/**
 * Created by JarvieK on 2018/3/29.
 */

public class Counter {
    private static int index = 0;

    public static int getNextIndex() {
        return ++index;
    }
}
