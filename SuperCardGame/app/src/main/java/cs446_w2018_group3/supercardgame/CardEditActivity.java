package cs446_w2018_group3.supercardgame;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.greenrobot.greendao.database.Database;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.model.Translate;
import cs446_w2018_group3.supercardgame.model.cards.Card;
import cs446_w2018_group3.supercardgame.model.cards.ElementCard;
import cs446_w2018_group3.supercardgame.model.dao.DaoMaster;
import cs446_w2018_group3.supercardgame.model.dao.DaoSession;
import cs446_w2018_group3.supercardgame.model.dao.User;
import cs446_w2018_group3.supercardgame.util.Config;
import cs446_w2018_group3.supercardgame.view.mainmenu.MainActivity;
import cs446_w2018_group3.supercardgame.viewmodel.GameViewModel;

public class CardEditActivity extends AppCompatActivity {
    //widgets
    Button save;
    Button exit;
    Button add;
    Button remove;
    TextView collectionText;
    TextView deckText;
    DaoSession mSession;
    //cache deck
    Player mPlayer;
    User mUser;
    List<ElementCard> cacheDeck;
    List<ElementCard> collection;
    Map<Integer, Integer> CardDataMap;
    List<ElementCard> addList; // store id of cards chosen to add into deck
    List<ElementCard> removeList;//card chosen to remove from deck
    int TEXT_SIZE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_edit);
        //disable landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TEXT_SIZE =26;
        //viewModel
        CardDataMap = new HashMap<>();
        removeList = new ArrayList<>();
        addList = new ArrayList<>();
        cacheDeck = new ArrayList<>();
        collection = new ArrayList<>();
        getSession();
        mUser = User.getLocalUser(mSession.getUserDao());
        cacheDeck = new Gson().fromJson(mUser.getPlayerData(), cs446_w2018_group3.supercardgame.model.player.Player.class).getDeck();
        collection= new Gson().fromJson(mUser.getPlayerData(), cs446_w2018_group3.supercardgame.model.player.Player.class).getCollectionDeck();
        String userName = new Gson().fromJson(mUser.getPlayerData(), cs446_w2018_group3.supercardgame.model.player.Player.class).getName();
        mPlayer = new Player(new BigDecimal(mUser.getId()).intValueExact(), userName);
        //connect wigets
        add = findViewById(R.id.AddButton);
        remove = findViewById(R.id.RemoveButton);
        save = findViewById(R.id.SaveButton);
        exit = findViewById(R.id.ExitEditButton);
        collectionText = findViewById(R.id.CollectionText);
        deckText = findViewById(R.id.DeckText);
        //init text
        save.setText("SAVE");
        exit.setText("Exit");
        add.setText("ADD");
        remove.setText("REMOVE");
        collectionText.setText("Your collection");
        int cardNo = cacheDeck.size();
        deckText.setText("Your deck          Card number:"+Integer.toString(cardNo));
        setTextSize(TEXT_SIZE);
        //button listener
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(v.getContext(), MainActivity.class);
                // Start activity
                v.getContext().startActivity(intent);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.setDeck(cacheDeck);
                mPlayer.setCollectionDeck(collection);
                Gson gson = new Gson();
                mUser.setPlayerData(gson.toJson(mPlayer));
                User.replaceUser(mSession.getUserDao(),mUser);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(ElementCard cards : addList){
                    cacheDeck.add(cards);
                }
                for(ElementCard cards: addList){
                    collection.remove(cards);
                }
                addList.clear();
                resetContainer();
                setContainer(collection,1);
                setContainer(cacheDeck,2);
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(ElementCard cards : removeList){
                    cacheDeck.remove(cards);
                }
                for(ElementCard cards: removeList){
                    collection.add(cards);
                }
                removeList.clear();
                resetContainer();
                setContainer(collection,1);
                setContainer(cacheDeck,2);
            }
        });

        setContainer(collection,1);
        setContainer(cacheDeck,2);

    }
    //create a card and add it into collection container(mode 1) or deck container(mode 2)
    private void setCard(ElementCard card,int mode){
        //create a view for card and checkbox
        final LinearLayout childLayout = new LinearLayout(this);
        childLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams childLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if(mode==1) {
            LinearLayout container = findViewById(R.id.CollectionContainer);
            container.addView(childLayout, childLayoutParams);
        }
        else{
            LinearLayout container = findViewById(R.id.DeckContainer);
            container.addView(childLayout, childLayoutParams);
        }
        ImageView cardView = new ImageView(this);
        cardView.setClickable(true);
        final CheckBox cardBox = new CheckBox(this);
        String cardName = Translate.cardToString(card.getCardType());
        setCardImage(cardView,cardName);
        cardBox.setText(cardName);
        cardBox.setTextSize(32);
        LinearLayout.LayoutParams cardLayoutParams = new LinearLayout.LayoutParams(450, 400);
        LinearLayout.LayoutParams boxLayoutParams = new LinearLayout.LayoutParams(450, 200);
        childLayout.addView(cardBox, boxLayoutParams);
        childLayout.addView(cardView, cardLayoutParams);


        // data binding (in a lame way)
        // NOTE: need to be aware of variable scope

        cardBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    if(mode==1) {
                        addList.add(card);
                    }
                    else{
                        removeList.add(card);
                    }
                } else {
                    if(mode==1) {
                                addList.remove(card);
                        }
                    else{

                             removeList.remove(card);
                    }
                }
            }
        });

        cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                PopupInfo popupInfo = new PopupInfo(CardEditActivity.this,card.getCardType());
                popupInfo.showPopup(new View(CardEditActivity.this));
            }
        });
    }

    private void setContainer(List<ElementCard> cards,int mode) {
        // NOTE: must be called after resetHandView()
        for (ElementCard card: cards) {
            setCard(card,mode);
        }
    }

    private void resetContainer() {
        final LinearLayout deckView = findViewById(R.id.DeckContainer);
        final LinearLayout collectionView = findViewById(R.id.CollectionContainer);
        deckView.removeAllViews();
        collectionView.removeAllViews();
        removeList.clear();
        addList.clear();
        CardDataMap.clear();
        int cardNo=cacheDeck.size();
        deckText.setText("Your deck          Card number:"+Integer.toString(cardNo));
    }

    private void setCardImage(ImageView cardView,String cardName){
        switch(cardName){
            case "Fire":cardView.setImageResource(R.drawable.fireicon);break;
            case "Water":cardView.setImageResource(R.drawable.watericon);break;
            case "Air":cardView.setImageResource(R.drawable.airicon);break;
            case "Dirt":cardView.setImageResource(R.drawable.dirticon);break;
            case "Flame":cardView.setImageResource(R.drawable.flameicon);break;
            case "Aqua":cardView.setImageResource(R.drawable.auqaicon);break;
            case "Gale":cardView.setImageResource(R.drawable.galeicon);break;
            case "Rock":cardView.setImageResource(R.drawable.rockicon);break;
            case "Lava":cardView.setImageResource(R.drawable.lavaicon);break;
            case "Mud":cardView.setImageResource(R.drawable.mudicon);break;
            case "Steam":cardView.setImageResource(R.drawable.steamicon);break;
            case "Sand":cardView.setImageResource(R.drawable.sandicon);break;
            case "Blast":cardView.setImageResource(R.drawable.blasticon);break;
            case "Ice":cardView.setImageResource(R.drawable.iceicon);break;

        }
    }
    private void setTextSize(int textSize) {
        deckText.setTextSize(textSize);
        collectionText.setTextSize(textSize);
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

}