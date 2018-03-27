package cs446_w2018_group3.supercardgame.network.Lobby;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import com.google.gson.Gson;

import java.util.List;

import cs446_w2018_group3.supercardgame.model.network.ConnInfo;
import cs446_w2018_group3.supercardgame.network.Connection.ClientHandler;
import cs446_w2018_group3.supercardgame.network.Connection.HostHandler;
import cs446_w2018_group3.supercardgame.network.Connection.IClient;
import cs446_w2018_group3.supercardgame.network.Connection.ConnectionListener;
import cs446_w2018_group3.supercardgame.network.Connection.IHost;
import cs446_w2018_group3.supercardgame.network.Connection.P2pClient;
import cs446_w2018_group3.supercardgame.network.Connection.P2pHost;
import cs446_w2018_group3.supercardgame.util.cb;
import cs446_w2018_group3.supercardgame.viewmodel.LobbyViewModel;

/**
 * Created by JarvieK on 2018/3/22.
 */

public class P2PLobbyManager implements ILobbyManager {
    private static final String TAG = P2PLobbyManager.class.getName();
    private final MutableLiveData<List<ConnInfo>> sessions = new MutableLiveData<>();
    private final LobbyViewModel mViewModel;
    private NsdHelper mNsdHelper;
    private IHost host;
    private IClient client;
    private ConnectionListener mConnectionListener;

    public P2PLobbyManager(Activity activity, LobbyViewModel viewModel) {
        mViewModel = viewModel;
        IntentFilter intentFilter = new IntentFilter();
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
        // remove previously registered service
        mNsdHelper.tearDown();
        // start server
        mViewModel.changeConnectionStateMessage("starting host");
        mViewModel.changeIsGameReadyFlag(false);
        host.start(new IHost.HostStartedListener() {
            @Override
            public void onStarted(ConnInfo connInfo) {
                Log.i(TAG, "host started");
                // start service discovery
                mNsdHelper.registerService(connInfo);
                // update UI
                mViewModel.changeConnectionStateMessage("waiting for clients to join");
                mConnectionListener = new ConnectionListener() {
                    @Override
                    public void onConnected() {
                        Log.i(TAG, "connected from client: " + connInfo);
                        mViewModel.changeConnectionStateMessage("client connected");
                        mViewModel.changeIsGameReadyFlag(true);
                        HostHandler.setHost(host);
                    }

                    @Override
                    public void onDisconnected() {
                        // nothing
                    }

                    @Override
                    public void onMessage(String message) {
                        // nothing
                    }
                };
                host.addConnectionListener(mConnectionListener);
            }
        });
    }

    @Override
    public synchronized void sendConfirmationMessage(cb cb) {
        if (host != null) {
            host.sendMessage(IHost.GAME_JOIN_CONFIRMATION);
            Log.i(TAG, "confirmation message sent");
            cb.then();
        }
    }

    @Override
    public void joinGame(ConnInfo connInfo) {
        // create p2p client
        client = new P2pClient(connInfo);
        Log.i(TAG, "connecting to host: " + new Gson().toJson(connInfo));
        mViewModel.changeConnectionStateMessage("connecting to host");
        mViewModel.changeIsGameReadyFlag(false);
        mConnectionListener = new ConnectionListener() {
            @Override
            public void onConnected() {
                Log.i(TAG, "connected to host: " + connInfo);
                mViewModel.changeConnectionStateMessage("connected to host");
                ClientHandler.setClient(client);
                mViewModel.changeIsGameReadyFlag(true);
            }

            @Override
            public void onDisconnected() {
                Log.i(TAG, "disconnected from host: " + connInfo);
                mViewModel.changeConnectionStateMessage("disconnected from host");
            }

            @Override
            public void onMessage(String message) {
                if (message.equals(IHost.GAME_JOIN_CONFIRMATION)) {
                    mViewModel.changeConnectionStateMessage("host confirmed");
                }
            }
        };
        client.addConnectionListener(mConnectionListener);
        client.connect();
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
        if (client != null) {
            client.removeConnectionListener(mConnectionListener);
        }

    }

    @Override
    public void onDestroy() {

    }

}
