package cs446_w2018_group3.supercardgame.network;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import com.google.gson.Gson;

import java.util.List;

import cs446_w2018_group3.supercardgame.model.network.ConnInfo;
import cs446_w2018_group3.supercardgame.network.Connection.Connectable;
import cs446_w2018_group3.supercardgame.network.Connection.IHost;
import cs446_w2018_group3.supercardgame.network.Connection.P2pClient;
import cs446_w2018_group3.supercardgame.network.Connection.P2pHost;
import cs446_w2018_group3.supercardgame.viewmodel.LobbyViewModel;

/**
 * Created by JarvieK on 2018/3/22.
 */

public class P2PLobbyManager implements ILobbyManager {
    private static final String TAG = P2PLobbyManager.class.getName();
    private final IntentFilter intentFilter = new IntentFilter();
    private final MutableLiveData<List<ConnInfo>> sessions = new MutableLiveData<>();
    private final LobbyViewModel mViewModel;
    private NsdHelper mNsdHelper;
    private IHost host;

    public P2PLobbyManager(Activity activity, LobbyViewModel viewModel) {
        mViewModel = viewModel;
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        this.mNsdHelper = new NsdHelper(activity, new NsdHelper.SessionListListener() {
            @Override
            public void onServiceListChanged(List<ConnInfo> _sessions) {
                sessions.postValue(_sessions);
            }
        });
        this.host = new P2pHost();
    }

    @Override
    public LiveData<List<ConnInfo>> getLobby() {
        return sessions;
    }

    @Override
    public void start() {
    }

    @Override
    public void hostGame() {
        // start server
        host.start(new IHost.HostStartedListener() {
            @Override
            public void onStarted(ConnInfo connInfo) {
                // start service discovery
                mNsdHelper.registerService(connInfo);
            }
        });
    }

    @Override
    public void joinGame(ConnInfo connInfo) {
        // create p2p client
        Connectable client = new P2pClient(connInfo);
        mViewModel.changeConnectionStateMessage("connecting to host");
        Log.i(TAG, "connecting to host: " + new Gson().toJson(connInfo));
        client.connect(new Connectable.ConnectionListener() {
            @Override
            public void onConnected() {
                Log.i(TAG, "connected to host: " + connInfo);
                mViewModel.changeConnectionStateMessage("connected to host");
            }
        });

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
