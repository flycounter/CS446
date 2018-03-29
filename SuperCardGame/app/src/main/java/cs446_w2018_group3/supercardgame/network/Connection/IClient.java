package cs446_w2018_group3.supercardgame.network.Connection;

import cs446_w2018_group3.supercardgame.model.network.ConnInfo;
import cs446_w2018_group3.supercardgame.util.listeners.cb;

/**
 * Created by JarvieK on 2018/3/23.
 */

public interface IClient extends Sendable {
    void connect();

    ConnInfo getConnInfo();

    void addConnectionListener(ConnectionListener connectionListener);

    void removeConnectionListener(ConnectionListener connectionListener);

    void sendMessage(String message);

    void setMessageConnector(IMessageConnector messageConnector);

    void setConfirmationReceivedCallback(cb cb);
}
