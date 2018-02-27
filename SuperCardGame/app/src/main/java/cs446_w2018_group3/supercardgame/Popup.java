package cs446_w2018_group3.supercardgame;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.view.ViewGroup;

/**
 * Created by GARY on 2018-02-25.
 */

public class Popup extends PopupWindow {
    Button  single;
    Button multi;
    Button tutorial;
    public Popup(Activity context){
        //windows setting
        View contentView = LayoutInflater.from(context).inflate(R.layout.start_popup,null);
        this.setContentView(contentView);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#66000000"));
        this.setBackgroundDrawable(dw);
        //connect widgets
        single = contentView.findViewById(R.id.Single);
        multi = contentView.findViewById(R.id.Multi);
        tutorial = contentView.findViewById(R.id.Tutorial);
        //set Text
        single.setText("Single Player");
        multi.setText("Multi Player");
        tutorial.setText("Tutorial");
        //set Listener
        single.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Increment mModel counter
                Intent intent = new Intent();
                intent.setClass(v.getContext(),SinglePlayActivity.class);
                // Start activity
                v.getContext().startActivity(intent);
            }
        });
        //demo code
        multi.setEnabled(false);
        tutorial.setEnabled(false);
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
