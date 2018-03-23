package cs446_w2018_group3.supercardgame.view.lobby;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.net.wifi.p2p.WifiP2pDevice;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import cs446_w2018_group3.supercardgame.model.network.Session;
import cs446_w2018_group3.supercardgame.network.ILobbyManager;
import cs446_w2018_group3.supercardgame.network.P2PLobbyManager;
import cs446_w2018_group3.supercardgame.R;


public class LobbyActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ILobbyManager lobbyManager;
    LiveData<List<Session>> devices;

    private RecyclerViewAdapter mAdapter;

    private void setAdapter() {
        mAdapter = new RecyclerViewAdapter(LobbyActivity.this, devices.getValue());

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.SetOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Session session) {
                //handle item click events here
            }
        });

        devices.observe(this, new Observer<List<Session>>() {
            @Override
            public void onChanged(@Nullable List<Session> sessions) {
                mAdapter.updateList(sessions);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        lobbyManager = new P2PLobbyManager(this);
        lobbyManager.start();
        lobbyManager.hostGame();
        devices = lobbyManager.getLobby();
        recyclerView = findViewById(R.id.lobby_recycler_view);
        setAdapter();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // peer discovery
        lobbyManager.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        lobbyManager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        lobbyManager.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lobbyManager.onDestroy();
    }
}
