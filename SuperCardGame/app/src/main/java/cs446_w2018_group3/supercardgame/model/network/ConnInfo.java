package cs446_w2018_group3.supercardgame.model.network;

import java.net.InetAddress;

/**
 * Created by JarvieK on 2018/3/22.
 */

public class ConnInfo {
    private final String playerName;
    private final InetAddress host;
    private final int port;

    public ConnInfo(String playerName, InetAddress host, int port) {
        this.playerName = playerName;
        this.host = host;
        this.port = port;
    }

    public String getPlayerName() {
        return playerName;
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
                (this.getPlayerName().equals(((ConnInfo) that).getPlayerName()) &&
                        ((this.getHost() == null && ((ConnInfo) that).getHost() == null) ||
                                (this.getHost() != null && ((ConnInfo) that).getHost() != null) &&
                                        this.getHost().equals(((ConnInfo) that).getHost())) &&
                        this.getPort() == ((ConnInfo) that).getPort()
                );
    }
}
