package cs446_w2018_group3.supercardgame.network.Connection;

import java.util.List;

import cs446_w2018_group3.supercardgame.model.network.ConnInfo;

/**
 * Created by JarvieK on 2018/3/23.
 */

public interface IHost extends Sendable {
    String GAME_JOIN_CONFIRMATION = "GAME_JOIN_CONFIRMATION";

    void start(HostStartedListener hostStartedListener);

    void stop();

    ConnInfo getConnInfo();

    interface HostStartedListener {
        void onStarted(ConnInfo connInfo);
    }

    void broadcast(String message);

    void addConnectionListener(ConnectionListener connectionListener);

    void removeConnectionListener(ConnectionListener connectionListener);

    void setMessageConnector(IMessageConnector messageConnector);
}
