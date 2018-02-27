package cs446_w2018_group3.supercardgame;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.media.audiofx.Equalizer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_single_play);
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
}
