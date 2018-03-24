package cs446_w2018_group3.supercardgame.network;

import android.arch.lifecycle.LiveData;

import java.util.List;

import cs446_w2018_group3.supercardgame.model.network.ConnInfo;

/**
 * Created by JarvieK on 2018/3/22.
 */

public interface ILobbyManager {
    LiveData<List<ConnInfo>> getLobby();
    void hostGame();
    void joinGame(ConnInfo connInfo);
    void start();
    void onResume();
    void onPause();
    void onStop();
    void onDestroy();
}
