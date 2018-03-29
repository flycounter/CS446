package cs446_w2018_group3.supercardgame.model.network;

import java.net.InetAddress;

/**
 * Created by JarvieK on 2018/3/22.
 */

public class ConnInfo {
    private final String gameName;
    private final InetAddress host;
    private final int port;

    public ConnInfo(String gameName, InetAddress host, int port) {
        this.gameName = gameName;
        this.host = host;
        this.port = port;
    }

    public String getGameName() {
        return gameName;
    }

    public InetAddress getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    @Override
    public boolean equals(Object that) {
        return (that != null) &&
                (that instanceof ConnInfo) &&
                (this.getGameName().equals(((ConnInfo) that).getGameName()) &&
                        ((this.getHost() == null && ((ConnInfo) that).getHost() == null) ||
                                (this.getHost() != null && ((ConnInfo) that).getHost() != null) &&
                                        this.getHost().equals(((ConnInfo) that).getHost())) &&
                        this.getPort() == ((ConnInfo) that).getPort()
                );
    }
}
