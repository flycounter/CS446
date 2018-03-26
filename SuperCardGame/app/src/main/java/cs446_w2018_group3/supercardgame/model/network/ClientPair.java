package cs446_w2018_group3.supercardgame.model.network;

import cs446_w2018_group3.supercardgame.network.Connection.IClient;

/**
 * Created by JarvieK on 2018/3/25.
 */

public class ClientPair {
    private final IClient mClient;
    private final INetworkConnector mNetworkConnector;
    private boolean isReady;

    public ClientPair(IClient client, INetworkConnector networkConnector) {
        mClient = client;
        mNetworkConnector = networkConnector;
        isReady = false;
    }

    public IClient getClient() {
        return mClient;
    }

    public INetworkConnector getNetworkConnector() {
        return mNetworkConnector;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }
}
