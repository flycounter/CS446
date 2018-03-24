package cs446_w2018_group3.supercardgame.model.network;

import cs446_w2018_group3.supercardgame.model.dto.GameRuntimeData;
import cs446_w2018_group3.supercardgame.model.player.Player;
import cs446_w2018_group3.supercardgame.util.events.GameEvent;

/**
 * Created by JarvieK on 2018/3/23.
 */

public interface INetworkConnector {
    void onGameEventReceived(GameEvent e);
    void sendPlayerData(Player player);
    void sendFieldData();
    GameRuntimeData getSyncData();
    void sendSyncData(GameRuntimeData gameRuntimeData);
}
