package cs446_w2018_group3.supercardgame;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.greenrobot.greendao.database.Database;

import java.math.BigDecimal;
import java.util.List;

import cs446_w2018_group3.supercardgame.model.Translate;
import cs446_w2018_group3.supercardgame.model.cards.ElementCard;
import cs446_w2018_group3.supercardgame.model.dao.DaoMaster;
import cs446_w2018_group3.supercardgame.model.dao.DaoSession;
import cs446_w2018_group3.supercardgame.model.dao.User;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.util.Config;
import cs446_w2018_group3.supercardgame.view.mainmenu.MainActivity;
import cs446_w2018_group3.supercardgame.viewmodel.GameViewModel;

public class CardShopActivity extends AppCompatActivity {
    ImageView fire;
    ImageView water;
    ImageView air;
    ImageView dirt;
    TextView title;
    Button exit;
    private DaoSession mSession;
    int fireNo,waterNo,airNo,dirtNo,gold;
    List<Integer> collection;
    User mUser;
    Player mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_shop);
        getSession();
        mUser = User.getLocalUser(mSession.getUserDao());
        collection= new Gson().fromJson(mUser.getPlayerData(), cs446_w2018_group3.supercardgame.model.player.Player.class).getCollection();
        String userName = new Gson().fromJson(mUser.getPlayerData(), cs446_w2018_group3.supercardgame.model.player.Player.class).getName();
        mPlayer = new Player(new BigDecimal(mUser.getId()).intValueExact(), userName);
        mPlayer.setCollection(collection);
        mPlayer.setCollectionDeck(new Gson().fromJson(mUser.getPlayerData(), cs446_w2018_group3.supercardgame.model.player.Player.class).getCollectionDeck());
        mPlayer.setGold(gold);
        mPlayer.setDeck(new Gson().fromJson(mUser.getPlayerData(), cs446_w2018_group3.supercardgame.model.player.Player.class).getDeck());
        waterNo = collection.get(0);
        fireNo = collection.get(1);
        airNo = collection.get(2);
        dirtNo = collection.get(3);
        gold = new Gson().fromJson(mUser.getPlayerData(), cs446_w2018_group3.supercardgame.model.player.Player.class).getGold();
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
                ShopPopup shopPopup = new ShopPopup(CardShopActivity.this, Translate.CardType.Fire,CardShopActivity.this);
                shopPopup.showPopup(new View(CardShopActivity.this));
            }
        });
        air.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopPopup shopPopup = new ShopPopup(CardShopActivity.this, Translate.CardType.Air,CardShopActivity.this);
                shopPopup.showPopup(new View(CardShopActivity.this));
            }
        });
        dirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopPopup shopPopup = new ShopPopup(CardShopActivity.this, Translate.CardType.Dirt,CardShopActivity.this);
                shopPopup.showPopup(new View(CardShopActivity.this));
            }
        });
        water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopPopup shopPopup = new ShopPopup(CardShopActivity.this, Translate.CardType.Water,CardShopActivity.this);
                shopPopup.showPopup(new View(CardShopActivity.this));
            }
        });

    }
    public DaoSession getSession() {
        if (mSession == null) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, Config.DB_NAME);
            Database db = helper.getWritableDb();
            helper.getReadableDb();
            mSession = new DaoMaster(db).newSession();
        }
        return mSession;
    }
}
