package cs446_w2018_group3.supercardgame.network.Connection;

import cs446_w2018_group3.supercardgame.model.network.ConnInfo;

/**
 * Created by JarvieK on 2018/3/23.
 */

public interface IHost {
    void start(HostStartedListener hostStartedListener);
    void stop();
    ConnInfo getConnInfo();

    interface HostStartedListener {
        void onStarted(ConnInfo connInfo);
    }
}
