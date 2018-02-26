package cs446_w2018_group3.supercardgame.gamelogic.model;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import cs446_w2018_group3.supercardgame.BR;

/**
 * Created by JarvieK on 2018/2/23.
 */

public class Player extends BaseObservable {
    String id;
    String name;

    // ...
    public Player() {
        // stub
        id = "1";
        name = "player name";
    }

    public Player(String name) {
        id = "1";
        this.name = name;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
}
