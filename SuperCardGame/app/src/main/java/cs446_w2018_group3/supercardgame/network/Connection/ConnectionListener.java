package cs446_w2018_group3.supercardgame.network.Connection;

/**
 * Created by JarvieK on 2018/3/25.
 */

public interface ConnectionListener {
    void onMessage(String message);

    void onConnected();

    void onDisconnected();
}
