package cs446_w2018_group3.supercardgame.view.mainmenu;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

import cs446_w2018_group3.supercardgame.R;
import cs446_w2018_group3.supercardgame.view.lobby.LobbyActivity;
import cs446_w2018_group3.supercardgame.view.game.GameActivity;

/**
 * Created by GARY on 2018-02-25.
 */

class GameModePopup extends PopupWindow {
    private Button single;
    private Button multi;
    private Button tutorial;

    GameModePopup(Context context) {
        super(context);
        View contentView = LayoutInflater.from(context).inflate(R.layout.game_mode_popup, null);
        this.setContentView(contentView);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();

        // widgets
        single = contentView.findViewById(R.id.Single);
        multi = contentView.findViewById(R.id.Multi);
        tutorial = contentView.findViewById(R.id.Tutorial);

        // set Text
        single.setText("Single Player");
        multi.setText("Multi Player");
        tutorial.setText("Tutorial");

        //set Listener
        single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("mode", 1);
                intent.setClass(v.getContext(), GameActivity.class);
                // Start activity
                v.getContext().startActivity(intent);
            }
        });
        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("mode", 3);
                intent.setClass(v.getContext(), GameActivity.class);
                // Start activity
                v.getContext().startActivity(intent);
            }
        });

        multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(view.getContext(), LobbyActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    void showPopup(View parent) {
        if (!this.isShowing()) {
            this.showAtLocation(parent, Gravity.CENTER, 0, 0);
        } else {
            this.dismiss();
        }
    }
}
