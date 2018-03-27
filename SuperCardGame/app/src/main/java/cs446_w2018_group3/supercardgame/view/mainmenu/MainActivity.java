package cs446_w2018_group3.supercardgame.view.mainmenu;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cs446_w2018_group3.supercardgame.CardEditActivity;
import cs446_w2018_group3.supercardgame.CardShopActivity;
import cs446_w2018_group3.supercardgame.R;
import cs446_w2018_group3.supercardgame.view.lobby.LobbyActivity;
import cs446_w2018_group3.supercardgame.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {
    TextView title;
    Button start;
    Button deckEdit;
    Button shop;
    Button leaderBoard;
    MainViewModel viewModel;

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

        // set Text
        title.setText("Super card\ngame!");
        title.setTextSize(72);
        start.setText("Start");
        deckEdit.setText("DECK EDIT");
        shop.setText("Card shop");
        leaderBoard.setText("Leaderboard");

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
        deckEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(v.getContext(),CardEditActivity.class);
                // Start activity
                v.getContext().startActivity(intent);
            }
        });

        shop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(v.getContext(),CardShopActivity.class);
                // Start activity
                v.getContext().startActivity(intent);
            }
        });
        //demo code

        leaderBoard.setEnabled(false);

    }

}
