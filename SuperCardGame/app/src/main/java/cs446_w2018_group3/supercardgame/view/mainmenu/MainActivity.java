package cs446_w2018_group3.supercardgame.view.mainmenu;

import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import org.greenrobot.greendao.database.Database;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import cs446_w2018_group3.supercardgame.R;
import cs446_w2018_group3.supercardgame.model.dao.DaoMaster;
import cs446_w2018_group3.supercardgame.model.dao.DaoSession;
import cs446_w2018_group3.supercardgame.model.dao.User;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.util.Config;
import cs446_w2018_group3.supercardgame.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity implements AddUserFragment.UserAddedCallback {
    private static final String TAG = MainActivity.class.getName();

    TextView title;
    Button start;
    Button deckEdit;
    Button shop;
    Button leaderBoard;
    Button reset;
    TextView welcomeMsg;

    MainViewModel viewModel;
    private DaoSession mSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // connect widgets
        title = findViewById(R.id.Title);
        start = findViewById(R.id.Start);
        deckEdit = findViewById(R.id.DeckEdit);
        shop = findViewById(R.id.Shop);
        leaderBoard = findViewById(R.id.Leaderboard);
        reset = findViewById(R.id.reset);
        welcomeMsg = findViewById(R.id.welcome_msg);

        // set Text
        title.setText("Super card\ngame!");
        title.setTextSize(72);
        start.setText("Start");
        deckEdit.setText("DECK EDIT");
        shop.setText("Card shop");
        leaderBoard.setText("Leaderboard");
        reset.setText("Reset");

        // viewmodel
        final MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        this.viewModel = viewModel;

        // set listener
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final GameModePopup gameModePopup = new GameModePopup(MainActivity.this);
                gameModePopup.showPopup(new View(MainActivity.this));
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userReset();
            }
        });


        //demo code
        deckEdit.setEnabled(false);
        shop.setEnabled(false);
        leaderBoard.setEnabled(false);

        // check if user exists
        firstTimeOpenAppCheck();
    }

    private DaoSession getSession() {
        if (mSession == null) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, Config.DB_NAME);
            Database db = helper.getWritableDb();
            helper.getReadableDb();
            mSession = new DaoMaster(db).newSession();
        }
        return mSession;
    }

    private void setWelcomeMsg(String playerName) {
        welcomeMsg.setText(String.format("welcome, " + playerName));

    }

    private void firstTimeOpenAppCheck() {
        List<User> users = getSession().getUserDao().queryBuilder().list();
        if (users.size() < 1) {
            // no user exists
            welcomeMsg.setText(null);
            showAddNewUserDialog();
        }
        else {
            setWelcomeMsg(new Gson().fromJson(users.get(0).getPlayerData(), Player.class).getName());
        }
    }

    private void showAddNewUserDialog() {
        FragmentManager fm = getSupportFragmentManager();
        DialogFragment dialogFragment = AddUserFragment.newInstance(null, null);
        dialogFragment.show(fm, "AddUserFragment");
    }

    private void userReset() {
        getSession().getUserDao().deleteAll();
        firstTimeOpenAppCheck();
    }

    @Override
    public void onFragmentInteraction(String userName) {
        setWelcomeMsg(userName);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                Random rng = new Random();
                User user = new User();

                Player player = new Player(new BigDecimal(user.getId()).intValueExact(), userName);
                user.setId(rng.nextLong());
                user.setPlayerData(gson.toJson(player));
                User.replaceUser(getSession().getUserDao(), user);
            }
        });
    }
}
