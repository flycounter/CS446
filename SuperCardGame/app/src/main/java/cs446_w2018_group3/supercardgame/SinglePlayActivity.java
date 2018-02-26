package cs446_w2018_group3.supercardgame;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cs446_w2018_group3.supercardgame.gamelogic.model.Player;
import cs446_w2018_group3.supercardgame.viewmodel.GameViewModel;

public class SinglePlayActivity extends AppCompatActivity {
    Button combineButton;
    Button endTurnButton;
    Button surrenderButton;
    Button useButton;
    TextView oppBasicStatusView;
    TextView oppBuffEquipView;
    TextView weatherView;
    TextView actionLogView;
    TextView playerBasicStatusView;
    TextView playerBuffEquipView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_single_play);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        //connect wigets
//        combineButton = findViewById(R.id.CombineButton);
//        endTurnButton = findViewById(R.id.EndTurnButton);
//        useButton = findViewById(R.id.UseButton);
//        surrenderButton = findViewById(R.id.SurrenderButton);
//        oppBasicStatusView = findViewById(R.id.OppBasic);
//        oppBuffEquipView = findViewById(R.id.OppBuffEquip);
//        weatherView = findViewById(R.id.Weather);
//        actionLogView = findViewById(R.id.CurrentAction);
//        playerBasicStatusView = findViewById(R.id.PlayerBasic);
//        playerBuffEquipView = findViewById(R.id.playerBuffEquip);
//    }
//
//    @Override
//    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//
//        final GameViewModel viewModel = ViewModelProviders.of(this).get(GameViewModel.class);
//
//        observeViewModel(viewModel);
//    }
//
//    private void observeViewModel(GameViewModel viewModel) {
//        viewModel.getPlayerObservable().observe(this, new Observer<Player>() {
//            @Override
//            public void onChanged(@Nullable Player player) {
//                // update UI if player info changed
//                // example here shows a toast "player data updated"
//                Toast.makeText(SinglePlayActivity.this, "player data updated", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
