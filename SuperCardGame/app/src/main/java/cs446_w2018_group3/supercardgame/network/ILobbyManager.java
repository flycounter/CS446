package cs446_w2018_group3.supercardgame.network;

import android.arch.lifecycle.LiveData;
import android.net.wifi.p2p.WifiP2pDevice;

import java.util.List;

import cs446_w2018_group3.supercardgame.model.network.Session;

/**
 * Created by JarvieK on 2018/3/22.
 */

public interface ILobbyManager {
    LiveData<List<Session>> getLobby();
    void hostGame();
    void joinGame(Session session);
    void start();
    void onResume();
    void onPause();
    void onStop();
    void onDestroy();
}
