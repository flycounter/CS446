package cs446_w2018_group3.supercardgame.model.network;

import java.net.InetAddress;

/**
 * Created by JarvieK on 2018/3/22.
 */

public class Session {
    private final String playerName;
    private final InetAddress host;
    private final int port;

    public Session(String playerName, InetAddress host, int port) {
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
                (that instanceof Session) &&
                (this.getPlayerName().equals(((Session) that).getPlayerName()) &&
                        ((this.getHost() == null && ((Session) that).getHost() == null) ||
                                (this.getHost() != null && ((Session) that).getHost() != null) &&
                                        this.getHost().equals(((Session) that).getHost())) &&
                        this.getPort() == ((Session) that).getPort()
                );
    }
}
