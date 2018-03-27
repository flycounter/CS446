package cs446_w2018_group3.supercardgame.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.List;

import cs446_w2018_group3.supercardgame.model.network.ConnInfo;
import cs446_w2018_group3.supercardgame.network.Lobby.ILobbyManager;
import cs446_w2018_group3.supercardgame.network.Lobby.P2PLobbyManager;
import cs446_w2018_group3.supercardgame.util.cb;

/**
 * Created by JarvieK on 2018/3/23.
 */

public class LobbyViewModel extends AndroidViewModel {
    private static final String TAG = LobbyViewModel.class.getName();
    private ILobbyManager lobbyManager;
    private final MutableLiveData<ConnInfo> connInfoContainer = new MutableLiveData<>();
    private final MutableLiveData<String> connStateContainer = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isGameReadyContainer = new MutableLiveData<>();

    public LobbyViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(Activity activity) {
        lobbyManager = new P2PLobbyManager(activity, this);
        lobbyManager.start();
    }

    public void hostGame() {
        lobbyManager.hostGame();
    }

    public void sendConfirmationMessage(cb cb) {
        lobbyManager.sendConfirmationMessage(cb);
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
        if (Looper.myLooper() == Looper.getMainLooper()) isGameReadyContainer.setValue(val); else isGameReadyContainer.postValue(val);
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
        if (Looper.myLooper() == Looper.getMainLooper()) connStateContainer.setValue(message); else connStateContainer.postValue(message);
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
}
