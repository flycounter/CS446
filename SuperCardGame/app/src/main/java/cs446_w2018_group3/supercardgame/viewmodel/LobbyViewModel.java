package cs446_w2018_group3.supercardgame.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import cs446_w2018_group3.supercardgame.model.dao.DaoSession;
import cs446_w2018_group3.supercardgame.model.dao.User;
import cs446_w2018_group3.supercardgame.model.network.ConnInfo;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.network.Lobby.ILobbyManager;
import cs446_w2018_group3.supercardgame.network.Lobby.P2PLobbyManager;
import cs446_w2018_group3.supercardgame.util.listeners.cb;

/**
 * Created by JarvieK on 2018/3/23.
 */

public class LobbyViewModel extends AndroidViewModel {
    private static final String TAG = LobbyViewModel.class.getName();
    private ILobbyManager lobbyManager;
    private final MutableLiveData<ConnInfo> connInfoContainer = new MutableLiveData<>();
    private final MutableLiveData<String> connStateContainer = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isGameReadyContainer = new MutableLiveData<>();

    DaoSession mSession;

    private String gameName;
    private String playerName = "default_player_name";

    public LobbyViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(Activity activity) {
        lobbyManager = new P2PLobbyManager(activity, this);
        lobbyManager.start();
        playerName = Player.getLocalPlayer(mSession).getName();
    }

    public void hostGame() {
        gameName = String.format("%s's game", playerName);
        lobbyManager.hostGame(gameName);
    }

    public void sendConfirmationMessage() {
        lobbyManager.sendConfirmationMessage();
    }

    public void joinGame(ConnInfo connInfo) {
        connInfoContainer.setValue(connInfo);
        lobbyManager.joinGame(connInfo);
    }

    public void connectionListenerCleanup() {
        lobbyManager.onStop();
    }

    public void changeIsGameReadyFlag(Boolean val) {
        // notify UI that game is ready to join
        if (Looper.myLooper() == Looper.getMainLooper()) isGameReadyContainer.setValue(val);
        else isGameReadyContainer.postValue(val);
    }

    public LiveData<List<ConnInfo>> getLobby() {
        return lobbyManager.getLobby();
    }

    public void startHostDiscovery() {
        lobbyManager.onResume();
    }

    public void stopHostDiscovery() {
        lobbyManager.onPause();
    }

    public void destroyHostDiscovery() {
        lobbyManager.onDestroy();
    }

    public void changeConnectionStateMessage(String message) {
        if (Looper.myLooper() == Looper.getMainLooper()) connStateContainer.setValue(message);
        else connStateContainer.postValue(message);
    }

    public void changeConnInfo(ConnInfo connInfo) {
        if (Looper.myLooper() == Looper.getMainLooper()) connInfoContainer.setValue(connInfo);
        else connInfoContainer.postValue(connInfo);
    }

    public LiveData<String> getConnStateContainer() {
        return connStateContainer;
    }

    public LiveData<ConnInfo> getConnInfoContainer() {
        return connInfoContainer;
    }

    public LiveData<Boolean> getIsGameReadyContainer() {
        return isGameReadyContainer;
    }

    public String getGameName() {
        return gameName;
    }

    public void setSession(DaoSession session) {
        Log.i(TAG, "session set: " + session);
        mSession = session;
    }
}
