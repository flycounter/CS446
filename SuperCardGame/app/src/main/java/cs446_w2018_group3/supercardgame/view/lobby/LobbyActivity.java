package cs446_w2018_group3.supercardgame.view.lobby;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.greenrobot.greendao.database.Database;

import java.net.InetAddress;
import java.util.List;

import cs446_w2018_group3.supercardgame.model.dao.DaoMaster;
import cs446_w2018_group3.supercardgame.model.dao.DaoSession;
import cs446_w2018_group3.supercardgame.model.network.ConnInfo;
import cs446_w2018_group3.supercardgame.R;
import cs446_w2018_group3.supercardgame.util.Config;
import cs446_w2018_group3.supercardgame.viewmodel.LobbyViewModel;


public class LobbyActivity extends AppCompatActivity implements
        JoinGameDialogFragment.OnFragmentInteractionListener,
        HostGameDialogFragment.OnFragmentInteractionListener,
        ManualJoinGameDialogFragment.OnFragmentInteractionListener {

    RecyclerView recyclerView;
    Button hostGameBtn;
    Button joinGameBtn;
    LiveData<List<ConnInfo>> hostInfoList;

    private RecyclerViewAdapter mAdapter;

    private LobbyViewModel viewModel;

    private DaoSession mSession;

    private DaoSession getSession() {
        if (mSession == null) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, Config.DB_NAME);
            Database db = helper.getWritableDb();
            helper.getReadableDb();
            mSession = new DaoMaster(db).newSession();
        }
        return mSession;
    }

    private void setAdapter() {
        mAdapter = new RecyclerViewAdapter(LobbyActivity.this, hostInfoList.getValue());

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.SetOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ConnInfo connInfo) {
                // join game
                viewModel.joinGame(connInfo);
                // show join game dialog
                showJoinGameDialog();
            }
        });

        hostInfoList.observe(this, new Observer<List<ConnInfo>>() {
            @Override
            public void onChanged(@Nullable List<ConnInfo> sessions) {
                mAdapter.updateList(sessions);
            }
        });
    }

    private void setHostGameBtn() {
        hostGameBtn = findViewById(R.id.host_game_btn);
        hostGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.hostGame();
                showHostGameDialog();
            }
        });
    }

    private void setJoinGameBtn() {
        joinGameBtn = findViewById(R.id.join_game_btn);
        joinGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showManualJoinGameDialog();
            }
        });

    }

    private void showManualJoinGameDialog() {
        FragmentManager fm = getSupportFragmentManager();
        DialogFragment dialogFragment = ManualJoinGameDialogFragment.newInstance();
        dialogFragment.show(fm, "ManualJoinGameDialog");
    }

    private void showJoinGameDialog() {
        FragmentManager fm = getSupportFragmentManager();
        DialogFragment dialogFragment = JoinGameDialogFragment.newInstance();
        dialogFragment.show(fm, "JoinGameDialog");
    }

    private void showHostGameDialog() {
        FragmentManager fm = getSupportFragmentManager();
        DialogFragment dialogFragment = HostGameDialogFragment.newInstance();
        dialogFragment.show(fm, "HostGameDialog");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        // viewmodel
        viewModel = ViewModelProviders.of(this).get(LobbyViewModel.class);
        viewModel.setSession(getSession());
        viewModel.init(this);

        hostInfoList = viewModel.getLobby();

        recyclerView = findViewById(R.id.lobby_recycler_view);
        setAdapter();
        setHostGameBtn();
        setJoinGameBtn();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.startHostDiscovery();
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.stopHostDiscovery();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.destroyHostDiscovery();
    }

    @Override
    public void onFragmentInteraction() {
        // do nothing
    }

    @Override
    public void onFragmentInteraction(InetAddress host, int port) {
        ConnInfo connInfo = new ConnInfo(viewModel.getGameName(), host, port);
        viewModel.joinGame(connInfo);
        showJoinGameDialog();
    }
}
