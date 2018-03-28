package cs446_w2018_group3.supercardgame.view.cardshop;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;

import cs446_w2018_group3.supercardgame.R;
import cs446_w2018_group3.supercardgame.model.Translate;
import cs446_w2018_group3.supercardgame.model.cards.Card;
import cs446_w2018_group3.supercardgame.model.cards.element.ElementCard;
import cs446_w2018_group3.supercardgame.model.dao.User;
import cs446_w2018_group3.supercardgame.util.Parser;

/**
 * Created by GARY on 2018-03-25.
 */

public class ShopPopup extends PopupWindow {
    Button buyButton;
    TextView name;
    TextView status;
    SeekBar seekBar;
    int gold,own,buy,cost;
    Translate.CardType cardType;
    CardShopActivity parent;

    public ShopPopup(Activity context, Translate.CardType cardType,CardShopActivity parent){
        View contentView = LayoutInflater.from(context).inflate(R.layout.shop_popup,null);
        this.setContentView(contentView);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(800);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#ffffff"));
        this.setBackgroundDrawable(dw);
        //conncet
        buyButton  =  contentView.findViewById(R.id.BuyButton);
        name = contentView.findViewById(R.id.ShopPopupTitle);
        status=contentView.findViewById(R.id.BuyStatus);
        seekBar=contentView.findViewById(R.id.PopupSeekBar);
        this.cardType = cardType;
        this.parent = parent;
        //get data from database
        this.gold=parent.gold;
        getOwn();
        buy=0;
        cost=0;
        //init text and seekbar
        name.setText(Translate.cardToString(cardType));
        name.setTextSize(36);
        setStatus(gold,own,buy,cost);
        status.setTextSize(32);
        seekBar.setProgress(0);
        seekBar.setMax(5);
        buyButton.setText("Buy");
        //set listener
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                buy = progress;
                cost = progress*50;
                setStatus(gold,own,buy,cost);
                if(cost>gold){
                    buyButton.setEnabled(false);
                }
                else{
                    buyButton.setEnabled(true);
                }
            }
        });
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gold= gold-cost;
                parent.gold=gold;
                own = own+buy;
                upDateData(buy);

            }
        });


    }

    public void showPopup(View parent){
        if(!this.isShowing()){
            this.showAtLocation(parent, Gravity.CENTER,0,0);
        }
        else{
            this.dismiss();
        }
    }
     private void setStatus(int gold,int own,int buy,int cost){
        String status;
        status = "Gold:"+Integer.toString(gold);
        status = status + "     Own:"+Integer.toString(own);
        status = status + "     Buy:"+Integer.toString(buy);
        status = status + "     Cost:"+Integer.toString(cost);
        this.status.setText(status);
    }
    private void getOwn(){
         switch(cardType){
             case Water: own = parent.waterNo;break;
             case Air:own = parent.airNo;break;
             case Fire: own = parent.fireNo;break;
             case Dirt:own = parent.dirtNo;break;
         }
    }
    private void upDateData(int buy){
        ElementCard newCard=Card.createNewCard(cardType);
        parent.gold=gold;
        parent.mPlayer.setGold(gold);
        parent.mPlayer.addCardToCollection(newCard,buy);
        Gson gson = Parser.getInstance().getParser();
        parent.mUser.setPlayerData(gson.toJson(parent.mPlayer));
        User.replaceUser(parent.getSession().getUserDao(),parent.mUser);
        setStatus(gold,own,0,0);
    }
}
