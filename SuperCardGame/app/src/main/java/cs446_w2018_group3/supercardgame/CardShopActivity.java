package cs446_w2018_group3.supercardgame;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cs446_w2018_group3.supercardgame.model.DBHelper;
import cs446_w2018_group3.supercardgame.model.Translate;
import cs446_w2018_group3.supercardgame.runtime.DeckEditor;

public class CardShopActivity extends AppCompatActivity {
    ImageView fire;
    ImageView water;
    ImageView air;
    ImageView dirt;
    TextView title;
    Button exit;
    DBHelper deck;
    DeckEditor deckEditor;

    //DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_shop);
        // init database
        deck = new DBHelper(this);
        //Cursor ret = deck.getData();
//        int gold = ret.getInt(ret.getColumnIndex(DBHelper.DECK_COLUMN_GOLD));
//        int maxWater = ret.getInt(ret.getColumnIndex(DBHelper.DECK_COLUMN_MAX_WATER));
//        int maxFire = ret.getInt(ret.getColumnIndex(DBHelper.DECK_COLUMN_MAX_FIRE));
//        int maxAir = ret.getInt(ret.getColumnIndex(DBHelper.DECK_COLUMN_MAX_AIR));
//        int maxDirt = ret.getInt(ret.getColumnIndex(DBHelper.DECK_COLUMN_MAX_DIRT));
        deckEditor = new DeckEditor(deck);
        //connect wigets
        fire = findViewById(R.id.ShopFire);
        water= findViewById(R.id.ShopWater);
        air = findViewById(R.id.ShopAir);
        dirt= findViewById(R.id.ShopDirt);
        exit = findViewById(R.id.ExitShopButton);
        title = findViewById(R.id.ShopText);
        //init text and image
        title.setText("     Card Shop");
        title.setTextSize(36);
        exit.setText("Main Menu");
        fire.setImageResource(R.drawable.fireicon);
        water.setImageResource(R.drawable.watericon);
        air.setImageResource(R.drawable.airicon);
        dirt.setImageResource(R.drawable.dirticon);
        //set Listener
        fire.setClickable(true);
        water.setClickable(true);
        air.setClickable(true);
        dirt.setClickable(true);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(v.getContext(), MainActivity.class);
                // Start activity
                v.getContext().startActivity(intent);
            }
        });
        fire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopPopup shopPopup = new ShopPopup(CardShopActivity.this, Translate.CardType.Fire);
                shopPopup.showPopup(new View(CardShopActivity.this));
            }
        });
        air.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopPopup shopPopup = new ShopPopup(CardShopActivity.this, Translate.CardType.Air);
                shopPopup.showPopup(new View(CardShopActivity.this));
            }
        });
        dirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopPopup shopPopup = new ShopPopup(CardShopActivity.this, Translate.CardType.Dirt);
                shopPopup.showPopup(new View(CardShopActivity.this));
            }
        });
        water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopPopup shopPopup = new ShopPopup(CardShopActivity.this, Translate.CardType.Water);
                shopPopup.showPopup(new View(CardShopActivity.this));
            }
        });

    }

}
