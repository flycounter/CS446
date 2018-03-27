package cs446_w2018_group3.supercardgame.network.Lobby;

import android.arch.lifecycle.LiveData;

import java.util.List;

import cs446_w2018_group3.supercardgame.model.network.ConnInfo;
import cs446_w2018_group3.supercardgame.util.cb;

/**
 * Created by JarvieK on 2018/3/22.
 */

public interface ILobbyManager {
    LiveData<List<ConnInfo>> getLobby();
    void hostGame();
    void sendConfirmationMessage(cb cb);
    void joinGame(ConnInfo connInfo);
    void start();
    void onResume();
    void onPause();
    void onStop();
    void onDestroy();
}
