package cs446_w2018_group3.supercardgame.network.Connection;

/**
 * Created by JarvieK on 2018/3/25.
 */

public class HostHandler {
    private static IHost host;

    public static synchronized IHost getHost() {
        return host;
    }

    public static void setHost(IHost host) {
        HostHandler.host = host;
    }
}
