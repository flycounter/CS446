package cs446_w2018_group3.supercardgame;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SinglePlayActivity extends AppCompatActivity {
    Model model;
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
    int handNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_play);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //connect wigets
        combineButton = findViewById(R.id.CombineButton);
        endTurnButton = findViewById(R.id.EndTurnButton);
        useButton = findViewById(R.id.UseButton);
        surrenderButton = findViewById(R.id.SurrenderButton);
        oppBasicStatusView = findViewById(R.id.OppBasic);
        oppBuffEquipView = findViewById(R.id.OppBuffEquip);
        weatherView = findViewById(R.id.Weather);
        actionLogView = findViewById(R.id.CurrentAction);
        playerBasicStatusView = findViewById(R.id.PlayerBasic);
        playerBuffEquipView = findViewById(R.id.playerBuffEquip);
        handNum = model.getHandNum();

    }

}
