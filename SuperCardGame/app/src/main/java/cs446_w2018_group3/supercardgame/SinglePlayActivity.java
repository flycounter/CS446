package cs446_w2018_group3.supercardgame;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.drawable.ColorDrawable;
import android.media.audiofx.Equalizer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

import cs446_w2018_group3.supercardgame.model.Player;
import cs446_w2018_group3.supercardgame.model.cards.Card;
import cs446_w2018_group3.supercardgame.viewmodel.GameViewModel;

public class SinglePlayActivity extends AppCompatActivity {
    //widgets
    TextView oppStatus;
    TextView oppBuffEquip;
    TextView weather;
    TextView playerStatus;
    TextView playerBuffEquip;
    TextView actionLog;
    Button combine;
    Button use;
    Button endTurn;
    Button surrender;
    int TEXTSIZE;
   // final GameViewModel viewModel = ViewModelProviders.of(this).get(GameViewModel.class);
    List<CheckBox> chosenCard = new ArrayList<CheckBox>(); //store the card that player is chosen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_single_play);
        //get the size of screen and set textsize
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenHeight = dm.heightPixels;
        if(screenHeight<=2000) TEXTSIZE = 20;
        else {
            TEXTSIZE = 28;
        }
        //connect widgets
        oppStatus = findViewById( R.id.OppStatus);
        oppBuffEquip = findViewById( R.id.OppBuffEquid);
        weather = findViewById( R.id.Weather);
        playerStatus = findViewById( R.id.PlayerStatus);
        playerBuffEquip = findViewById( R.id.PlayerBuffEquip);
        actionLog = findViewById( R.id.ActionLog);
        combine = findViewById( R.id.Combine);
        use = findViewById(R.id.Use);
        endTurn = findViewById(R.id.EndTurn);
        surrender = findViewById(R.id.Surrender);
        //init text
        setStatus("Dragon",30,10,"Boss",5,1);
        setStatus("You",20,10,"None",5,2);
        List<String> emptyList = new ArrayList<String>();
        setBuffEquip(emptyList,emptyList,1);
        setBuffEquip(emptyList,emptyList,2);
        combine.setText("Combine");
        use.setText("Use");
        endTurn.setText("EndTurn");
        surrender.setText("Surrender");
        weather.setText("Weather:");
        actionLog.setText("Action:");
        setTextSize(TEXTSIZE);
        //create hand cards
        createHand();
        //add listenrs
        combine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = chosenCard.size();
                if(size!=2){
                    actionLog.setText("Action:Invalid command,you have to choose exactly two element card to combine");
                }
                else{
                    //todo
                }
            }
        });
        use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = chosenCard.size();
                if(size<1){
                    actionLog.setText("Action:Invalid command,you have to choose at least one card to use.");
                }
                if(size>1){
                    actionLog.setText("Action:Invalid command,you can only use one card each time.");
                }
                else{
                   //todo
                }
            }
        });
        endTurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //viewModel.turnEnd();
                actionLog.setText("Action:You end the turn,now is your opponent's turn.");
                combine.setEnabled(false);
                use.setEnabled(false);
                endTurn.setEnabled(false);
            }
        });
        surrender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo
            }
        });
    }

    //mode1:set oppStatus,mode2: set playerStatus
    private void setStatus(String name,int hp,int ap,String pclass,int hand,int mode){
        String text = "Player:"+name+"    HP:"+Integer.toString(hp)+"    AP:"+Integer.toString(ap)+"    Class:"+ pclass +"    Hand:"+Integer.toString(hand);
        if(mode==1) {
            oppStatus.setText(text);

        }
        if(mode==2) {
            playerStatus.setText(text);

        }
    }
    private void setBuffEquip(List<String> buffList,List<String> equipList,int mode){
        String text = "Buff:";
        for(int i =0;i<buffList.size();i++) {
            text += buffList.get(i) + " ";
        }
        if(buffList.size()==0) text += "None";
        text +="    Equip:";
        for(int i=0;i<equipList.size();i++) {
            text += equipList.get(i) + " ";
        }
        if(equipList.size()==0) text += "None";
        if(mode==1) {
            oppBuffEquip.setText(text);

        }
        if(mode==2) {
            playerBuffEquip.setText(text);

        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        final GameViewModel viewModel = ViewModelProviders.of(this).get(GameViewModel.class);

        observeViewModel(viewModel);

        combine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get list of cardIds selected

                List<Integer> cardIds = new ArrayList<>();
                list.add(1);
                viewModel.combineCards(list);
            }
        });

        use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Integer> list = new ArrayList<>();
                list.add(1);
                viewModel.combineCards(list);
            }
        });

        endTurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Integer> list = new ArrayList<>();
                list.add(1);
                viewModel.combineCards(list);
            }
        });
    }

    private void observeViewModel(GameViewModel viewModel) {
        // wire up ui elements and LiveData
        viewModel.getGameRuntime().getPlayer().observe(this, new Observer<Player>() {
            @Override
            public void onChanged(@Nullable Player player) {
                // update UI if player info changed
                // example here shows a toast "player data updated"
                Toast.makeText(SinglePlayActivity.this, "player data updated", Toast.LENGTH_SHORT).show();

                // set player status
                setStatus(
                        player.getName(),
                        player.getHP(),
                        player.getAP(),
                        "default_player_class",
                        player.getHand().size(),
                        2);

                // set hand

            }
        });
    }

    private void setTextSize(int textSize){
        playerBuffEquip.setTextSize(textSize);
        oppBuffEquip.setTextSize( textSize);
        playerStatus.setTextSize( textSize);
        oppStatus.setTextSize(textSize);
        actionLog.setTextSize(textSize);
        weather.setTextSize(textSize);
    }

    private void createHand(){
        final LinearLayout handView = findViewById(R.id.HandContainer);
        handView.removeAllViews();
        //temp code to create fake hands
        int handnum = 5;
        for(int i=0;i<handnum;i++){
            final LinearLayout childLayout = new LinearLayout(this);
            childLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams childLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            handView.addView(childLayout,childLayoutParams);
            TextView cardView = new TextView(this);
            final CheckBox cardBox = new CheckBox(this);
            cardView.setText("Fire");
            cardView.setTextSize(32);
            cardBox.setText("select");
            cardBox.setTextSize(32);
            //cardBox.setId(cardid);
            LinearLayout.LayoutParams cardLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            LinearLayout.LayoutParams boxLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            childLayout.addView(cardBox,boxLayoutParams);
            childLayout.addView(cardView,cardLayoutParams);
            //add listener
                cardBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if(b){
                                chosenCard.add(cardBox);
                                int size =chosenCard.size();
                                if(size>2) {
                                    chosenCard.get(0).setChecked(false);
                                }
                            }
                            else{
                                    chosenCard.remove(cardBox);
                                }
                        }
                });
         }
    }
}
