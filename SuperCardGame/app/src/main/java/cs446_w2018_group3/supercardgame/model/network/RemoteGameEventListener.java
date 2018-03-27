package cs446_w2018_group3.supercardgame.model.network;

import cs446_w2018_group3.supercardgame.model.dto.GameRuntimeData;
import cs446_w2018_group3.supercardgame.model.field.GameField;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.util.events.GameEvent.GameEvent;

/**
 * Created by JarvieK on 2018/3/25.
 */

public interface RemoteGameEventListener {
    void onGameEventReceived(GameEvent e);
    void onSyncDataReceived(GameRuntimeData gameRuntimeData);
    void onPlayerDataReceived(Player player);
    void onFieldDataReceived(GameField gameField);
}
