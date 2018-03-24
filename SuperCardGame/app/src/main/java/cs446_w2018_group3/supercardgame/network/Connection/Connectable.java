package cs446_w2018_group3.supercardgame.network.Connection;

/**
 * Created by JarvieK on 2018/3/23.
 */

public interface Connectable {
    void connect(ConnectionListener connectionListener);

    interface ConnectionListener {
        void onConnected();
    }
}
