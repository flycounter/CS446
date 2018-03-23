package cs446_w2018_group3.supercardgame;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import cs446_w2018_group3.supercardgame.viewmodel.GameViewModel;

public class MainActivity extends AppCompatActivity {
    TextView title;
    Button start;
    Button deckEdit;
    Button shop;
    Button leaderBoard;
    GameViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final GameViewModel viewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        this.viewModel = viewModel;
        //connect widgets
        title = findViewById(R.id.Title);
        start = findViewById(R.id.Start);
        deckEdit = findViewById(R.id.DeckEdit);
        shop = findViewById(R.id.Shop);
        leaderBoard = findViewById(R.id.Leaderboard);
        //set Text
        title.setText("Super card      game!");
        title.setTextSize(72);
        start.setText("START");
        deckEdit.setText("DECK EDIT");
        shop.setText("Card shop");
        leaderBoard.setText("Leaderboard");

        //set listener
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Popup startPopup = new Popup(MainActivity.this, viewModel);
                startPopup.showPopup(new View(MainActivity.this));
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


        //demo code
        shop.setEnabled(false);
        leaderBoard.setEnabled(false);

    }

}
