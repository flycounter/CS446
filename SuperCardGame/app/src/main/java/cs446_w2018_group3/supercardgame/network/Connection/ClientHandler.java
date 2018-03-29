package cs446_w2018_group3.supercardgame.network.Connection;

/**
 * Created by JarvieK on 2018/3/25.
 */

public class ClientHandler {
    // NOTE: right now only one client supported, change the static variable to a list to support multiple clients
    private static IClient client;

    public static synchronized IClient getClient() {
        return client;
    }

    public static void setClient(IClient client) {
        ClientHandler.client = client;
    }
}
