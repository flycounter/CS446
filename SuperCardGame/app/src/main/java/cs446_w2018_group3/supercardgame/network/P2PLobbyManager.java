package cs446_w2018_group3.supercardgame.network;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;

import java.util.List;

import cs446_w2018_group3.supercardgame.model.network.Session;

/**
 * Created by JarvieK on 2018/3/22.
 */

public class P2PLobbyManager implements ILobbyManager {
    private static final String TAG = "P2PLobbyManager";
    private final IntentFilter intentFilter = new IntentFilter();
    private final MutableLiveData<List<Session>> sessions = new MutableLiveData<>();
    private Activity activity;
    private NsdHelper mNsdHelper;

    public P2PLobbyManager(Activity activity) {
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        this.activity = activity;
        this.mNsdHelper = new NsdHelper(activity, new NsdHelper.SessionListListener() {
            @Override
            public void onServiceListChanged(List<Session> _sessions) {
                sessions.postValue(_sessions);
            }
        });
    }

    @Override
    public LiveData<List<Session>> getLobby() {
        return sessions;
    }

    @Override
    public void start() {}

    @Override
    public void hostGame() {
        mNsdHelper.registerService(9999);
    }

    @Override
    public void joinGame(Session session) {

    }

    @Override
    public void onResume() {
        mNsdHelper.discoverServices();
    }

    @Override
    public void onPause() {
        mNsdHelper.stopDiscovery();
    }

    @Override
    public void onStop() {
        mNsdHelper.tearDown();
    }

    @Override
    public void onDestroy() {

    }

}
