package cs446_w2018_group3.supercardgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView title;
    Button start;
    Button deckEdit;
    Button shop;
    Button leaderBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        start.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Increment mModel counter
                Intent intent = new Intent(MainActivity.this, SinglePlayActivity.class);
                // Start activity
                startActivity(intent);
                finish();
            }
        });
        //demo code
        deckEdit.setEnabled(false);
        shop.setEnabled(false);
        leaderBoard.setEnabled(false);
    }
}
