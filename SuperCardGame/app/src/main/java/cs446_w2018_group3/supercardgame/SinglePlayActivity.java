package cs446_w2018_group3.supercardgame;

import android.media.audiofx.Equalizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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
}
