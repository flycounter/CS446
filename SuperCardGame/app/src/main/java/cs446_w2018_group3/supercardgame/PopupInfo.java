package cs446_w2018_group3.supercardgame;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import cs446_w2018_group3.supercardgame.model.Translate;
import cs446_w2018_group3.supercardgame.model.cards.Card;

/**
 * Created by GARY on 2018-03-23.
 */

public class PopupInfo extends PopupWindow {
    TextView info;
    public PopupInfo(Activity context,Translate.CardType cardType){
        View contentView = LayoutInflater.from(context).inflate(R.layout.popup_info,null);
        this.setContentView(contentView);
        this.setWidth(1000);
        this.setHeight(1000);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#000000"));
        this.setBackgroundDrawable(dw);
        //conncet
        info = contentView.findViewById(R.id.CardInfo);
        info.setTextSize(26);
        String infoText = Card.getInfo(cardType);
        info.setText(infoText);
        info.setTextColor(0xffffffff);
    }

    public void showPopup(View parent){
        if(!this.isShowing()){
            this.showAtLocation(parent, Gravity.CENTER,0,0);
        }
        else{
            this.dismiss();
        }
    }
}
