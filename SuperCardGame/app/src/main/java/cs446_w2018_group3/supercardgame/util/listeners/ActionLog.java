package cs446_w2018_group3.supercardgame.util.listeners;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import cs446_w2018_group3.supercardgame.model.dto.GameRuntimeData;
import cs446_w2018_group3.supercardgame.model.field.GameField;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.GameEvent;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.LocalGameEventListener;
import cs446_w2018_group3.supercardgame.model.network.RemoteGameEventListener;;

/**
 * Created by yandong on 2018-03-27.
 */

public class ActionLog implements LocalGameEventListener, RemoteGameEventListener {
    private final MutableLiveData<String> logInfo = new MutableLiveData<>();

    public void updateActionLog (String msg) {
        logInfo.setValue(msg);
    }

    public LiveData<String> getActionLog () {
        return logInfo;
    }

    public void onGameEvent(GameEvent e) {
        String msg = "";
        GameEvent.EventCode eventCode = e.getEventCode();
        switch (eventCode) {
            case PLAYER_ADD:
                msg = "Player is added";
            case GAME_END:
                msg = "Game end";
            case PLAYER_END_TURN:
                msg = "Your turn end";
            case PLAYER_USE_CARD:
                msg = "You use a card";
            case PLAYER_START_TURN:
                msg = "Your turn started";
            case PLAYER_COMBINE_ELEMENT:
                msg = "You combine a element card";
        }
        updateActionLog(msg);
    }

    public void onRemoteGameEventReceived(GameEvent e){

    }

    public void onSyncDataReceived(GameRuntimeData gameRuntimeData){

    }

    public void onPlayerDataReceived(Player player){

    }

    public void onFieldDataReceived(GameField gameField){

    }
}
